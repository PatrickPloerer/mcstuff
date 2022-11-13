package de.padel.buildffa.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import de.padel.buildffa.main.Main;

public class Config {

	public static void setupConfig() {
	    if (!Main.cfg.isSet("Config")) {
	      Main.cfg.set("Config.Prefix", "&e&lBFFA &7| &r");
	      Main.cfg.set("Config.TeamingForbidden", "&4Teaming ist verboten und kann mit einem &lBan &r&4bestraft werden!");
	      Main.cfg.set("Config.Teaming", Boolean.valueOf(false));
	      Main.cfg.set("Config.BuildprotectionUnderSpawn", Integer.valueOf(5));
	      Main.cfg.set("Config.ShowDisplayname", Boolean.valueOf(false));
	      Main.cfg.set("Config.MySQL", Boolean.valueOf(false));
	      Main.cfg.set("MySQL.ip", "IP");
	      Main.cfg.set("MySQL.database", "database");
	      Main.cfg.set("MySQL.name", "name");
	      Main.cfg.set("MySQL.password", "password");
	      save();
	    } 
	  }
	  
	  public static void save() {
	    try {
	      Main.cfg.save(Main.f);
	    } catch (IOException e) {
	      System.err.println("[BFFA] Daten konnten nicht gespeichert werden.");
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

