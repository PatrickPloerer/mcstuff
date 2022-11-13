/*    */ package de.padel.buildffa.commands;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.utils.LocationManager;
/*    */ 
/*    */ public class CMD_setheight implements CommandExecutor {
/*    */   @SuppressWarnings("unused")
			private Main plugin;
/*    */   
/*    */   public CMD_setheight(Main main) {
/* 18 */     this.plugin = main;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 24 */     if (sender instanceof Player) {
/* 25 */       Player p = (Player)sender;
/* 26 */       if (p.hasPermission("bffa.admin.setheight")) {
/* 28 */         if (args.length == 0) {
/* 30 */           LocationManager.setHeight("Height", p.getLocation().getBlockY());
/* 31 */           p.sendMessage(String.valueOf(Main.prefix) + "§7Du hast die erfolreich die §eHöhe §7auf §eY:" + p.getLocation().getBlockY() + " §7gesetzt!");
/*    */         } else {
/* 34 */           p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/setheight");
/*    */         } 
/*    */       } else {
/* 38 */         p.sendMessage(String.valueOf(Main.prefix) + "§cKein Recht!");
/*    */       } 
/*    */     } 
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jarja\Desktop\Plugins\Original\BFFA.jar!\net\bote\bffa\commands\CMD_setheight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */