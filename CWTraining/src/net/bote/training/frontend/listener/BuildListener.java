package net.bote.training.frontend.listener;

import net.bote.training.Training;
import net.bote.training.backend.enums.GameRole;
import net.bote.training.backend.enums.SpectatorState;
import net.bote.training.backend.enums.TrainingLocation;
import net.bote.training.backend.game.TrainingPlayer;
import net.bote.training.frontend.commands.ConfigureMapCommand;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Elias Arndt | bote100
 * Created on 25.01.2020
 */

public final class BuildListener implements Listener {
		

    @EventHandler
    public void on(BlockBreakEvent event) {

        Player p = event.getPlayer();
        TrainingPlayer trainingPlayer = Training.getInstance().getController().getCWTPlayer(p);
        if (ConfigureMapCommand.enabled) return;

        switch (Training.getInstance().getController().getCurrentState()) {
            case LOBBY: case RESTART:

                if(p.getGameMode().equals(GameMode.CREATIVE)) return;
                event.setCancelled(true);
                break;

            case INGAME:
                if(event.getBlock().getType().equals(Material.BED_BLOCK)) {
                	if(Training.getInstance().getController().getCurrentMap().getName().contains("EarlyEP")) {
                		if (trainingPlayer.getTeam().getPlayersAlive() == 0)
                            Training.getInstance().getController().getCurrentMap().closeRound(Training.getInstance().getController().getOppenent(trainingPlayer.getTeam()), false);
                        else {
                            p.teleport(Training.getInstance().getController().getTrainingLocation(
                                    Training.getInstance().getController().getCurrentMap().getName(), TrainingLocation.SPEC_SPAWN));
                            new BukkitRunnable() {
        						
        						@Override
        						public void run() {
        							p.setHealth(20);
        							p.teleport(Training.getInstance().getController().getTrainingLocation(
        		                            Training.getInstance().getController().getCurrentMap().getName(), TrainingLocation.SPEC_SPAWN));
        							
        						}
        					}.runTaskLater(Training.getInstance(), 20);
                            p.setGameMode(GameMode.SPECTATOR);
                            trainingPlayer.setSpectatorState(SpectatorState.PLAYER_SPECTATOR);
                            Training.getInstance().getTitleService().sendTitle(
                                    p,
                                    Training.getInstance().getMessageService().getMessage("ingame.specTitle"),
                                    Training.getInstance().getMessageService().getMessage("ingame.specSubtitle"),
                                    0, 1, 0
                            );
                        }
                	}else if(Training.getInstance().getController().getCurrentMap().getName().contains("FirstRush")) {
                		if (trainingPlayer.getTeam().getPlayersAlive() == 0)
                            Training.getInstance().getController().getCurrentMap().closeRound(Training.getInstance().getController().getOppenent(trainingPlayer.getTeam()), false);
                        else {
                            p.teleport(Training.getInstance().getController().getTrainingLocation(
                                    Training.getInstance().getController().getCurrentMap().getName(), TrainingLocation.SPEC_SPAWN));
                            new BukkitRunnable() {
        						
        						@Override
        						public void run() {
        							p.setHealth(20);
        							p.teleport(Training.getInstance().getController().getTrainingLocation(
        		                            Training.getInstance().getController().getCurrentMap().getName(), TrainingLocation.SPEC_SPAWN));
        							
        						}
        					}.runTaskLater(Training.getInstance(), 20);
                            p.setGameMode(GameMode.SPECTATOR);
                            trainingPlayer.setSpectatorState(SpectatorState.PLAYER_SPECTATOR);
                            Training.getInstance().getTitleService().sendTitle(
                                    p,
                                    Training.getInstance().getMessageService().getMessage("ingame.specTitle"),
                                    Training.getInstance().getMessageService().getMessage("ingame.specSubtitle"),
                                    0, 1, 0
                            );
                        }
                	}
                	
                    event.setCancelled(true);

                    if(Training.getInstance().getController().getCWTPlayer(event.getPlayer()).getTeam().getRole().equals(GameRole.DEFENDER)) return;

                    event.getBlock().setType(Material.AIR);
                    Training.getInstance().getController().getTeamByRole(GameRole.DEFENDER).setRespawnable(false);
                    Bukkit.getOnlinePlayers().forEach(all
                            -> Training.getInstance().getTitleService().sendTitle(
                                    all,
                                    Training.getInstance().getMessageService().getMessage("ingame.bed.title"),
                                    Training.getInstance().getMessageService().getMessage("ingame.bed.subtitle"),
                                    1, 1, 1
                    ));
                    Training.getInstance().getMySQLBridge().addToEntry("beds", 1, event.getPlayer().getUniqueId().toString());
                    Training.getInstance().getMySQLBridge().addToEntry("points", 3, event.getPlayer().getUniqueId().toString());
                }
                if(event.getBlock().getType().equals(Material.WEB)) {
                	event.setCancelled(true);
                	event.getBlock().setType(Material.AIR);
                }
                if(event.getBlock().getType().equals(Material.ENDER_CHEST)) {
                	event.setCancelled(true);
                	event.getBlock().setType(Material.AIR);
                }
                event.setCancelled(EnvironmentListener.isBreakAllowed(event.getBlock().getType()));

                break;
        }

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (ConfigureMapCommand.enabled) return;

        switch (Training.getInstance().getController().getCurrentState()) {
            case LOBBY: case RESTART:
                e.setCancelled(true);
                break;
            case INGAME:
                if (!Training.getInstance().getController().getCWTPlayer(e.getPlayer()).getSpectatorState().equals(SpectatorState.NONE))
                    e.setCancelled(true);
                break;
        }
    }

}
