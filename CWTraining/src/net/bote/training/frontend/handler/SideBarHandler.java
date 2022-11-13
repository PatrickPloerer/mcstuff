package net.bote.training.frontend.handler;

import net.bote.training.Training;
import net.bote.training.backend.enums.GameRole;
import net.bote.training.backend.enums.GameState;
import net.bote.training.backend.enums.SpectatorState;
import net.bote.training.backend.game.TrainingPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elias Arndt | bote100
 * Created on 24.01.2020
 */

public class SideBarHandler {

    public List<String> lines(TrainingPlayer trainingPlayer) {

        switch (Training.getInstance().getController().getCurrentState()) {
            case LOBBY:
                return Training.getInstance().getMessageService().getYamlConfiguration().getStringList("scoreboard.lobby");
            case INGAME: case RESTART:
                if(!trainingPlayer.getSpectatorState().equals(SpectatorState.NORMAL_SPECTATOR))
                    return Training.getInstance().getMessageService().getYamlConfiguration().getStringList("scoreboard.ingame.player");
                else
                    return Training.getInstance().getMessageService().getYamlConfiguration().getStringList("scoreboard.ingame.spec");
        }

        return Collections.emptyList();
    }

    public void display(TrainingPlayer player) {

        ScoreboardManager sm = Bukkit.getScoreboardManager();
        final Scoreboard board = sm.getNewScoreboard();
        final Objective o = board.registerNewObjective("aaa", "dummy");

        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.translateAlternateColorCodes('&', Training.getInstance().getMessageService().getMessage("scoreboard.caption")));
        // Lobby Scoreboard

        AtomicInteger lineCount = new AtomicInteger(15);

        if (lines(player).size() > 15) {
            o.getScore("§4Too much lines!").setScore(13);
            player.getPlayer().setScoreboard(board);
            return;
        }

        lines(player).forEach(line -> {
            line = Training.getInstance().getMessageService()
                    .validate(ChatColor.translateAlternateColorCodes('&', line)
                            .replace("%RANK%", Training.getInstance().getMySQLBridge().getRank(player.getPlayer().getUniqueId().toString())+""))
                    .replace("%>%", "»");
            if(!Training.getInstance().getController().getCurrentState().equals(GameState.LOBBY)) {
                line = line
                        .replace("%>%", "»")
                        .replace("%GAME_NOW%", Training.getInstance().getController().getPlayedRounds()+"")
                        .replace("%GAME_VAL%", Training.getInstance().getController().getGamesAmount()+"");

                try {
                    if(!player.getSpectatorState().equals(SpectatorState.NORMAL_SPECTATOR))
                        line = line
                                .replace("%ROLE%", player.getTeam().getRole().toString())
                                .replace("%POINTS%", Training.getInstance().getController().getCurrentMap().getName());
                } catch (NullPointerException ignored) {}
            }
            o.getScore(line).setScore(lineCount.getAndDecrement());
        });

        insertToTeam(board);

        player.getPlayer().setScoreboard(board);
    }

    public void display(Player player) {
        display(Training.getInstance().getController().getCWTPlayer(player));
    }

    @SuppressWarnings("deprecation")
	private void insertToTeam(Scoreboard board) {

        if(Training.getInstance().getController().getCurrentState().equals(GameState.LOBBY)) return;

        Team attacker = board.registerNewTeam("0001Att");
        attacker.setPrefix(Training.getInstance().getMessageService().getMessage("ingame.prefix.att"));

        Team defenser = board.registerNewTeam("0002Def");
        defenser.setPrefix(Training.getInstance().getMessageService().getMessage("ingame.prefix.def"));

        Team specs = board.registerNewTeam("0009Black");
        specs.setPrefix("§7[§cX§7] ");

        Bukkit.getOnlinePlayers().forEach(all -> {
            if(Training.getInstance().getController().getCWTPlayer(all).getSpectatorState().equals(SpectatorState.NORMAL_SPECTATOR)) {
                specs.addPlayer(all);
                all.setPlayerListName(specs.getPrefix() + all.getName());
                return;
            }

            TrainingPlayer trainingPlayer = Training.getInstance().getController().getCWTPlayer(all);
            if(Objects.isNull(trainingPlayer)) return;
            if(Objects.isNull(trainingPlayer.getTeam())) return;

            if(trainingPlayer.getTeam().getRole().equals(GameRole.ATTACKER)) {
                attacker.addPlayer(all);
                all.setPlayerListName(attacker.getPrefix() + all.getName());
            } else {
                defenser.addPlayer(all);
                all.setPlayerListName(defenser.getPrefix() + all.getName());
            }

        });

    }

}
