package de.padel.speedCW.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.padel.speedCW.GamePhase;
import de.padel.speedCW.GameRole;
import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.SpeedPlayer;
import de.padel.speedCW.Manager.InventoryManager;
import de.padel.speedCW.Manager.MapManager;
import de.padel.speedCW.Manager.ScoreboardHandler;
import de.padel.speedCW.Utils.Utils;

public class JoinListener implements Listener{
	
	public static boolean ArmorSpawn = false;
	public static boolean villager = false;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		SpeedPlayer sp = new SpeedPlayer(SpeedCW.manager.spec, 0, p, GameRole.SPEC);
		SpeedCW.manager.addPlayerToTeam(sp, "spec");
		ScoreboardHandler.setScoreboard(p);
		InventoryManager.loadLobbyInv(p);
		e.setJoinMessage(SpeedCW.prefix+"§7Der Spieler §b"+e.getPlayer().getName()+" §7ist beigetreten.");
		if(SpeedCW.manager.phase.equals(GamePhase.INGAME)) {
			p.setGameMode(GameMode.SPECTATOR);
		}else {
			p.setGameMode(GameMode.ADVENTURE);
			if(MapManager.isLocation("LobbySpawn")) {
				new BukkitRunnable() {
					
					@Override
					public void run() {
						p.teleport(MapManager.getLocation("LobbySpawn"));
					}
				}.runTaskLater(SpeedCW.getInstance(), 4);
			}
			if((Bukkit.getOnlinePlayers().size()) == (SpeedCW.cfg.getInt("PlayerPerTeam")*2)) {
				SpeedCW.manager.startLobbyTime();
				SpeedCW.getInstance().setStatus("FULL");
			}
			
		}
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(ArmorSpawn == false) {
					SpeedCW.manager.spawnArmorStands();
					SpeedCW.manager.spawnBeds();
					ArmorSpawn = true;
				}
			}
		}.runTaskLater(SpeedCW.getInstance(), 2);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(villager == false) {
					SpeedCW.getInstance().checkVillager();
					villager = true;
				}
			}
		}.runTaskLater(SpeedCW.getInstance(), 2);
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(SpeedCW.prefix+"§7Der Spieler §b"+p.getName()+" §7hat das Spiel verlassen.");
		if((SpeedCW.manager.phase.equals(GamePhase.INGAME) && SpeedCW.manager.getSpeedPlayer(p).getTeam() != SpeedCW.manager.spec)) {
			SpeedCW.manager.enemy = p;
			SpeedCW.manager.removePlayerFromTeam(SpeedCW.manager.getSpeedPlayer(p).getTeam().getName(), SpeedCW.manager.getSpeedPlayer(p));
			if(SpeedCW.manager.getPlayersFromteam("team1").size() == 0) {
				p.getInventory().clear();	
				new BukkitRunnable() {
					
					@Override
					public void run() {
						SpeedCW.manager.endGame(SpeedCW.manager.Team2);
					}
				}.runTaskLater(SpeedCW.getInstance(), 5);
			}else if(SpeedCW.manager.getPlayersFromteam("team2").size() == 0) {
				p.getInventory().clear();
				new BukkitRunnable() {
					
					@Override
					public void run() {
						SpeedCW.manager.endGame(SpeedCW.manager.Team1);
					}
				}.runTaskLater(SpeedCW.getInstance(), 5);
			}
		} else {
			SpeedCW.getInstance().setStatus("ONLINE");
			SpeedCW.manager.removePlayerFromTeam(SpeedCW.manager.getSpeedPlayer(p).getTeam().getName(), SpeedCW.manager.getSpeedPlayer(p));
		}
		if(Utils.build.contains(p.getUniqueId())) {
			Utils.build.remove(p.getUniqueId());
		}
		if(InteractListener.armors.containsKey(p.getUniqueId())) {
			ArmorStand as = InteractListener.armors.get(p.getUniqueId());
			as.setHelmet(null);
			as.setBoots(null);
			as.setChestplate(null);
			as.setLeggings(null);
			as.setHealth(0);
			as.remove();
		}
	}

}
