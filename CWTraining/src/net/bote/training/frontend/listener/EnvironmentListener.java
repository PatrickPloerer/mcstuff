package net.bote.training.frontend.listener;

import net.bote.training.Training;
import net.bote.training.backend.enums.GameState;
import net.bote.training.backend.enums.SpectatorState;
import net.bote.training.frontend.commands.ConfigureMapCommand;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * @author Elias Arndt | bote100
 * Created on 27.01.2020
 */

public final class EnvironmentListener implements Listener {
	
	
	@EventHandler
	public void onDrink(PlayerItemConsumeEvent e) {
		if(e.getItem().getType().equals(Material.POTION)){
			int slot = e.getPlayer().getInventory().getHeldItemSlot();
			new BukkitRunnable() {
				
				@Override
				public void run() {
					e.getPlayer().getInventory().setItem(slot, new ItemStack(Material.AIR));
				}
			}.runTaskLater(Training.getInstance(), 2);
		}
	}
	@EventHandler
	public void AnimalSpiritEndermanNoPearlDamage(PlayerTeleportEvent event){
	    Player p = event.getPlayer();
	    if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL){
	        event.setCancelled(true);
	        p.setFallDistance(0);
	        p.setNoDamageTicks(1);
	        p.teleport(event.getTo());
	    }
	}
	
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            switch (Training.getInstance().getController().getCurrentState()) {
                case LOBBY:
                case RESTART:
                    e.setCancelled(true);
                    break;
                case INGAME:
                	if(e.getCause().equals(DamageCause.ENTITY_EXPLOSION) || e.getCause().equals(DamageCause.BLOCK_EXPLOSION)) {
                		if(e.getDamager() instanceof TNTPrimed) {
                			TNTPrimed tnp = (TNTPrimed) e.getDamager();
                			if(tnp.getSource() instanceof Player) {
	                			Player source = (Player) tnp.getSource();
	                			if(player == source) {
	                				e.setCancelled(true);
	                			}
                			}
                		}
                	}
                	if(e.getEntityType() == EntityType.PLAYER && e.getDamager() != null && e.getDamager().getType() == EntityType.ARROW
                			) {
                		if(((Arrow)e.getDamager()).getShooter() == e.getEntity()) {
                			e.setCancelled(true);
                		}
                	}
                    if (!(e.getDamager() instanceof Player)) return;

                    if (!(Training.getInstance().getController().getCWTPlayer(((Player) e.getDamager())).getSpectatorState().equals(SpectatorState.NONE))) {
                        e.setCancelled(true);
                        return;
                    }
                    if (Training.getInstance().getController().getCWTPlayer(player).getTeam().equals(Training.getInstance().getController().getCWTPlayer(((Player) e.getDamager())).getTeam())) {
                        e.setCancelled(true);
                        return;
                    }

                    break;
            }
        }

        if(e.getEntity().getType().equals(EntityType.VILLAGER))
            e.setCancelled(true);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        e.blockList().removeIf(block -> isBreakAllowed(block.getType()));
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Villager) {
            e.setCancelled(true);
            return;
        } else if (e.getEntity() instanceof Player)
            e.setCancelled(!Training.getInstance().getController().getCWTPlayer(((Player) e.getEntity())).getSpectatorState().equals(SpectatorState.NONE)
                    || !Training.getInstance().getController().getCurrentState().equals(GameState.INGAME));
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (ConfigureMapCommand.enabled) return;
        e.setCancelled(Training.getInstance().getController().getCurrentState().equals(GameState.LOBBY) ||
                !Training.getInstance().getController().getCWTPlayer(((Player) e.getWhoClicked())).getSpectatorState().equals(SpectatorState.NONE));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (ConfigureMapCommand.enabled) return;
        if (Training.getInstance().getController().getCurrentState().equals(GameState.INGAME))
            if (!Training.getInstance().getController().getCWTPlayer(e.getPlayer()).getSpectatorState().equals(SpectatorState.NONE)) {
                try {
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
                        if (e.getPlayer().getItemInHand() != null)
                            e.getPlayer().getNearbyEntities(3, 3, 3).stream()
                                    .filter(all -> all instanceof Player)
                                    .filter(all -> !Training.getInstance().getController().getCWTPlayer(((Player) all)).getSpectatorState().equals(SpectatorState.NONE))
                                    .forEach(all -> all.setVelocity(new Vector(0.8, 0.3, 0.8)));
                } catch (NullPointerException ignored) {
                }
            }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (ConfigureMapCommand.enabled) return;
        e.setCancelled(true);
    }

    public static boolean isBreakAllowed(Material type) {
        switch (type) {
            default:
                return true;
            case SANDSTONE:
            case EMERALD_BLOCK:
            case ENDER_STONE:
            case GLASS:
            case GLOWSTONE:
            case WEB:
            case LADDER:
            case TNT:
            case IRON_BLOCK:
            case SANDSTONE_STAIRS:
            case RED_SANDSTONE:
            case RED_SANDSTONE_STAIRS:
            case CHEST:
            	return false;
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (ConfigureMapCommand.enabled) return;
        if (!Training.getInstance().getController().getCWTPlayer(e.getPlayer()).getSpectatorState().equals(SpectatorState.NONE)) {
            e.setCancelled(true);
            return;
        }

        e.setCancelled(Training.getInstance().getController().getCurrentState() != GameState.INGAME);
    }

    @EventHandler
    public void onReward(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onSleep(PlayerInteractEvent e) {
        e.setCancelled(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.BED_BLOCK);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(e.toWeatherState());
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (!Training.getInstance().getController().getCWTPlayer(((Player) e.getEntity())).getSpectatorState().equals(SpectatorState.NONE)) {
            e.setCancelled(true);
            return;
        }
        e.setCancelled(Training.getInstance().getController().getCurrentState().equals(GameState.LOBBY));
    }

}
