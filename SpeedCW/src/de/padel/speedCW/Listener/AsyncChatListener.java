package de.padel.speedCW.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.padel.speedCW.GamePhase;
import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.SpeedPlayer;

public class AsyncChatListener implements Listener{
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		for(SpeedPlayer all : SpeedCW.manager.specs) {
			if(SpeedCW.manager.phase.equals(GamePhase.INGAME)){
				if(p.equals(all.getPlayer())) {
					for(Player player : Bukkit.getOnlinePlayers()) {
						if(SpeedCW.manager.specs.contains(SpeedCW.manager.getSpeedPlayer(player))) {
							e.setCancelled(false);
						}else {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}

}
