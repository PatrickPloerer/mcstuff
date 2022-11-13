package de.padel.buildffa.events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.utils.LocationManager;
import de.padel.buildffa.utils.Utils;

public class BuildListener implements Listener {
	
	public ArrayList<Block> placed = new ArrayList<>();
	
	@EventHandler
	public void onBuild(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(Utils.build.contains(p.getUniqueId())) {
			e.setCancelled(false);
			return;
		}else {
			Block b = e.getBlock();
			if(b.getType().equals(Material.SANDSTONE) || b.getType().equals(Material.RED_SANDSTONE) || b.getType().equals(Material.BRICK) || b.getType().equals(Material.ENDER_STONE) || b.getType().equals(Material.QUARTZ_BLOCK)
					 || b.getType().equals(Material.GLOWSTONE) || b.getType().equals(Material.IRON_BLOCK) || b.getType().equals(Material.EMERALD_BLOCK) || b.getType().equals(Material.REDSTONE_BLOCK)
					 || b.getType().equals(Material.DIAMOND_BLOCK)) {
				double y = p.getLocation().getY();
				p.getItemInHand().setAmount(64);
				if(y >= (LocationManager.getSpawnHeight()-5)) {
					e.setCancelled(true);
				}else {
					e.setCancelled(false);
					placed.add(b);
					new BukkitRunnable() {
						
						@Override
						public void run() {
							b.setType(Material.GOLD_BLOCK);
							new BukkitRunnable() {
								
								@Override
								public void run() {
									b.setType(Material.AIR);
									placed.remove(placed.indexOf(b));
								}
							}.runTaskLater(Main.getInstance(), 60);
						}
					}.runTaskLater(Main.getInstance(), 60);
				}
			}else {
				e.setCancelled(true);
			}
		}
		
	}

}
