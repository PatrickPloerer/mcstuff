/*    */ package de.padel.buildffa.commands;
/*    */ 
/*    */ import org.bukkit.GameMode;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.utils.Utils;
/*    */ 
/*    */ public class CMD_build implements CommandExecutor {
/*    */   @SuppressWarnings("unused")
			private Main plugin;
/*    */   
/*    */   public CMD_build(Main main) {
/* 20 */     this.plugin = main;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
/* 26 */     if (sender instanceof Player) {
/* 27 */       Player p = (Player)sender;
/* 28 */       if (p.hasPermission("bffa.admin.build")) {
/* 30 */         if (args.length == 0) {
/* 31 */          if(Utils.build.contains(p.getUniqueId())) {
					Utils.build.remove(Utils.build.indexOf(p.getUniqueId()));
/* 33 */             p.sendMessage(String.valueOf(Main.prefix) + "§7Du bist nun nicht mehr im §eBuild");
/* 34 */             p.setGameMode(GameMode.SURVIVAL);
/* 35 */             p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 2.0F, 3.0F);
/*    */           } else {
/* 37 */           		Utils.build.add(p.getUniqueId());
/* 38 */             p.sendMessage(String.valueOf(Main.prefix) + "§7Du bist nun im §eBuild");
/* 39 */             p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 2.0F, 3.0F);
/* 40 */             p.setGameMode(GameMode.CREATIVE);
/*    */           } 
/*    */         } else {
/* 43 */           p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/build");
/*    */         } 
/*    */       } else {
/* 47 */         p.sendMessage(String.valueOf(Main.prefix) + "§cKein Recht!");
/*    */       } 
/*    */     } 
/* 50 */     return true;
/*    */   }
/*    */ }
