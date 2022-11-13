package de.padel.speedCW.Manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;


public class MapManager {
	
	public static File f = new File("plugins/SpeedCW", "locations.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
	
	public static void Setup() {
			cfg.set("Location.example", new Location(Bukkit.getWorlds().get(0), 0, 0, 0));
			save();
	}

	public static void save() {
		try {
			cfg.save(f);
		} catch (IOException e) {
			System.err.println("[SpeedCW] Daten konnten nicht gespeichert werden.");
			e.printStackTrace();
		}
	}

	public static YamlConfiguration getConfig() {
		return cfg;
	}

	public static File getFile() {
		return f;
	}
	public static void addLocation(Location loc, String name) {
			cfg.set("Location."+name+".X", loc.getX());
			cfg.set("Location."+name+".Y", loc.getY());
			cfg.set("Location."+name+".Z", loc.getZ());
			cfg.set("Location."+name+".Yaw", loc.getYaw());
			cfg.set("Location."+name+".Pitch", loc.getPitch());
			cfg.set("Location."+name+".World", loc.getWorld().getName());
			save();
	}
	public static boolean isLocation(String name) {
		if(cfg.contains("Location."+name)) {
			return true;
		}
		return false;
	}
	
	public static Location getLocation(String name) {
		ConfigurationSection section = cfg.getConfigurationSection("Location");
		Location loc = null;
		if(section != null ) {
			double x = cfg.getDouble("Location."+name+".X");
			double y = cfg.getDouble("Location."+name+".Y");
			double z = cfg.getDouble("Location."+name+".Z");
			double yaw = cfg.getDouble("Location."+name+".Yaw");
			double pitch = cfg.getDouble("Location."+name+".Pitch");
			String worldName = cfg.getString("Location."+name+".World");
			loc = new Location(Bukkit.getServer().createWorld(new WorldCreator(worldName)), x, y, z, (float)yaw, (float)pitch);
		}
		return loc;
	}

}
