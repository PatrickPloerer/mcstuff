package de.padel.speedCW.Manager;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import de.padel.speedCW.SpeedCW;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class ScoreboardHandler {

	static int sek = 0;
	static int min = 0;
	static int hour = 0;

	public static void startAnimation() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player current : Bukkit.getOnlinePlayers()) {
					showActionbar(current, (hour > 9 ? hour : "0"+hour)+":"+(min > 9 ? min : "0"+min)+":"+(sek > 9 ? sek : "0"+sek));
				}
				sek++;
				if(sek == 60) {
					min++;
					sek = 0;
				}
				if(min == 60) {
					hour++;
					min = 0;
				}
			}
		}.runTaskTimer(SpeedCW.getInstance(), 0, 20);
	}
	public static void setScoreboard(Player p) {
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard sb = sm.getNewScoreboard();
		Objective o = sb.registerNewObjective("aaa", "dummy");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		Team blue = sb.registerNewTeam("blue");
		Team red = sb.registerNewTeam("red");
		Team self = sb.registerNewTeam("self");
		o.setDisplayName("§b§lSpeedCW §7- §c"+SpeedCW.manager.phase);
		o.getScore("§6     ").setScore(9);
		o.getScore("§f§lTeams:").setScore(8);
		blue.addEntry(" §1Blau");
		blue.setPrefix("");
		blue.setSuffix("");
		red.addEntry(" §cRot");
		red.setPrefix("");
		red.setSuffix("");
		self.addEntry("§7◯ ");
		self.setPrefix("");
		self.setSuffix("");
		o.getScore(" §1Blau").setScore(7);
		o.getScore(" §cRot").setScore(6);
		o.getScore("§6  ").setScore(5);
		o.getScore("§f§lTeam:").setScore(4);
		o.getScore("§7◯ ").setScore(3);
		o.getScore("§6 ").setScore(2);
		o.getScore("§f§lIP:").setScore(1);
		o.getScore("§7◯ §cPenletter.de").setScore(0);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(SpeedCW.manager.Team1.isRespawnable()) {
					blue.setPrefix("§a✓");
				}else {
					blue.setPrefix("§c✘");
				}
				if(SpeedCW.manager.Team2.isRespawnable()) {
					red.setPrefix("§a✓");
				}else {
					red.setPrefix("§c✘");
				}
				if(SpeedCW.manager.getSpeedPlayer(p).getTeam() != SpeedCW.manager.spec) {
					if(SpeedCW.manager.getSpeedPlayer(p).getTeam() == SpeedCW.manager.Team1) {
						self.setSuffix("§1Blau");
					}else {
						self.setSuffix("§cRot");	
					}
				}else {
					self.setSuffix("§8Zuschauer");
				}
				o.setDisplayName("§b§lSpeedCW §7- §c"+SpeedCW.manager.phase);
			}
		}.runTaskTimer(SpeedCW.getInstance(), 0, 20);
		
		p.setScoreboard(sb);
	}
	public static String compile(String text) {
		text = text.replace("&", "§");

		return text;
	}
	public static void showActionbar(Player player, String text) {
		PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

}
