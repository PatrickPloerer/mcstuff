package de.padel.buildffa.utils;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.stats.Stats;

public class Utils {
	  
	  public static ArrayList<UUID> build = new ArrayList<>();
	  
	  private static int count = 0;
	  
	  /*  33 */   private static String[] animation = new String[] { "§l§eBuildFFA", "§l§e§6B§l§euildFFA", "§l§eB§6u§l§eildFFA", "§l§eBu§l§6i§eldFFA", "§l§eBui§6l§l§edFFA", "§l§eBuil§6d§l§eFFA", "§l§eBuild§6§lFFA", "§l§eBuild§6§lFFA", "§l§eBuildFFA" };
	  /*     */   
	  /*     */   public void startAnimation() {
	  /*  37 */     Bukkit.getScheduler().runTaskTimer((Plugin)Main.getInstance(), new Runnable() {
	  /*     */           public void run() {
	  /*  42 */             Bukkit.getOnlinePlayers().forEach(current -> {
	  /*     */                   if (current.getScoreboard() == null)
	  /*     */                     Utils.updateScoreboard(current); 
	  /*     */                   current.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(Utils.animation[Utils.count]);
	  /*     */                 });
	  /*  51 */             Utils.count = Utils.count + 1;
	  /*  53 */             if (Utils.count == Utils.animation.length)
	  /*  54 */               Utils.count = 0; 
	  /*     */           }
	  /*  58 */         },  0L, 20L);
	  /*     */   }
	  /*     */   
	  /*     */   public static void updateScoreboard(Player p) {
	  /*  63 */     ScoreboardManager sm = Bukkit.getScoreboardManager();
	  /*  64 */     Scoreboard board = sm.getNewScoreboard();
	  /*  65 */     Objective o = board.registerNewObjective("aaa", "dummy");
	  /*  67 */     o.setDisplaySlot(DisplaySlot.SIDEBAR);
	  /*  68 */     o.setDisplayName(animation[count]);
	  /*  70 */     o.getScore("§f§lMap:").setScore(10);
	  /*  71 */     if (Main.cfg.getString("Config.Mapname") != null) {
	  /*  72 */       o.getScore("§7◯ §c" + Main.cfg.getString("Config.Mapname")).setScore(9);
	  /*     */     } else {
	  /*  74 */       o.getScore("§7◯ §7Unbekannt").setScore(9);
	  /*     */     } 
	  /*  76 */     o.getScore("§7 ").setScore(8);
	  /*  77 */     o.getScore("§f§lKills:").setScore(7);
	  /*  79 */       o.getScore("§7◯ §c" + Stats.getKills(p.getUniqueId().toString())).setScore(6);
	  /*  83 */     o.getScore("§c ").setScore(5);
	  /*  84 */     o.getScore("§f§lSpieler:").setScore(4);
	  /*  85 */     o.getScore("§7◯ §c" + Bukkit.getOnlinePlayers().size()).setScore(3);
	  /*  86 */     o.getScore("§6 ").setScore(2);
	  /*  87 */     o.getScore("§f§lIP:").setScore(1);
	  /*  88 */     o.getScore("§7◯ §cPenletter.de").setScore(0);
	  /*  89 */     p.setScoreboard(board);
	  /*     */   }
	  /*     */   
	  /*     */   public static void updateScoreboard(Player p, int players) {
	  /*  94 */     ScoreboardManager sm = Bukkit.getScoreboardManager();
	  /*  95 */     Scoreboard board = sm.getNewScoreboard();
	  /*  96 */     Objective o = board.registerNewObjective("aaa", "dummy");
	  /*  98 */     o.setDisplaySlot(DisplaySlot.SIDEBAR);
	  /*  99 */     o.setDisplayName(animation[count]);
	  /* 101 */     o.getScore("§f§lMap:").setScore(10);
	  /* 102 */     if (Main.cfg.getString("Config.Mapname") != null) {
	  /* 103 */       o.getScore("§7◯ §c" + Main.cfg.getString("Config.Mapname")).setScore(9);
	  /*     */     } else {
	  /* 105 */       o.getScore("§7◯ §7Unbekannt").setScore(9);
	  /*     */     } 
	  /* 107 */     o.getScore("§7 ").setScore(8);
	  /* 108 */     o.getScore("§f§lKills:").setScore(7);
	  /* 110 */       o.getScore("§7◯ §c" + Stats.getKills(p.getUniqueId().toString())).setScore(6);
	  /* 114 */     o.getScore("§c ").setScore(5);
	  /* 115 */     o.getScore("§f§lSpieler:").setScore(4);
	  /* 116 */     o.getScore("§7◯ §c" + players).setScore(3);
	  /* 117 */     o.getScore("§6 ").setScore(2);
	  /* 118 */     o.getScore("§f§lIP:").setScore(1);
	  /* 119 */     o.getScore("§7◯ §cPenletter.de").setScore(0);
	  /* 121 */     p.setScoreboard(board);
	  /*     */   }
	  public static String compile(String text) {
		  text = text.replace("&", "§");
		  
		  return text;
	  }
	}

