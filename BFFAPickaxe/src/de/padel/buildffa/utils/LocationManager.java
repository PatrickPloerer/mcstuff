package de.padel.buildffa.utils;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class LocationManager {
  public static File folder = new File("plugins/BFFA/");
  
  public static File file = new File("plugins/BFFA/locations.yml");
  
  public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
  
  public static void setupFiles() {
    if (!folder.exists())
      folder.mkdir(); 
    if (!file.exists())
      try {
        file.createNewFile();
      } catch (IOException ex) {
        ex.printStackTrace();
      }  
  }
  public static void saveLocations() {
    try {
      cfg.save(file);
    } catch (IOException ex) {
      ex.printStackTrace();
    } 
  }
  
  public static double getSpawnHeight() {
    return cfg.getDouble("Locations.Spawn.Y");
  }
  
  public static double getHeight() {
    return cfg.getDouble("Locations.Height");
  }
  
  public static void setLocation(String name, Location loc) {
    double x = loc.getBlockX() + 0.5D;
    double y = loc.getBlockY();
    double z = loc.getBlockZ() + 0.5D;
    double yaw = (Math.round(loc.getYaw() / 45.0F) * 45);
    double pitch = (Math.round(loc.getPitch() / 45.0F) * 45);
    String worldName = loc.getWorld().getName();
    cfg.set("Locations." + name + ".X", Double.valueOf(x));
    cfg.set("Locations." + name + ".Y", Double.valueOf(y));
    cfg.set("Locations." + name + ".Z", Double.valueOf(z));
    cfg.set("Locations." + name + ".Yaw", Double.valueOf(yaw));
    cfg.set("Locations." + name + ".Pitch", Double.valueOf(pitch));
    cfg.set("Locations." + name + ".worldName", worldName);
    saveLocations();
  }
  
  public static void setHeight(String name, double height) {
    cfg.set("Locations." + name, Double.valueOf(height));
    saveLocations();
  }
  
  public static void removeLocation(String name) {
    String path = "Locations." + name;
    cfg.set(path, null);
    saveLocations();
  }
  
  public static boolean locationIsExisting(String name) {
    return (cfg.get("Locations." + name) != null);
  }
  
  public static Location getLocation(String name) {
    double x = cfg.getDouble("Locations." + name + ".X");
    double y = cfg.getDouble("Locations." + name + ".Y");
    double z = cfg.getDouble("Locations." + name + ".Z");
    double yaw = cfg.getDouble("Locations." + name + ".Yaw");
    double pitch = cfg.getDouble("Locations." + name + ".Pitch");
    String worldName = cfg.getString("Locations." + name + ".worldName");
    Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);
    loc.setYaw((float)yaw);
    loc.setPitch((float)pitch);
    return loc;
  }
}
