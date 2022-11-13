package net.bote.training.frontend.listener;

import net.bote.training.Training;
import net.bote.training.backend.enums.GameState;
import net.bote.training.backend.enums.SpectatorState;
import net.bote.training.backend.enums.TrainingLocation;
import net.bote.training.backend.game.TrainingPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

/**
 * @author Elias Arndt | bote100
 * Created on 26.01.2020
 */

public final class DamageListener implements Listener {

    @EventHandler
    public void onDMG(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            switch (Training.getInstance().getController().getCurrentState()) {
                case LOBBY:
                case RESTART:
                    e.setCancelled(true);
                    e.setDamage(0D);
                    break;
                default:    
            }
        }

        if(e.getEntity().getType().equals(EntityType.VILLAGER))
            e.setCancelled(true);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {

        Player death = e.getEntity();
        TrainingPlayer tPDeath = Training.getInstance().getController().getCWTPlayer(death);

        e.setDroppedExp(0);
        e.setKeepInventory(true);
        tPDeath.setSpectatorState(SpectatorState.PLAYER_SPECTATOR);

        if (Objects.isNull(death.getKiller())) {
            e.setDeathMessage(Training.getInstance().getMessageService().getMessage("ingame.death", tPDeath.getTeam().getColorCode(), death.getName()));
        } else {
            Player killer = death.getKiller();
            TrainingPlayer tPKiller = Training.getInstance().getController().getCWTPlayer(killer);
            e.setDeathMessage(Training.getInstance().getMessageService().getMessage(
                    "ingame.deathByPlayer",
                    tPDeath.getTeam().getColorCode(),
                    death.getName(),
                    tPKiller.getTeam().getColorCode(),
                    killer.getName()));
            Training.getInstance().getMySQLBridge().addToEntry("points", 1, killer.getUniqueId().toString());
        }

        Bukkit.getScheduler().runTaskLater(Training.getInstance(), () -> death.spigot().respawn(), 5);

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (!Training.getInstance().getController().getCurrentState().equals(GameState.INGAME)) return;
        Bukkit.getScheduler().runTaskLater(Training.getInstance(), () -> {
            Player p = event.getPlayer();
            TrainingPlayer trainingPlayer = Training.getInstance().getController().getCWTPlayer(p);

            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.setLevel(0);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setFallDistance(0);
            p.setFireTicks(0);
            if (trainingPlayer.getTeam().isRespawnable()) {
                p.sendMessage(Training.getInstance().getMessageService().getMessage("ingame.respawn"));
                p.teleport(trainingPlayer.getTeam().getRespawnLocation());
                new BukkitRunnable() {
					int counter = 0;
					@Override
					public void run() {
						counter++;
						if(counter <= 6) {
						p.teleport(trainingPlayer.getTeam().getRespawnLocation());
						}else {
							this.cancel();
						}
					}
				}.runTaskTimer(Training.getInstance(), 0, 1);

                trainingPlayer.setSpectatorState(SpectatorState.NONE);
                trainingPlayer.setRespawnInventory();
            } else {
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
        }, 3);
    }

    @EventHandler
    public void onHeight(PlayerMoveEvent e) {
        if(e.getPlayer().getLocation().getY() <= 0 && Training.getInstance().getController().getCWTPlayer(e.getPlayer()).getSpectatorState().equals(SpectatorState.NONE))
            e.getPlayer().setHealth(0D);
    }

}