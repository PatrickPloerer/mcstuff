package de.padel.buildffa.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.padel.buildffa.commands.CMD_bffa;
import de.padel.buildffa.commands.CMD_build;
import de.padel.buildffa.commands.CMD_setRangList;
import de.padel.buildffa.commands.CMD_setheight;
import de.padel.buildffa.commands.CMD_setmap;
import de.padel.buildffa.commands.CMD_setspawn;
import de.padel.buildffa.commands.CMD_stats;
import de.padel.buildffa.commands.CMD_teaming;
import de.padel.buildffa.events.BasicListener;
import de.padel.buildffa.events.BuildListener;
import de.padel.buildffa.events.DeathListener;
import de.padel.buildffa.events.InvSortListener;
import de.padel.buildffa.events.JoinListener;
import de.padel.buildffa.events.QuitListener;
import de.padel.buildffa.stats.MySQL;
import de.padel.buildffa.stats.Ranglist;
import de.padel.buildffa.utils.Config;
import de.padel.buildffa.utils.LocationManager;
import de.padel.buildffa.utils.Utils;

public class Main extends JavaPlugin {
	
	  public static String prefix = "";
	  public static MySQL mysql;
	  public static String noperm = String.valueOf(prefix) + "§cKeine Rechte!";
	  public static String error = String.valueOf(prefix) + "§cDaten konnten nicht gespeichert werden!";
	  public static File f = new File("plugins/BFFA/config", "configuration.yml");
	  public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
	  private static Main instance;
	  
	  public void onEnable() {
	    Config.setupConfig();
	    prefix = Utils.compile(cfg.getString("Config.Prefix"));
	    if (cfg.getBoolean("Config.MySQL"))
	      connctMySQL(); 
	    instance = this;
	    registerEvents();
	    getCommand("setname").setExecutor((CommandExecutor)new CMD_setmap(this));
	    getCommand("setspawn").setExecutor((CommandExecutor)new CMD_setspawn(this));
	    getCommand("build").setExecutor((CommandExecutor)new CMD_build(this));
	    getCommand("setheight").setExecutor((CommandExecutor)new CMD_setheight(this));
	    getCommand("stats").setExecutor((CommandExecutor)new CMD_stats(this));
	    getCommand("teaming").setExecutor((CommandExecutor)new CMD_teaming(this));
	    getCommand("bffa").setExecutor((CommandExecutor)new CMD_bffa(this));
	    getCommand("setranglist").setExecutor((CommandExecutor)new CMD_setRangList(this));
	    if (LocationManager.locationIsExisting("RangLoc")) {
	      Ranglist.set();
	      Ranglist.runn();
	    } 
	    Utils u = new Utils();
	    u.startAnimation();
	    System.out.println("[BFFA] Das Plugin wurde erfolgreich gestartet!");
	  }
	  
	  public void onDisable() {
	    for (Player all : Bukkit.getOnlinePlayers())
	      all.kickPlayer(String.valueOf(prefix) + "Server wird kurz neugestartet.");
	    if(mysql.isConnected()) {
	    	mysql.close();
	    }
	  }
	  
	  private void registerEvents() {
	    Bukkit.getPluginManager().registerEvents(new BasicListener(), this);
	    Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
	    Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
	    Bukkit.getPluginManager().registerEvents(new InvSortListener(), this);
	    Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
	    Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
	    System.out.println("[BFFA] Events wurden registriert!");
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
	      mysql = new MySQL(ip, database, name, passwort);
	      mysql.update("CREATE TABLE IF NOT EXISTS BFFAStats(UUID varchar(64), KILLS int, DEATHS int);");
	      mysql.update("CREATE TABLE IF NOT EXISTS BFFAPickInvs(UUID varchar(64), invs varchar(5000));");
	      Bukkit.getConsoleSender().sendMessage("Stats wurden geladen!");
	    } catch (Exception ex) {
	      Bukkit.getConsoleSender().sendMessage("Es konnte keine Verbindung zur MySQL hergestellt werden!");
	    } 
	  }
	  
	  public static boolean isMySQLEnabled() {
	    return cfg.getBoolean("Config.MySQL");
	  }

}
