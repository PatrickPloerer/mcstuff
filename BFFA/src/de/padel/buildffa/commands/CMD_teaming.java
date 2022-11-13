/*    */ package de.padel.buildffa.commands;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.utils.Config;
import de.padel.buildffa.utils.Utils;
/*    */ 
/*    */ public class CMD_teaming implements CommandExecutor {
/*    */   @SuppressWarnings("unused")
		private Main plugin;
/*    */   
/*    */   public CMD_teaming(Main main) {
/* 19 */     this.plugin = main;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
/* 24 */     if (sender instanceof Player) {
/* 25 */       Player p = (Player)sender;
/* 26 */       if (p.hasPermission("buildffa.admin.teaming")) {
/* 27 */         if (args.length == 1) {
/* 28 */           if (args[0].equalsIgnoreCase("allow")) {
/* 29 */             Main.cfg.set("Config.Teaming", Boolean.valueOf(true));
/* 30 */             Config.save();
/* 31 */             for (Player all : Bukkit.getOnlinePlayers())
/* 32 */               Utils.updateScoreboard(all); 
/* 34 */             p.sendMessage(String.valueOf(Main.prefix) + "§7Du hast Teaming auf §aERLAUBT §7gestellt.");
/* 35 */           } else if (args[0].equalsIgnoreCase("deny")) {
/* 36 */             Main.cfg.set("Config.Teaming", Boolean.valueOf(false));
/* 37 */             p.sendMessage(String.valueOf(Main.prefix) + "§7Du hast Teaming auf §cVERBOTEN §7gestellt.");
/* 38 */             Config.save();
/* 39 */             for (Player all : Bukkit.getOnlinePlayers())
/* 40 */               Utils.updateScoreboard(all); 
/*    */           } else {
/* 43 */             p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/teaming <§aallow §e| §cdeny§e>");
/*    */           } 
/*    */         } else {
/* 46 */           p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/teaming <§aallow §e| §cdeny§e>");
/*    */         } 
/*    */       } else {
/* 49 */         p.sendMessage(String.valueOf(Main.prefix) + "§cKein Recht!");
/*    */       } 
/*    */     } 
/* 52 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jarja\Desktop\Plugins\Original\BFFA.jar!\net\bote\bffa\commands\CMD_teaming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */