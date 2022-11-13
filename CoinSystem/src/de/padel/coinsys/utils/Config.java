package de.padel.coinsys.utils;

/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;

import de.padel.coinsys.main.Main;
/*    */ 
/*    */ public class Config {
/*    */   public static void setupConfig() {
/* 13 */     if (!Main.cfg.isSet("Config")) {
/* 15 */       Main.cfg.set("Config.Prefix", "&6Coins | &e");
/* 17 */       Main.cfg.set("Config.MySQL", Boolean.valueOf(false));
/* 19 */       Main.cfg.set("MySQL.ip", "IP");
/* 20 */       Main.cfg.set("MySQL.database", "coins");
/* 21 */       Main.cfg.set("MySQL.name", "name");
/* 22 */       Main.cfg.set("MySQL.password", "password");
/* 24 */       save();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void save() {
/*    */     try {
/* 31 */       Main.cfg.save(Main.f);
/* 32 */     } catch (IOException e) {
/* 33 */       System.err.println("[Coins] Daten konnten nicht gespeichert werden.");
/* 34 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static YamlConfiguration getConfig() {
/* 39 */     return Main.cfg;
/*    */   }
/*    */   
/*    */   public static File getFile() {
/* 43 */     return Main.f;
/*    */   }
/*    */ }