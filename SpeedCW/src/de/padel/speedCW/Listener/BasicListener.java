package de.padel.speedCW.Listener;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.padel.speedCW.GamePhase;
import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.SpeedPlayer;
import de.padel.speedCW.Utils.Utils;

public class BasicListener implements Listener{
	
	@EventHandler
	public void onLevelChange(PlayerLevelChangeEvent e) {
		e.getPlayer().setLevel(0);
	}
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	public void onMerge(ItemMergeEvent e) {
		if(e.getEntity().getItemStack().hasItemMeta() && e.getEntity().getItemStack().getItemMeta().hasDisplayName()) {
			if(e.getEntity().getLocation().distance(e.getTarget().getLocation()) < 5 && e.getEntity().getLocation().distance(e.getTarget().getLocation()) > 0.4) {
				e.setCancelled(true);
			}else {
				e.setCancelled(false);
			}
		}else{
			e.setCancelled(false);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(SpeedCW.manager.phase.equals(GamePhase.LOBBY) || SpeedCW.manager.phase.equals(GamePhase.RESTART) || SpeedCW.manager.specs.contains(SpeedCW.manager.getSpeedPlayer(e.getPlayer()))) {
			if(!Utils.build.contains(e.getPlayer().getUniqueId())) {
				e.setCancelled(true);
			}
		}else {
			e.setCancelled(false);
		}
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(SpeedCW.manager.phase.equals(GamePhase.LOBBY) || SpeedCW.manager.phase.equals(GamePhase.RESTART) || SpeedCW.manager.specs.contains(SpeedCW.manager.getSpeedPlayer(e.getPlayer()))) {
			if(!Utils.build.contains(e.getPlayer().getUniqueId())) {
				e.setCancelled(true);
			}
		}else {
			if(Utils.build.contains(e.getPlayer().getUniqueId())) {
				e.setCancelled(false);
			}else {
				Utils.buildBlocks.add(e.getBlock());
				e.setCancelled(false);
			}
		}
	}
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();			
		if(SpeedCW.manager.phase.equals(GamePhase.LOBBY) || SpeedCW.manager.phase.equals(GamePhase.RESTART) || SpeedCW.manager.specs.contains(SpeedCW.manager.getSpeedPlayer(p))) {
			if(!Utils.build.contains(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}else {
			if(e.getEntityType().equals(EntityType.VILLAGER) || e.getEntityType().equals(EntityType.ARMOR_STAND)) {
				e.setCancelled(true);
			}else {
				e.setCancelled(false);
			}
		}
	}
	}
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		if(SpeedCW.manager.phase.equals(GamePhase.LOBBY) || SpeedCW.manager.phase.equals(GamePhase.RESTART) || SpeedCW.manager.specs.contains(SpeedCW.manager.getSpeedPlayer((Player)e.getEntity()))) {
			e.setCancelled(true);
		}else {
			e.setCancelled(false);
		}
	}
	@EventHandler
	public void onDrink(PlayerItemConsumeEvent e) {
		if(e.getItem().getType().equals(Material.POTION)){
			int slot = e.getPlayer().getInventory().getHeldItemSlot();
			new BukkitRunnable() {
				
				@Override
				public void run() {
					e.getPlayer().getInventory().setItem(slot, new ItemStack(Material.AIR));
				}
			}.runTaskLater(SpeedCW.getInstance(), 2);
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.getBlock() instanceof ArmorStand) {
			e.setCancelled(true);
		}
		if(SpeedCW.manager.phase.equals(GamePhase.LOBBY) || SpeedCW.manager.phase.equals(GamePhase.RESTART) || SpeedCW.manager.specs.contains(SpeedCW.manager.getSpeedPlayer(e.getPlayer()))) {
			if(!Utils.build.contains(e.getPlayer().getUniqueId())) {
				e.setCancelled(true);
			}
		}
		if(e.getBlock().getType().equals(Material.BED_BLOCK)) {
			e.setCancelled(true);
			Block bed1Foot = SpeedCW.manager.beds.get("bed1Foot");
			Block bed1Head = SpeedCW.manager.beds.get("bed1Head");
			Block bed2Foot = SpeedCW.manager.beds.get("bed2Foot");
			Block bed2Head = SpeedCW.manager.beds.get("bed2Head");
			if(bed1Foot.equals(e.getBlock()) || bed1Head.equals(e.getBlock())) {
				if(SpeedCW.manager.getSpeedPlayer(e.getPlayer()).getTeam().getName().contains("team1")) {
					e.getPlayer().sendMessage(SpeedCW.prefix+"§7Du kannst dein §beigenes §7Bett nicht abbauen");
				}else if(SpeedCW.manager.getSpeedPlayer(e.getPlayer()).getTeam().getName().contains("team2")) {
					bed1Foot.setType(Material.AIR, false);
					bed1Head.setType(Material.AIR, false);
					e.getBlock().setType(Material.AIR, false);
					Block loc = e.getBlock().getRelative(BlockFace.NORTH);
					loc.setType(Material.AIR, false);
					SpeedCW.manager.Team1.setRespawnable(false);
					ArrayList<SpeedPlayer> team1 = SpeedCW.manager.getPlayersFromteam("team1");
					for(SpeedPlayer sp : team1) {
						sp.getPlayer().sendTitle("§cDein Bett wurde zerstört!", "");
					}
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(SpeedCW.prefix+"§7Das Bett von Team §1Blau §7wurde §czerstört!");
						all.playSound(all.getLocation(), Sound.WITHER_DEATH, 1, 1);
					}
					e.getPlayer().sendTitle(SpeedCW.prefix+"§7Du hast das Bett von", "§7Team §1Blau §7abgebaut");
				}else {
					e.setCancelled(true);
				}
			}else if(bed2Foot.equals(e.getBlock()) || bed2Head.equals(e.getBlock())) {
				if(SpeedCW.manager.getSpeedPlayer(e.getPlayer()).getTeam().getName().contains("team2")) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(SpeedCW.prefix+"§7Du kannst dein §beigenes §7Bett nicht abbauen");
				}else if(SpeedCW.manager.getSpeedPlayer(e.getPlayer()).getTeam().getName().contains("team1")) {
					e.setCancelled(true);
					bed2Foot.setType(Material.AIR, false);
					bed2Head.setType(Material.AIR, false);
					e.getBlock().setType(Material.AIR, false);
					Block loc = e.getBlock().getRelative(BlockFace.NORTH);
					loc.setType(Material.AIR, false);
					SpeedCW.manager.Team2.setRespawnable(false);
					ArrayList<SpeedPlayer> team2 = SpeedCW.manager.getPlayersFromteam("team2");
					for(SpeedPlayer sp : team2) {
						sp.getPlayer().sendTitle("§cDein Bett wurde zerstört!", "");
					}
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(SpeedCW.prefix+"§7Das Bett von Team §cRot §7wurde §czerstört!");
						all.playSound(all.getLocation(), Sound.WITHER_DEATH, 1, 1);
					}
					e.getPlayer().sendTitle(SpeedCW.prefix+"§7Du hast das Bett von", "§7Team §cRot §7abgebaut");
				}else {
					e.setCancelled(true);
				}
			}else {
				e.setCancelled(true);
			}
		
		}else {
			if(Utils.build.contains(e.getPlayer().getUniqueId())) {
				e.setCancelled(false);
			}else {
				if(Utils.buildBlocks.contains(e.getBlock())) {
					if(e.getBlock().getType().equals(Material.SANDSTONE)) {
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
						e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), Utils.createItem2(Material.SANDSTONE, (short) 2, 1, "§6Blöcke", Arrays.asList("")));
					}else if(e.getBlock().getType().equals(Material.LADDER)) {
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
						e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), Utils.createItem2(Material.LADDER, (short) 1, 1, "§cLeiter", Arrays.asList("")));
					}else if(e.getBlock().getType().equals(Material.IRON_BLOCK)) {
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
						e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), Utils.createItem2(Material.IRON_BLOCK, (short) 1, 1, "§7Eisenblock", Arrays.asList("")));
					}else if(e.getBlock().getType().equals(Material.CHEST)) {
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
						e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(),  Utils.createItem(Material.CHEST,  1, "§7Truhe", Arrays.asList("")));
					}else if(e.getBlock().getType().equals(Material.ENDER_STONE)) {
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
						e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), Utils.createItem2(Material.ENDER_STONE, (short) 1, 1, "§5End-Stone",
								Arrays.asList("")));
					}else if(e.getBlock().getType().equals(Material.ENDER_CHEST)) {
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
						e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), Utils.createItem(Material.ENDER_CHEST,  1, "§5Endertruhe", Arrays.asList("")));
					}else if(e.getBlock().getType().equals(Material.WEB)) {
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
					}else if(e.getBlock().getType().equals(Material.SANDSTONE_STAIRS)) {
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
						e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), Utils.createItem(Material.SANDSTONE_STAIRS, 1, "§eStairs", Arrays.asList("")));
					}else {
						e.setCancelled(false);
					}
					Utils.buildBlocks.remove(e.getBlock());
				}else {
					e.setCancelled(true);
				}
			}
		}
	}

}
