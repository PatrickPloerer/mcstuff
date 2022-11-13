/*     */ package de.padel.itemsystem.main;
import de.padel.itemsystem.listener.EntitySpawnListener;
import de.padel.itemsystem.listener.InvListener;
import de.padel.itemsystem.listener.QuitListener;
/*     */ 
/*     */ import de.padel.itemsystem.mobs.RideAbleChicken;
/*     */ import de.padel.itemsystem.mobs.RideAbleCow;
/*     */ import de.padel.itemsystem.mobs.RideAbleGuardian;
/*     */ import de.padel.itemsystem.mobs.RideAblePig;
/*     */ import de.padel.itemsystem.utils.Config;
/*     */ import de.padel.itemsystem.utils.Mysql;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.UUID;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.minecraft.server.v1_8_R3.Entity;
/*     */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*     */ import net.minecraft.server.v1_8_R3.Packet;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class Main extends JavaPlugin {
/*     */   public static Mysql mysql;
/*     */   
/*  33 */   public static File f = new File("plugins/Items", "config.yml");
/*     */   
/*  34 */   public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
/*     */   
/*     */   public static String prefix;
/*     */   
/*     */   private static Main instance;
/*     */   
/*  40 */   public static HashMap<UUID, RideAblePig> pigs = new HashMap<>();
/*     */   
/*  41 */   public static HashMap<UUID, RideAbleCow> cows = new HashMap<>();
/*     */   
/*  42 */   public static HashMap<UUID, RideAbleGuardian> guardians = new HashMap<>();
/*     */   
/*  43 */   public static HashMap<UUID, RideAbleChicken> chickens = new HashMap<>();
/*     */   
/*     */   public void onEnable() {
/*  50 */     Config.setupConfig();
/*  51 */     prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("Config.Prefix"));
/*  53 */     instance = this;
/*  54 */     registerCMD();
/*  55 */     registerEvents();
/*  56 */     if (cfg.getBoolean("Config.MySQL"))
/*  57 */       connctMySQL(); 
/*     */   }
/*     */   
/*     */   public void onDisable() {}
/*     */   
/*     */   public void registerCMD() {}
/*     */   
/*     */   public void registerEvents() {
/*  70 */     Bukkit.getPluginManager().registerEvents((Listener)new InvListener(), (Plugin)this);
/*  71 */     Bukkit.getPluginManager().registerEvents((Listener)new QuitListener(), (Plugin)this);
/*  72 */     Bukkit.getPluginManager().registerEvents((Listener)new EntitySpawnListener(), (Plugin)this);
/*     */   }
/*     */   
/*     */   public static Main getInstance() {
/*  76 */     return instance;
/*     */   }
/*     */   
/*     */   public void connctMySQL() {
/*  79 */     String ip = cfg.getString("MySQL.ip");
/*  80 */     String database = cfg.getString("MySQL.database");
/*  81 */     String name = cfg.getString("MySQL.name");
/*  82 */     String passwort = cfg.getString("MySQL.password");
/*     */     try {
/*  84 */       mysql = new Mysql(ip, database, name, passwort);
/*  85 */       mysql.update("CREATE TABLE IF NOT EXISTS Items(UUID varchar(64), ITEMS varchar(200), NAMES varchar(200), HIDE int(1));");
/*  86 */       Bukkit.getConsoleSender().sendMessage("Items wurden geladen!");
/*  88 */     } catch (Exception ex) {
/*  89 */       Bukkit.getConsoleSender().sendMessage("Es konnte keine Verbindung zur MySQL hergestellt werden!");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isMySQLEnabled() {
/*  96 */     return cfg.getBoolean("Config.MySQL");
/*     */   }
/*     */   
/*     */   public static void hideEntity(Player p, Entity e) {
/*  99 */     PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { e.getId() });
/* 100 */     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet<?>)packet);
/*     */   }
/*     */   
/*     */   public static void showEntity(Player p, EntityLiving e) {
/* 103 */     PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(e);
/* 104 */     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet<?>)packet);
/*     */   }
/*     */ }


/* Location:              C:\Users\jarja\Desktop\Plugins\Pls\PLobby\plugins\ItemSystem.jar!\de\padel\itemsystem\main\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */