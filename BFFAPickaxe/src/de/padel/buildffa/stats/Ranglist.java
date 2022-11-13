package de.padel.buildffa.stats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.plugin.Plugin;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.utils.LocationManager;
import de.padel.buildffa.utils.UUIDFetcher;

public class Ranglist {
	  static HashMap<Integer, String> rang = new HashMap<>();
	  
	  public static void set() {
	    ResultSet rs1 = Main.mysql.query("SELECT * FROM BFFAStats ORDER BY KILLS DESC LIMIT 10");
	    int in = 0;
	    try {
	      while (rs1.next()) {
	        in++;
	        rang.put(Integer.valueOf(in), rs1.getString("UUID"));
	      } 
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } 
	    if(rang.size() > 10) {
		    Location loc = LocationManager.getLocation("RangLoc");
		    loc.setX(loc.getX() - 2.0D);
		    Location loc2 = LocationManager.getLocation("RangLoc");
		    loc2.setX(loc2.getX() - 1.0D);
		    Location loc3 = LocationManager.getLocation("RangLoc");
		    Location loc4 = LocationManager.getLocation("RangLoc");
		    loc4.setX(loc4.getX() + 1.0D);
		    Location loc5 = LocationManager.getLocation("RangLoc");
		    loc5.setX(loc5.getX() + 2.0D);
		    ArrayList<Location> LOC = new ArrayList<>();
		    LOC.add(loc);
		    LOC.add(loc2);
		    LOC.add(loc3);
		    LOC.add(loc4);
		    LOC.add(loc5);
		    for (int i = 0; i < LOC.size(); i++) {
		      int id = i + 1;
		      ((Location)LOC.get(i)).setY(((Location)LOC.get(i)).getY() + 2.0D);
		      ((Location)LOC.get(i)).getBlock().setType(Material.SKULL);
		      Skull s = (Skull)((Location)LOC.get(i)).getBlock().getState();
		      s.setSkullType(SkullType.PLAYER);
		      String name = UUIDFetcher.getName(UUID.fromString(rang.get(id)));
		      s.setOwner(name);
		      s.setRotation(BlockFace.SOUTH);
		      s.update();
		      Location newLoc = LOC.get(i);
		      newLoc.setY(newLoc.getY() - 1.0D);
		      if (newLoc.getBlock().getState() instanceof Sign) {
		        BlockState bs = newLoc.getBlock().getState();
		        Sign S = (Sign)bs;
		        S.setLine(0, "");
		        S.setLine(1, "#" + id);
		        S.setLine(2, name);
		        S.setLine(3, "Kills: " + Stats.getKills(rang.get(Integer.valueOf(id))).toString());
		        S.update();
		      }
		    }
	    } 
	  }
	  
	  public static void remove() {
	    Location loc = LocationManager.getLocation("RangLoc");
	    loc.setX(loc.getX() - 2.0D);
	    Location loc2 = LocationManager.getLocation("RangLoc");
	    loc2.setX(loc2.getX() - 1.0D);
	    Location loc3 = LocationManager.getLocation("RangLoc");
	    Location loc4 = LocationManager.getLocation("RangLoc");
	    loc4.setX(loc4.getX() + 1.0D);
	    Location loc5 = LocationManager.getLocation("RangLoc");
	    loc5.setX(loc5.getX() + 2.0D);
	    loc.setY(loc.getY() + 2.0D);
	    loc.getBlock().setType(Material.AIR);
	    loc2.setY(loc2.getY() + 2.0D);
	    loc2.getBlock().setType(Material.AIR);
	    loc3.setY(loc3.getY() + 2.0D);
	    loc3.getBlock().setType(Material.AIR);
	    loc4.setY(loc4.getY() + 2.0D);
	    loc4.getBlock().setType(Material.AIR);
	    loc5.setY(loc5.getY() + 2.0D);
	    loc5.getBlock().setType(Material.AIR);
	  }
	  
	  public static void runn() {
	    Bukkit.getScheduler().runTaskTimer((Plugin)Main.getInstance(), new Runnable() {
	          public void run() {
	            Ranglist.remove();
	            Ranglist.set();
	          }
	        },  0L, 12000L);
	  }
	}
