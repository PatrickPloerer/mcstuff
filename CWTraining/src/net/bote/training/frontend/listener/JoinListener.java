package net.bote.training.frontend.listener;

import net.bote.training.Training;
import net.bote.training.backend.enums.SpectatorState;
import net.bote.training.backend.game.TrainingPlayer;
import net.bote.training.frontend.item.TeamSelectorItem;
import net.bote.training.util.ItemBuilder;
import net.bote.training.util.Skull;
import net.bote.training.util.transport.ItemInteractTransporter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

/**
 * @author Elias Arndt | bote100
 * Created on 23.01.2020
 */

public final class JoinListener implements Listener {

    @EventHandler
    public void handle(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if(!Training.getInstance().getMySQLBridge().isConnected()) {
            p.kickPlayer("§4Die MYSQL ist nicht verbunden!");
            return;
        }
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);
        p.setGameMode(GameMode.SURVIVAL);
        p.setLevel(0);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        Training.getInstance().getMySQLBridge().update("INSERT INTO cwbwtraining (`uuid`)" +
                "    VALUES ('"+p.getUniqueId().toString()+"')" +
                "        ON DUPLICATE KEY UPDATE `uuid` = `uuid`;");
        Training.getInstance().getNameFetcher().saveData(p);

        switch (Training.getInstance().getController().getCurrentState()) {
            case LOBBY:

                if (Objects.isNull(getLobbySpawn())) {
                    p.sendMessage(Training.getInstance().getMessageService().getPrefix() + "§cBitte setze den Lobbyspawn mit /setlobby und reloade den Server!");
                    return;
                }

                e.setJoinMessage(Training.getInstance().getMessageService().getMessage("lobby.join", p.getName()));
                Training.getInstance().getStatsRankingHandler().loadRanking();

                TrainingPlayer trainingPlayer = new TrainingPlayer(p);
                trainingPlayer.setSpectatorState(SpectatorState.NONE);
                Training.getInstance().getController().getPlayerList().add(trainingPlayer);

                if (Training.getInstance().getMessageService().getYamlConfiguration().getBoolean("lobby.earlycount"))
                    if (Bukkit.getOnlinePlayers().size() >= 2 * Training.getInstance().getMessageService().getYamlConfiguration().getInt("general.playersPerTeam"))
                        Training.getInstance().getController().setLobbyCountdown(Training.getInstance().getMessageService().getYamlConfiguration().getInt("lobby.earlyvalue"));

                setJoinItems(p);
                p.setLevel(Training.getInstance().getController().getLobbyCountdown());
                Training.getInstance().getController().startLobbyPhase();
                Training.getInstance().getSideBarHandler().display(trainingPlayer);

                if (p.hasPlayedBefore()) p.teleport(getLobbySpawn());
                else Bukkit.getScheduler().runTaskLater(Training.getInstance(), () -> p.teleport(getLobbySpawn()), 2);
                break;
            case RESTART:
            case INGAME:
                TrainingPlayer specPlayer = new TrainingPlayer(p);
                Training.getInstance().getController().getPlayerList().add(specPlayer);
                specPlayer.setSpectatorState(SpectatorState.NORMAL_SPECTATOR);
                if (p.hasPlayedBefore())
                    p.teleport(Training.getInstance().getController().getCurrentMap().getSpectatorSpawn());
                else Bukkit.getScheduler().runTaskLater(Training.getInstance(), ()
                        -> p.teleport(Training.getInstance().getController().getCurrentMap().getSpectatorSpawn()), 2);
                e.setJoinMessage(null);
                p.sendMessage(Training.getInstance().getMessageService().getMessage("ingame.specJoin"));
                Training.getInstance().getSideBarHandler().display(specPlayer);
                Bukkit.getScheduler().runTaskLater(Training.getInstance(), () -> p.setGameMode(GameMode.SPECTATOR), 2);
                Bukkit.getOnlinePlayers().forEach(all -> {
                    all.hidePlayer(p);
                    if(!Training.getInstance().getController().getCWTPlayer(all).getSpectatorState().equals(SpectatorState.NONE))
                        p.hidePlayer(all);
                });
                break;
        }
    }

    private void setJoinItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemBuilder(Material.BED)
                .setID(player.getUniqueId().toString())
                .setName(Training.getInstance().getMessageService().getMessage("lobby.item.team"))
                .addAction(new TeamSelectorItem(player))
                .build());
        player.getInventory().setItem(8,
                new ItemBuilder(Skull.getCustomSkull("http://textures.minecraft.net/texture/19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf",
                        Training.getInstance().getMessageService().getMessage("lobby.item.leave")))
                        .setID(player.getUniqueId().toString())
                        .addAction(new ItemInteractTransporter() {
                            @Override
                            public void exec(PlayerInteractEvent event) {
                                event.getPlayer().kickPlayer("");
                            }
                        })
                        .build());
    }

    private Location getLobbySpawn() {
        YamlConfiguration cfg = Training.getInstance().getController().getLocationConfiguration();

        if (Objects.isNull(cfg.getString("Lobby.World"))) return null;

        return new Location(
                Bukkit.getWorld(cfg.getString("Lobby.World")),
                cfg.getDouble("Lobby.X"),
                cfg.getDouble("Lobby.Y"),
                cfg.getDouble("Lobby.Z"),
                (float) cfg.getDouble("Lobby.Yaw"),
                (float) cfg.getDouble("Lobby.Pitch")
        );
    }

}
