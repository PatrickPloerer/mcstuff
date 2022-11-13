package de.padel.buildffa.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.stats.MySQL;
import de.padel.buildffa.utils.InvManager;
import de.padel.buildffa.utils.LocationManager;
import de.padel.buildffa.utils.Utils;

public class JoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage("");
		if(!DeathListener.inventory.contains(p)) {
			DeathListener.inventory.add(p);
		}
		if(Utils.build.contains(p.getUniqueId())) {
			Utils.build.remove(Utils.build.indexOf(p.getUniqueId()));
		}
		if (!MySQL.playerExists(p.getUniqueId().toString())) {
		  MySQL.createPlayer(p.getUniqueId().toString());
		 } 
		if(LocationManager.locationIsExisting("Spawn")) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					p.teleport(LocationManager.getLocation("Spawn"));
					InvManager.setSpawnInv(p);
				}
			}.runTaskLater(Main.getInstance(), 3);
		}else {
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.sendMessage("§cPlease set the Spawn.");
			}
			new BukkitRunnable() {
				
				@Override
				public void run() {
					p.teleport(new Location(Bukkit.getWorld("world"), 0, 0, 0));
					InvManager.setSpawnInv(p);
				}
			}.runTaskLater(Main.getInstance(), 3);
		}
		for (Player all : Bukkit.getOnlinePlayers())
			Utils.updateScoreboard(all); 
	}

}
