/*    */ package de.padel.buildffa.commands;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;

	import de.padel.buildffa.main.Main;
/*    */ 
/*    */ public class CMD_bffa implements CommandExecutor {
/*    */   @SuppressWarnings("unused")
				private Main plugin;
/*    */   
/*    */   public CMD_bffa(Main main) {
/* 17 */     this.plugin = main;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
/* 23 */     if (sender instanceof Player) {
/* 24 */       Player p = (Player)sender;
/* 25 */       if (p.hasPermission("bffa.admin.help")) {
/* 26 */         if (args.length == 1) {
/* 27 */           if (args[0].equalsIgnoreCase("help")) {
/* 28 */             p.sendMessage("§6-----§7[§bBuildFFA Help§7]§6-----");
/* 29 */             p.sendMessage("§eSpawn setzen §8- §7/setspawn");
/* 30 */             p.sendMessage("§eTodeshöhe setzen §8- §7/setheight");
/* 31 */             p.sendMessage("§eVillager setzen §8- §7/setvillager");
/* 32 */             p.sendMessage("§eMapnamen setzen §8- §7/setname <Name>");
/* 33 */             p.sendMessage("");
/* 34 */             p.sendMessage("§eIn den Build gehen §8- §7/build");
/* 35 */             p.sendMessage("§eTeaming editieren §8- §7/teaming <allow | deny>");
/* 36 */             p.sendMessage("§6-----§7[§bBuildFFA Help§7]§6-----");
/*    */           } else {
/* 38 */             sendHelp(p);
/*    */           } 
/*    */         } else {
/* 41 */           sendHelp(p);
/*    */         } 
/*    */       } else {
/* 44 */         p.sendMessage(Main.noperm);
/*    */       } 
/*    */     } 
/* 47 */     return true;
/*    */   }
/*    */   
/*    */   private void sendHelp(Player p) {
/* 50 */     p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/bffa <help>");
/*    */   }
/*    */ }
