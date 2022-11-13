/*     */ package de.padel.buildffa.commands;
/*     */ 
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.stats.Stats;
/*     */ 
/*     */ public class CMD_stats implements CommandExecutor {
/*     */   @SuppressWarnings("unused")
private Main plugin;
/*     */   
/*     */   public CMD_stats(Main main) {
/*  21 */     this.plugin = main;
/*     */   }
/*     */   
/*     */   @SuppressWarnings("deprecation")
public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
/*  27 */     if (sender instanceof Player) {
/*  28 */       Player p = (Player)sender;
/*  29 */       if (Main.isMySQLEnabled()) {
/*     */         try {
/*  31 */           if (args.length == 0) {
/*  32 */             p.sendMessage("§7-----[§6Stats » §e" + p.getName() + "§7]-----");
/*  33 */             p.sendMessage("§7Kills: §e" + Stats.getKills(p.getUniqueId().toString()));
/*  34 */             p.sendMessage("§7Tode: §e" + Stats.getDeaths(p.getUniqueId().toString()));
/*  35 */             p.sendMessage("§7K/D: §e" + Stats.getKD(p.getUniqueId().toString()));
					  p.sendMessage("§7Rang: §e" + Stats.getRank(p.getUniqueId().toString()));
/*  36 */             p.sendMessage("§7-----[§6Stats » §e" + p.getName() + "§7]-----");
/*  37 */           } else if (args.length == 1) {
/*  38 */             Player target = Bukkit.getPlayer(args[0]);
/*  39 */             if (target != null) {
/*  40 */               p.sendMessage("§7-----[§6Stats » §e" + target.getName() + "§7]-----");
/*  41 */               p.sendMessage("§7Kills: §e" + Stats.getKills(target.getUniqueId().toString()));
/*  42 */               p.sendMessage("§7Tode: §e" + Stats.getDeaths(target.getUniqueId().toString()));
/*  43 */               p.sendMessage("§7K/D: §e" + Stats.getKD(target.getUniqueId().toString()));
						p.sendMessage("§7Rang: §e" + Stats.getRank(target.getUniqueId().toString()));
/*  44 */               p.sendMessage("§7-----[§6Stats » §e" + target.getName() + "§7]-----");
/*     */             } else {
/*  47 */               OfflinePlayer off = Bukkit.getOfflinePlayer(args[0]);
/*  48 */               if (off.isOnline()) {
/*  49 */                 p.sendMessage("§7-----[§6Stats » §e" + off.getName() + "§7]-----");
/*  50 */                 p.sendMessage("§7Kills: §e" + Stats.getKills(off.getUniqueId().toString()));
/*  51 */                 p.sendMessage("§7Tode: §e" + Stats.getDeaths(off.getUniqueId().toString()));
/*  52 */                 p.sendMessage("§7K/D: §e" + Stats.getKD(off.getUniqueId().toString()));
						  p.sendMessage("§7Rang: §e" + Stats.getRank(off.getUniqueId().toString()));
/*  53 */                 p.sendMessage("§7-----[§6Stats » §e" + off.getName() + "§7]-----");
/*  55 */               } else if (off.hasPlayedBefore()) {
/*  56 */                 p.sendMessage("§7-----[§6Stats » §e" + off.getName() + "§7]-----");
/*  57 */                 p.sendMessage("§7Kills: §e" + Stats.getKills(off.getUniqueId().toString()));
/*  58 */                 p.sendMessage("§7Tode: §e" + Stats.getDeaths(off.getUniqueId().toString()));
/*  59 */                 p.sendMessage("§7K/D: §e" + Stats.getKD(off.getUniqueId().toString()));
						  p.sendMessage("§7Rang: §e" + Stats.getRank(off.getUniqueId().toString()));
/*  60 */                 p.sendMessage("§7-----[§6Stats » §e" + off.getName() + "§7]-----");
/*     */               } else {
/*  62 */                 p.sendMessage(String.valueOf(Main.prefix) + "§cDieser Spieler hat §4noch nie §cBuildFFA gespielt!");
/*     */               } 
/*     */             } 
/*     */           } else {
/*  67 */             p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/stats <Name>");
/*     */           } 
/*  69 */         } catch (NullPointerException error) {
/*  70 */           if (p.isOp()) {
/*  71 */             p.sendMessage(String.valueOf(Main.prefix) + "§cVerbinde das Plugin mit MySQL, um Stats zu nutzen!");
/*  72 */             System.err.println("Fehler: " + error.getMessage());
/*     */           } else {
/*  74 */             p.sendMessage(String.valueOf(Main.prefix) + "§cDie Stats sind zur Zeit nicht verfügbar!");
/*     */           } 
/*     */         } 
/*     */       } else {
/* 114 */         p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/stats <Name>");
/*     */       } 
/*     */     } 
/* 118 */     return true;
/*     */   }
/*     */ }
