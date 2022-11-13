/*    */ package de.padel.buildffa.commands;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.stats.Ranglist;
import de.padel.buildffa.utils.LocationManager;
/*    */ 
/*    */ public class CMD_setRangList implements CommandExecutor {
/*    */   @SuppressWarnings("unused")
private Main plugin;
/*    */   
/*    */   public CMD_setRangList(Main main) {
/* 17 */     this.plugin = main;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
/* 22 */     if (sender instanceof Player) {
/* 23 */       Player p = (Player)sender;
/* 24 */       if (p.hasPermission("bffa.admin.setRangList")) {
/* 25 */         if (args.length == 0) {
/* 27 */           LocationManager.setLocation("RangLoc", p.getLocation());
/* 29 */           LocationManager.saveLocations();
/* 31 */           p.sendMessage(String.valueOf(Main.prefix) + "§7Du hast die §eRangListenPosition §7gesetzt!");
/* 33 */           Ranglist.set();
/* 34 */           Ranglist.runn();
/*    */         } else {
/* 36 */           p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/setranglist");
/*    */         } 
/*    */       } else {
/* 39 */         p.sendMessage(String.valueOf(Main.prefix) + "§cKein Recht!");
/*    */       } 
/*    */     } 
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jarja\Desktop\Plugins\Original\BFFA.jar!\net\bote\bffa\commands\CMD_setRangList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */