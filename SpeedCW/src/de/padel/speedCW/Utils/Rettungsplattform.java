package de.padel.speedCW.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.padel.speedCW.SpeedCW;

public class Rettungsplattform {
  
public static void createRettungsplattform(Material mat, int distance, Player p, boolean destroy, int delay) {
    Location loc1 =  p.getLocation().add(0.0D, distance, 0.0D);
    Location loc2 = p.getLocation().add(2.0D, distance, 0.0D);
    Location loc3 = p.getLocation().add(0.0D, distance, 2.0D);
    Location loc4 = p.getLocation().add(-2.0D,distance, 0.0D);
    Location loc5 = p.getLocation().add(0.0D, distance, -2.0D);
    Material mat1 = loc1.getBlock().getType();
    Material mat2 = loc2.getBlock().getType();
    Material mat3 = loc3.getBlock().getType();
    Material mat4 = loc4.getBlock().getType();
    Material mat5 = loc5.getBlock().getType();
    final Material air = Material.AIR;
    if (mat1.equals(air) && mat2.equals(air) && mat3.equals(air) && mat4.equals(air) && mat5.equals(air)) {
      final Location loc0 = p.getLocation().add(0.0D, distance, 0.0D);
      final Location loc1a = p.getLocation().add(1.0D, distance, 0.0D);
      final Location loc2a = p.getLocation().add(0.0D, distance, 1.0D);
      final Location loc3a = p.getLocation().add(1.0D, distance, 1.0D);
      final Location loc4a = p.getLocation().add(-1.0D, distance, 0.0D);
      final Location loc5a = p.getLocation().add(0.0D, distance, -1.0D);
      final Location loc6 = p.getLocation().add(-1.0D, distance, -1.0D);
      final Location loc7 = p.getLocation().add(-1.0D, distance, 1.0D);
      final Location loc8 = p.getLocation().add(1.0D, distance, -1.0D);
      final Location loc9 = p.getLocation().add(2.0D, distance, 0.0D);
      final Location loc10 = p.getLocation().add(0.0D, distance, 2.0D);
      final Location loc11 = p.getLocation().add(0.0D, distance, -2.0D);
      final Location loc12 = p.getLocation().add(-2.0D, distance, 0.0D);
      loc0.getBlock().setType(mat);
      loc1a.getBlock().setType(mat);
      loc2a.getBlock().setType(mat);
      loc3a.getBlock().setType(mat);
      loc4a.getBlock().setType(mat);
      loc5a.getBlock().setType(mat);
      loc6.getBlock().setType(mat);
      loc7.getBlock().setType(mat);
      loc8.getBlock().setType(mat);
      loc9.getBlock().setType(mat);
      loc10.getBlock().setType(mat);
      loc11.getBlock().setType(mat);
      loc12.getBlock().setType(mat);
      ItemStack item = p.getItemInHand();
      int amount = item.getAmount();
      if (amount == 1) {
        p.setItemInHand(null);
      } else {
        item.setAmount(amount - 1);
      } 
      p.updateInventory();
      if (destroy) {
    	  new BukkitRunnable() {
			
			@Override
			public void run() {
				loc0.getBlock().setType(air);
                loc1a.getBlock().setType(air);
                loc2a.getBlock().setType(air);
                loc3a.getBlock().setType(air);
                loc4a.getBlock().setType(air);
                loc5a.getBlock().setType(air);
                loc6.getBlock().setType(air);
                loc7.getBlock().setType(air);
                loc8.getBlock().setType(air);
                loc9.getBlock().setType(air);
                loc10.getBlock().setType(air);
                loc11.getBlock().setType(air);
                loc12.getBlock().setType(air);
			}
		}.runTaskLater(SpeedCW.getInstance(), delay*20);
      } 
     }
  }
}
