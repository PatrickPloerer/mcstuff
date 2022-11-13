package de.padel.buildffa.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.stats.InvMysql;
import de.padel.buildffa.stats.Stats;
import de.padel.buildffa.utils.InvManager;
import de.padel.buildffa.utils.LocationManager;
import de.padel.buildffa.utils.Utils;
import de.padel.coinapi.main.CoinAPI;

public class DeathListener implements Listener {

	public static HashMap<Player, Player> damagedPlayers = new HashMap<>();
	public static HashMap<Player, BukkitTask> damage = new HashMap<>();
	
	public static HashMap<Player, Integer> killstreak = new HashMap<>();
	public static ArrayList<Player> inventory = new ArrayList<>();
	
	public static HashMap<UUID, Inventory> PlayerInventorys = new HashMap<>();
	
	public HashMap<UUID, EnderPearl> eps = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		e.setDroppedExp(0);
		e.getDrops().clear();
		if (damagedPlayers.containsKey(p)) {
			Player killer = damagedPlayers.get(p);
			// Killstreak
			if (killstreak.containsKey(killer)) {
				killstreak.replace(killer, killstreak.get(killer), (killstreak.get(killer) + 1));
				if (killstreak.get(killer) == 3 || killstreak.get(killer) == 5 || killstreak.get(killer) == 10
						|| killstreak.get(killer) == 15 || killstreak.get(killer) == 20
						|| killstreak.get(killer) == 25) {
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(Main.prefix + "§7Der Spieler §6" + killer.getName() + " §7hat eine §c"
								+ killstreak.get(killer) + " §7Killstreak!");
					}
				}
				if (killstreak.get(killer) == 2) {
					Inventory killerInv = InvManager.loadPlayerinv(killer);
					int Slot = 0;
					boolean stop = false;
					for (ItemStack i : killerInv.getContents()) {
						if (stop) {

						} else {
							if(i != null && i.getType() != null) {
								if (i.getType().equals(Material.ENDER_PEARL)) {
									stop = true;
									killer.getInventory().remove(Slot);
									i.setAmount(1);
									killer.getInventory().setItem(Slot, i);
								}
							}
							Slot++;
						}
					}

				}
			} else {
				killstreak.put(killer, 1);
			}
			if (killstreak.containsKey(p)) {
				if (killstreak.get(p) == 3 || killstreak.get(p) == 5 || killstreak.get(p) == 10
						|| killstreak.get(p) == 15 || killstreak.get(p) == 20 || killstreak.get(p) == 25) {
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(
								Main.prefix + "§7Der Spieler §6" + p.getName() + " §7hat keine Killstreak mehr!");
					}
				}
				killstreak.remove(p);
			}
			if(killstreak.get(killer) == 3 || killstreak.get(killer) == 5 || killstreak.get(killer) == 10
					|| killstreak.get(killer) == 15 || killstreak.get(killer) == 20 || killstreak.get(killer) == 25) {
				CoinAPI.addCoins(killer.getUniqueId().toString(), killstreak.get(killer));
				if(killstreak.get(killer) == 5) {
					killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 0));
				}
				if(killstreak.get(killer) == 10) {
					killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 0));
				}
			}else {
				CoinAPI.addCoins(killer.getUniqueId().toString(), 2);
			}
			// DeathMessage
			killer.sendMessage(Main.prefix + "§7Du hast §6" + p.getName() +" §7mit §7(§c"+(int) (killer.getHealth()/2) +"§4❤§7)" + " §7getötet!");
			killer.sendMessage(Main.prefix + "§7Du hast §62 Coins erhalten.");
			p.sendMessage(Main.prefix + "§7Du wurdest von §c" + killer.getName() + " §7(§c"
					+ (int) (killer.getHealth() / 2) + "§4❤§7)" + " §7getötet!");

			// Stats
			Stats.addDeath(p.getUniqueId().toString());
			Stats.addKill(killer.getUniqueId().toString());

			// remove from HashMaps
			// some things to set
			killer.setHealth(20);
			 Utils.updateScoreboard(killer);
			 Utils.updateScoreboard(p);
			 
			
			killer.playSound(killer.getLocation(), getAktivSound(killer), 2, 2);

			// respawn
			Vector vec = new Vector(0, 0, 0);
			p.setVelocity(vec);
			p.spigot().respawn();
		
			damagedPlayers.remove(p);
			if(eps.containsKey(p.getUniqueId())) {
				EnderPearl ep = eps.get(p.getUniqueId());
				ep.remove();
				eps.remove(p.getUniqueId());
			}
		} else {
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.sendMessage(Main.prefix + "§7Der Spieler §6" + p.getName() + " §7ist gestorben!");
			}
			if(eps.containsKey(p.getUniqueId())) {
				EnderPearl ep = eps.get(p.getUniqueId());
				ep.remove();
				eps.remove(p.getUniqueId());
			}
			// Stats
			Stats.addDeath(p.getUniqueId().toString());
			Vector vec = new Vector(0, 0, 0);
			p.setVelocity(vec);
			p.spigot().respawn();
		}

	}
	public Sound getAktivSound(Player p) {
		switch(InvMysql.getAktivSound(p.getUniqueId().toString())) {
		case 0:
			return Sound.LEVEL_UP;
		case 1:
			return Sound.VILLAGER_DEATH;
		case 2:
			return Sound.ANVIL_BREAK;
		case 3:
			return Sound.ITEM_BREAK;
		case 4:
			return Sound.AMBIENCE_THUNDER;
		default:
			return Sound.LEVEL_UP;
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (Utils.build.contains(p.getUniqueId())) {
			return;
		} else if (p.getLocation().getY() < LocationManager.getHeight()) {
			p.setHealth(0);
			p.spigot().respawn();
			return;
		} else if (p.getLocation().getY() < LocationManager.getSpawnHeight()
				&& p.getLocation().getY() > (LocationManager.getSpawnHeight() - 3)) {
			if(inventory.contains(p)) {
				p.closeInventory();
				p.getInventory().clear();
				new BukkitRunnable() {
					
					@Override
					public void run() {
						p.getInventory().clear();
					}
				}.runTaskLater(Main.getInstance(), 3);
				new BukkitRunnable() {
					
					@Override
					public void run() {
						InvManager.setGameInv(p);
						inventory.remove(p);
					}
				}.runTaskLater(Main.getInstance(), 6);
			}
			return;
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		p.setHealth(20);
		p.setFoodLevel(20);
		Vector vec = new Vector(0, 0, 0);
		p.setVelocity(vec);
		new BukkitRunnable() {

			@Override
			public void run() {
				p.closeInventory();
				p.getInventory().clear();
				InvManager.setSpawnInv(p);
			}
		}.runTaskLater(Main.getInstance(), 1);
		p.teleport(LocationManager.getLocation("Spawn"));
		p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 2.0F, 2.0F);
		if(!inventory.contains(p)) {
			inventory.add(p);
		}
	}

	@EventHandler
	public void onDMG(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player)
			if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
				e.setCancelled(true);
			} else if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
				e.setCancelled(true);
			}else if(e.getEntity().getLocation().getY() >= (LocationManager.getSpawnHeight() - 4)) {
				e.setCancelled(true);
			}	else {
				e.setCancelled(false);
			}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player attacker = (Player) e.getDamager();
			Player hitted = (Player) e.getEntity();
			if (attacker.getLocation().getY() >= (LocationManager.getSpawnHeight() - 4)) {
				e.setCancelled(true);
				return;
			} else if (hitted.getLocation().getY() >= (LocationManager.getSpawnHeight() - 4)) {
				e.setCancelled(true);
				return;
			} else {
				if (!damagedPlayers.containsKey(hitted)) {
					if(damage.containsKey(hitted)) {
						BukkitTask run = damage.get(hitted);
						run.cancel();
						damage.remove(hitted);
					}
					damagedPlayers.put(hitted, attacker);
					BukkitTask run = new BukkitRunnable() {

						@Override
						public void run() {
							if (damagedPlayers.containsKey(hitted)) {
								damagedPlayers.remove(hitted);
							}
						}
					}.runTaskLater(Main.getInstance(), 160);
					damage.put(hitted, run);
				} else {
					if(damage.containsKey(hitted)) {
						BukkitTask run = damage.get(hitted);
						run.cancel();
						damage.remove(hitted);
					}
					damagedPlayers.remove(hitted);
					damagedPlayers.put(hitted, attacker);
					BukkitTask run = new BukkitRunnable() {

						@Override
						public void run() {
							if (damagedPlayers.containsKey(hitted)) {
								damagedPlayers.remove(hitted);
							}
						}
					}.runTaskLater(Main.getInstance(), 160);
					damage.put(hitted, run);
				}
			}
		} else {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onEnderPearlThrow(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof EnderPearl) {
			EnderPearl ep = (EnderPearl) e.getEntity();
			Player p = (Player) ep.getShooter();
			eps.put(p.getUniqueId(), ep);
		}
	}

}
