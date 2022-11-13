package net.bote.training.frontend.listener;

import net.bote.training.Training;
import net.bote.training.backend.enums.GameState;
import net.bote.training.backend.enums.SpectatorState;
import net.bote.training.backend.game.TrainingPlayer;
import net.bote.training.frontend.commands.ConfigureMapCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Elias Arndt | bote100
 * Created on 28.01.2020
 */

public final class CWChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent e) {
        if (ConfigureMapCommand.enabled) return;
        TrainingPlayer trainingPlayer = Training.getInstance().getController().getCWTPlayer(e.getPlayer());

        System.out.println(trainingPlayer.getSpectatorState() + " " + trainingPlayer);

        if(Training.getInstance().getController().getCurrentState().equals(GameState.LOBBY)) {
            e.setFormat(Training.getInstance().getMessageService().getMessage("ingame.chat.lobby", trainingPlayer.getPlayer().getDisplayName(), e.getMessage()));
            return;
        }

        if(Training.getInstance().getMessageService().getYamlConfiguration().getInt("general.playersPerTeam") == 1
            && trainingPlayer.getSpectatorState().equals(SpectatorState.NONE)) {
            e.setFormat(Training.getInstance().getMessageService().getMessage("ingame.chat.global", trainingPlayer.getTeam().getColorCode(), trainingPlayer.getPlayer().getName(), e.getMessage()));
            return;
        }

        if (trainingPlayer.getSpectatorState().equals(SpectatorState.NONE)) {
            e.setCancelled(true);
            trainingPlayer.getTeam().getMembers().forEach(mem
                    -> mem.getPlayer().sendMessage(Training.getInstance().getMessageService().getMessage(
                            "ingame.chat.team",
                                  trainingPlayer.getTeam().getColorCode(),
                                  trainingPlayer.getPlayer().getName(),
                                  e.getMessage())));
            return;
        }

        e.setCancelled(true);
        Bukkit.getOnlinePlayers().stream()
                .filter(all->!Training.getInstance().getController().getCWTPlayer(all).getSpectatorState().equals(SpectatorState.NONE))
                .forEach(player -> player.getPlayer().sendMessage(Training.getInstance().getMessageService().getMessage(
                    "ingame.chat.spec",
                    trainingPlayer.getPlayer().getName(),
                    e.getMessage())));

    }

}
