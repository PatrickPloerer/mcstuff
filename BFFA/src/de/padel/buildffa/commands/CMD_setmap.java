/*    */ package de.padel.buildffa.commands;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.utils.Utils;
/*    */ 
/*    */ public class CMD_setmap implements CommandExecutor {
/*    */   @SuppressWarnings("unused")
private Main plugin;
/*    */   
/*    */   public CMD_setmap(Main main) {
/* 20 */     this.plugin = main;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
/* 26 */     if (sender instanceof Player) {
/* 27 */       Player p = (Player)sender;
/* 28 */       if (p.hasPermission("bffa.admin.setname")) {
/* 29 */         if (args.length == 1) {
/* 30 */           String name = args[0];
/* 31 */           if (Main.cfg.getString("Config.Mapname") != null) {
/* 32 */             p.sendMessage(String.valueOf(Main.prefix) + "§7Du hast die Map erfolgreich zu: §e" + name + " §7umbenannt!");
/*    */           } else {
/* 34 */             p.sendMessage(String.valueOf(Main.prefix) + "§7Du hast den Mapname §e" + name + " §7gesetzt!");
/*    */           } 
/* 37 */           Main.cfg.set("Config.Mapname", name);
/*    */           try {
/* 39 */             Main.cfg.save(Main.f);
/* 41 */             for (Player all : Bukkit.getOnlinePlayers())
/* 42 */               Utils.updateScoreboard(all); 
/* 45 */           } catch (IOException e) {
/* 46 */             e.printStackTrace();
/* 47 */             p.sendMessage(Main.error);
/* 48 */             return true;
/*    */           } 
/*    */         } else {
/* 51 */           p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/setname <Name>");
/*    */         } 
/*    */       } else {
/* 54 */         p.sendMessage(String.valueOf(Main.prefix) + "§cKein Recht!");
/*    */       } 
/*    */     } 
/* 57 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jarja\Desktop\Plugins\Original\BFFA.jar!\net\bote\bffa\commands\CMD_setmap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */