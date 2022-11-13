/*    */ package de.padel.buildffa.commands;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.utils.LocationManager;
/*    */ 
/*    */ public class CMD_setspawn implements CommandExecutor {
/*    */   @SuppressWarnings("unused")
private Main plugin;
/*    */   
/*    */   public CMD_setspawn(Main main) {
/* 18 */     this.plugin = main;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
/* 24 */     if (sender instanceof Player) {
/* 25 */       Player p = (Player)sender;
/* 26 */       if (p.hasPermission("bffa.admin.setspawn")) {
/* 27 */         if (args.length == 0) {
/* 29 */           LocationManager.setLocation("Spawn", p.getLocation());
/* 31 */           LocationManager.saveLocations();
/* 33 */           p.sendMessage(String.valueOf(Main.prefix) + "§7Du hast den §eSpawn §7gesetzt!");
/*    */         } else {
/* 35 */           p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/setspawn");
/*    */         } 
/*    */       } else {
/* 38 */         p.sendMessage(String.valueOf(Main.prefix) + "§cKein Recht!");
/*    */       } 
/*    */     } 
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jarja\Desktop\Plugins\Original\BFFA.jar!\net\bote\bffa\commands\CMD_setspawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */