package de.padel.coinsys.main;

import java.io.File;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.padel.coinsys.commands.CoinsMain;
import de.padel.coinsys.commands.setChest;
import de.padel.coinsys.commands.setShop;
import de.padel.coinsys.commands.setVillager;
import de.padel.coinsys.events.InventoryEvent;
import de.padel.coinsys.utils.Config;
import de.padel.coinsys.utils.LocationM;
import de.padel.coinsys.utils.Mysql;
import de.padel.coinsys.utils.NMSUtils;
import de.padel.coinsys.utils.Utils;
import de.padel.coinsys.utils.VillagerN;
import de.padel.coinsys.utils.WitchN;

public class Main extends JavaPlugin {
	public static Mysql mysql;

	public static File f = new File("plugins/Coins/config", "configuration.yml");

	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

	public static String prefix;

	private static Main instance;

	public static Utils utils;

	public static InventoryEvent InvEvnt;

	public void onEnable() {
		utils = new Utils();
		Config.setupConfig();
		prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("Config.Prefix"));
		instance = this;
		checkLottery();
		registerCMD();
		InvEvnt = new InventoryEvent();
		InvEvnt.startTimer();
		registerEvents();
		if (cfg.getBoolean("Config.MySQL")) {
			connctMySQL();
		}
	}

	public void onDisable() {
		mysql.close();
	}

	public void registerCMD() {
		getCommand("coins").setExecutor(new CoinsMain());
		getCommand("setVillager").setExecutor(new setVillager());
		getCommand("setShop").setExecutor(new setShop());
		getCommand("setLottery").setExecutor(new setChest());
	}

	public void registerEvents() {
		Bukkit.getPluginManager().registerEvents(InvEvnt, (Plugin) this);
	}

	public static Main getInstance() {
		return instance;
	}

	public void connctMySQL() {
		String ip = cfg.getString("MySQL.ip");
		String database = cfg.getString("MySQL.database");
		String name = cfg.getString("MySQL.name");
		String passwort = cfg.getString("MySQL.password");
		try {
			mysql = new Mysql(ip, database, name, passwort);
			mysql.update(
					"CREATE TABLE IF NOT EXISTS Coins(UUID varchar(64), COINS int(20), DAILY varchar(200), DAILY1 varchar(200), DAILY2 varchar(200));");
			Bukkit.getConsoleSender().sendMessage("Coins wurden geladen!");
		} catch (Exception ex) {
			Bukkit.getConsoleSender().sendMessage("Es konnte keine Verbindung zur MySQL hergestellt werden!");
		}
	}

	public static boolean isMySQLEnabled() {
		return cfg.getBoolean("Config.MySQL");
	}

	public void checkVillager() {
		if (LocationM.locationIsExisting("Villager")) {
			NMSUtils.spawnEntity(new VillagerN(LocationM.getLocation("Villager").getWorld()),
					LocationM.getLocation("Villager"));
		} else {
			Bukkit.broadcastMessage(String.valueOf(String.valueOf(prefix)) + "§cPlease set the Villager!");
		}
	}

	public void checkShop() {
		if (LocationM.locationIsExisting("Shop")) {
			NMSUtils.spawnEntity(new WitchN(LocationM.getLocation("Shop")), LocationM.getLocation("Shop"));
		} else {
			Bukkit.broadcastMessage(String.valueOf(String.valueOf(prefix)) + "§cPlease set the Shop!");
		}
	}

	public void checkLottery() {
		if (LocationM.locationIsExisting("Lottery")) {
			Location loc = LocationM.getLocation("Lottery");
			if (loc.getBlock().getType().equals(Material.ENDER_CHEST)) {
				return;
			} else {
				loc.getBlock().setType(Material.ENDER_CHEST);
			}

			Bukkit.broadcastMessage(String.valueOf(prefix) + "§cPlease set a Enderchest as the Lottery!");
		} else {
			Bukkit.broadcastMessage(String.valueOf(prefix) + "§cPlease set the Lottery!");
		}
	}
}
