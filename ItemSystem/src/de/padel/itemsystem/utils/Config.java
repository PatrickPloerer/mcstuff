package de.padel.itemsystem.utils;

import de.padel.itemsystem.main.Main;
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
  public static void setupConfig() {
    if (!Main.cfg.isSet("Config")) {
      Main.cfg.set("Config.Prefix", "&fItems | &f");
      Main.cfg.set("Config.MySQL", Boolean.valueOf(false));
      Main.cfg.set("MySQL.ip", "IP");
      Main.cfg.set("MySQL.database", "items");
      Main.cfg.set("MySQL.name", "name");
      Main.cfg.set("MySQL.password", "password");
      save();
    } 
  }
  
  public static void save() {
    try {
      Main.cfg.save(Main.f);
    } catch (IOException e) {
      System.err.println("[Items] Daten konnten nicht gespeichert werden.");
      e.printStackTrace();
    } 
  }
  
  public static YamlConfiguration getConfig() {
    return Main.cfg;
  }
  
  public static File getFile() {
    return Main.f;
  }
}
