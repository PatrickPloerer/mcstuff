package de.padel.coinsys.utils;

import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class LocationM {
/* 12 */   public static File folder = new File("plugins/Coins/");
/*    */   
/* 13 */   public static File file = new File("plugins/Coins/locations.yml");
/*    */   
/* 14 */   public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
/*    */   
/*    */   public static void setupFiles() {
/* 17 */     if (!folder.exists())
/* 18 */       folder.mkdir(); 
/* 21 */     if (!file.exists())
/*    */       try {
/* 23 */         file.createNewFile();
/* 24 */       } catch (IOException ex) {
/* 25 */         ex.printStackTrace();
/*    */       }  
/*    */   }
/*    */   
/*    */   public static void saveLocations() {
/*    */     try {
/* 32 */       cfg.save(file);
/* 33 */     } catch (IOException ex) {
/* 34 */       ex.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setLocation(String name, Location loc) {
/* 39 */     double x = loc.getBlockX() + 0.5D;
/* 40 */     double y = loc.getBlockY();
/* 41 */     double z = loc.getBlockZ() + 0.5D;
/* 42 */     double yaw = (Math.round(loc.getYaw() / 45.0F) * 45);
/* 43 */     double pitch = (Math.round(loc.getPitch() / 45.0F) * 45);
/* 44 */     String worldName = loc.getWorld().getName();
/* 46 */     cfg.set("Locations." + name + ".X", Double.valueOf(x));
/* 47 */     cfg.set("Locations." + name + ".Y", Double.valueOf(y));
/* 48 */     cfg.set("Locations." + name + ".Z", Double.valueOf(z));
/* 49 */     cfg.set("Locations." + name + ".Yaw", Double.valueOf(yaw));
/* 50 */     cfg.set("Locations." + name + ".Pitch", Double.valueOf(pitch));
/* 51 */     cfg.set("Locations." + name + ".worldName", worldName);
/* 52 */     saveLocations();
/*    */   }
/*    */   
/*    */   public static void removeLocation(String name) {
/* 56 */     String path = "Locations." + name;
/* 58 */     cfg.set(path, null);
/* 59 */     saveLocations();
/*    */   }
/*    */   
/*    */   public static void removeMap(int name) {
/* 63 */     String path = "Map." + name;
/* 65 */     cfg.set(path, null);
/* 66 */     saveLocations();
/*    */   }
/*    */   
/*    */   public static boolean locationIsExisting(String name) {
/* 70 */     return (cfg.get("Locations." + name) != null);
/*    */   }
/*    */   
/*    */   public static Location getLocation(String name) {
/* 74 */     double x = cfg.getDouble("Locations." + name + ".X");
/* 75 */     double y = cfg.getDouble("Locations." + name + ".Y");
/* 76 */     double z = cfg.getDouble("Locations." + name + ".Z");
/* 77 */     double yaw = cfg.getDouble("Locations." + name + ".Yaw");
/* 78 */     double pitch = cfg.getDouble("Locations." + name + ".Pitch");
/* 79 */     String worldName = cfg.getString("Locations." + name + ".worldName");
/* 81 */     Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);
/* 82 */     loc.setYaw((float)yaw);
/* 83 */     loc.setPitch((float)pitch);
/* 85 */     return loc;
/*    */   }
/*    */ }

