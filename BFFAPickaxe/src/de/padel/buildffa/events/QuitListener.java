package de.padel.buildffa.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.stats.Stats;
import de.padel.buildffa.utils.InvManager;
import de.padel.buildffa.utils.Utils;
import de.padel.coinapi.main.CoinAPI;

public class QuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage("");
		Player p = e.getPlayer();
		if(DeathListener.damage.containsKey(p)) {
			BukkitTask run = DeathListener.damage.get(p);
			run.cancel();
			DeathListener.damage.remove(p);
		}
		if (DeathListener.damagedPlayers.containsKey(p)) {
			Player killer = DeathListener.damagedPlayers.get(p);
			// Killstreak
			if (DeathListener.killstreak.containsKey(killer)) {
				DeathListener.killstreak.replace(killer, DeathListener.killstreak.get(killer),
						(DeathListener.killstreak.get(killer) + 1));
				if (DeathListener.killstreak.get(killer) == 3 || DeathListener.killstreak.get(killer) == 5
						|| DeathListener.killstreak.get(killer) == 10 || DeathListener.killstreak.get(killer) == 15
						|| DeathListener.killstreak.get(killer) == 20 || DeathListener.killstreak.get(killer) == 25) {
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(Main.prefix + "§7Der Spieler §6" + killer.getName() + " §7hat eine §c"
								+ DeathListener.killstreak.get(killer) + " §7Killstreak!");
					}
				}
				if (DeathListener.killstreak.get(killer) == 2) {
					Inventory killerInv = InvManager.loadPlayerinv(killer);
					int Slot = 0;
					boolean stop = false;
					for (ItemStack i : killerInv.getContents()) {
						if (stop) {

						} else {
							if (i != null && i.getType() != null && i.getType().equals(Material.ENDER_PEARL)) {
								stop = true;
								killer.getInventory().setItem(Slot, i);
							} else {
								Slot++;
							}
						}
					}

				}
			} else {
				DeathListener.killstreak.put(killer, 1);
			}
			if (DeathListener.killstreak.containsKey(p)) {
				if (DeathListener.killstreak.get(p) == 3 || DeathListener.killstreak.get(p) == 5
						|| DeathListener.killstreak.get(p) == 10 || DeathListener.killstreak.get(p) == 15
						|| DeathListener.killstreak.get(p) == 20 || DeathListener.killstreak.get(p) == 25) {
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(
								Main.prefix + "§7Der Spieler §6" + p.getName() + " §7hat keine Killstreak mehr!");
					}
				}
				DeathListener.killstreak.remove(p);
			}
			// Coins + DeathMessage
			CoinAPI.addCoins(killer.getUniqueId().toString(), 2);
			killer.sendMessage(Main.prefix + "§7Du hast §6" + p.getName() + " §7getötet!");
			killer.sendMessage(Main.prefix + "§7Du hast §62 Coins erhalten.");

			// Stats
			Stats.addDeath(p.getUniqueId().toString());
			Stats.addKill(killer.getUniqueId().toString());

			// remove from HashMaps
			DeathListener.damagedPlayers.remove(p);

			// some things to set
			killer.setHealth(20);
			Utils.updateScoreboard(killer, Bukkit.getOnlinePlayers().size() + 1);
			killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 2, 2);
		}
		if (DeathListener.killstreak.containsKey(p)) {
			DeathListener.killstreak.remove(p);
		}
		if(Utils.build.contains(p.getUniqueId())) {
			Utils.build.remove(Utils.build.indexOf(p.getUniqueId()));
		}
		if(DeathListener.inventory.contains(p)) {
			DeathListener.inventory.remove(DeathListener.inventory.indexOf(p));
		}
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.sendMessage(Main.prefix + "§7Der Spieler §6" + p.getName() + " §7hat das Spiel verlassen.");
		}
	}

}
