package de.padel.speedCW.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import de.padel.speedCW.SpeedCW;

public class Config {

	public static void Setup() {
		if (!SpeedCW.cfg.isSet("Config")) {
			SpeedCW.cfg.set("Config.MySQL", Boolean.valueOf(true));
			SpeedCW.cfg.set("MySQL.ip", "localhost");
			SpeedCW.cfg.set("MySQL.database", "SpeedCW");
			SpeedCW.cfg.set("MySQL.name", "name");
			SpeedCW.cfg.set("MySQL.password", "pass");
			SpeedCW.cfg.set("LobbyTime", 30);
			SpeedCW.cfg.set("PlayerPerTeam", 1);
			SpeedCW.cfg.set("Elo", false);
			save();
		}
	}

	public static void save() {
		try {
			SpeedCW.cfg.save(SpeedCW.f);
		} catch (IOException e) {
			System.err.println("[SpeedCW] Daten konnten nicht gespeichert werden.");
			e.printStackTrace();
		}
	}

	public static YamlConfiguration getConfig() {
		return SpeedCW.cfg;
	}

	public static File getFile() {
		return SpeedCW.f;
	}

}
