package de.padel.speedCW.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.padel.speedCW.GamePhase;
import de.padel.speedCW.GameRole;
import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.SpeedPlayer;
import de.padel.speedCW.Manager.InventoryManager;
import de.padel.speedCW.Manager.MapManager;
import de.padel.speedCW.Manager.MysqlManager;
import de.padel.tab.Main;
import net.minecraft.server.v1_8_R3.EnumChatFormat;

public class DeathListener implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player killer = e.getEntity().getKiller();
		SpeedPlayer sp = SpeedCW.manager.getSpeedPlayer(p);
		e.setDeathMessage("");
		e.getDrops().clear();
		if (killer != null) {
			if (sp.getTeam().isRespawnable()) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.sendMessage(SpeedCW.prefix + "§7Der Spieler §b" + killer.getName() + " §7(§c"
							+ (int) (killer.getHealth() / 2) + "§4❤§7)" + " §7hat §b" + p.getName() + " §7getötet!");
				}
				p.getInventory().clear();
				new BukkitRunnable() {

					@Override
					public void run() {
						p.spigot().respawn();
					}
				}.runTaskLater(SpeedCW.getInstance(), 5);
			} else {
				MysqlManager.addDeath(p.getUniqueId());
				MysqlManager.addKill(killer.getUniqueId());
				if (SpeedCW.manager.phase.equals(GamePhase.INGAME)
						&& SpeedCW.manager.getSpeedPlayer(p).getTeam() != SpeedCW.manager.spec) {
					SpeedCW.manager.enemy = p;
					SpeedCW.manager.removePlayerFromTeam(SpeedCW.manager.getSpeedPlayer(p).getTeam().getName(),
							SpeedCW.manager.getSpeedPlayer(p));
					if (SpeedCW.manager.getPlayersFromteam("team1").size() == 0) {
						p.getInventory().clear();
						new BukkitRunnable() {

							@Override
							public void run() {
								SpeedCW.manager.endGame(SpeedCW.manager.Team2);
							}
						}.runTaskLater(SpeedCW.getInstance(), 5);
					} else if (SpeedCW.manager.getPlayersFromteam("team2").size() == 0) {
						p.getInventory().clear();
						new BukkitRunnable() {

							@Override
							public void run() {
								SpeedCW.manager.endGame(SpeedCW.manager.Team1);
							}
						}.runTaskLater(SpeedCW.getInstance(), 5);
					}
				} else {
					SpeedCW.manager.removePlayerFromTeam(SpeedCW.manager.getSpeedPlayer(p).getTeam().getName(),
							SpeedCW.manager.getSpeedPlayer(p));
				}
				new BukkitRunnable() {

					@Override
					public void run() {
						SpeedCW.manager.addPlayerToTeam(new SpeedPlayer(SpeedCW.manager.spec, 0, p, GameRole.SPEC),
								"spec");
						p.spigot().respawn();

					}
				}.runTaskLater(SpeedCW.getInstance(), 5);
			}
		} else {
			if (sp.getTeam().isRespawnable()) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.sendMessage(SpeedCW.prefix + "§7Der Spieler §b" + p.getName() + " §7ist gestorben!");
				}
				p.getInventory().clear();
				new BukkitRunnable() {

					@Override
					public void run() {
						p.spigot().respawn();
					}
				}.runTaskLater(SpeedCW.getInstance(), 5);
			} else {
				MysqlManager.addDeath(p.getUniqueId());
				if (SpeedCW.manager.phase.equals(GamePhase.INGAME)
						&& SpeedCW.manager.getSpeedPlayer(p).getTeam() != SpeedCW.manager.spec) {
					SpeedCW.manager.enemy = p;
					SpeedCW.manager.removePlayerFromTeam(SpeedCW.manager.getSpeedPlayer(p).getTeam().getName(),
							SpeedCW.manager.getSpeedPlayer(p));
					if (SpeedCW.manager.getPlayersFromteam("team1").size() == 0) {
						p.getInventory().clear();
						new BukkitRunnable() {

							@Override
							public void run() {
								SpeedCW.manager.endGame(SpeedCW.manager.Team2);
							}
						}.runTaskLater(SpeedCW.getInstance(), 5);
					} else if (SpeedCW.manager.getPlayersFromteam("team2").size() == 0) {
						p.getInventory().clear();
						new BukkitRunnable() {

							@Override
							public void run() {
								SpeedCW.manager.endGame(SpeedCW.manager.Team1);
							}
						}.runTaskLater(SpeedCW.getInstance(), 5);
					}
				} else {
					SpeedCW.manager.removePlayerFromTeam(SpeedCW.manager.getSpeedPlayer(p).getTeam().getName(),
							SpeedCW.manager.getSpeedPlayer(p));

				}
				new BukkitRunnable() {

					@Override
					public void run() {
						SpeedCW.manager.addPlayerToTeam(new SpeedPlayer(SpeedCW.manager.spec, 0, p, GameRole.SPEC),
								"spec");
						p.spigot().respawn();

					}
				}.runTaskLater(SpeedCW.getInstance(), 5);
			}
		}

	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		p.setHealth(20);
		SpeedPlayer sp = SpeedCW.manager.getSpeedPlayer(p);
		if (sp.getTeam().equals(SpeedCW.manager.Team1)) {
			new BukkitRunnable() {

				@Override
				public void run() {
					p.getInventory().clear();
					p.teleport(MapManager.getLocation("Team1Spawn"));
					p.getInventory().setChestplate(new ItemStack(Material.AIR));
					p.getInventory().setBoots(new ItemStack(Material.AIR));
					p.getInventory().setHelmet(new ItemStack(Material.AIR));
					p.getInventory().setLeggings(new ItemStack(Material.AIR));
					InventoryManager.loadGameInv(p);
				}
			}.runTaskLater(SpeedCW.getInstance(), 5);
		} else if (sp.getTeam().equals(SpeedCW.manager.Team2)) {
			new BukkitRunnable() {

				@Override
				public void run() {
					p.getInventory().clear();
					p.teleport(MapManager.getLocation("Team2Spawn"));
					p.getInventory().setChestplate(new ItemStack(Material.AIR));
					p.getInventory().setBoots(new ItemStack(Material.AIR));
					p.getInventory().setHelmet(new ItemStack(Material.AIR));
					p.getInventory().setLeggings(new ItemStack(Material.AIR));
					InventoryManager.loadGameInv(p);
				}
			}.runTaskLater(SpeedCW.getInstance(), 5);
		} else if (sp.getTeam().equals(SpeedCW.manager.spec)) {
			p.setGameMode(GameMode.SPECTATOR);
			new BukkitRunnable() {

				@Override
				public void run() {
					p.teleport(MapManager.getLocation("SpecSpawn"));
				}
			}.runTaskLater(SpeedCW.getInstance(), 2);
		} else {
			p.setGameMode(GameMode.SPECTATOR);
			new BukkitRunnable() {

				@Override
				public void run() {
					p.teleport(MapManager.getLocation("SpecSpawn"));
				}
			}.runTaskLater(SpeedCW.getInstance(), 2);
		}
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (SpeedCW.manager.getSpeedPlayer(all).getTeam().equals(SpeedCW.manager.Team1)) {
				Main.getInstance().tM.changeTeam(all, "§1", EnumChatFormat.DARK_BLUE,
						(Main.isUsingBAC.get(all.getUniqueId()) ? " §4✔" : ""), 0);
			} else if (SpeedCW.manager.getSpeedPlayer(all).getTeam().equals(SpeedCW.manager.Team2)) {
				Main.getInstance().tM.changeTeam(all, "§c", EnumChatFormat.DARK_RED,
						(Main.isUsingBAC.get(all.getUniqueId()) ? " §4✔" : ""), 1);
			} else {
				Main.getInstance().tM.changeTeam(all, "§7", EnumChatFormat.GRAY,
						(Main.isUsingBAC.get(all.getUniqueId()) ? " §4✔" : ""), 2);
			}
			Main.getInstance().tM.update();
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (SpeedCW.manager.phase.equals(GamePhase.LOBBY) || SpeedCW.manager.phase.equals(GamePhase.RESTART)) {
			if (p.getLocation().getY() <= 50.0) {
				p.teleport(MapManager.getLocation("LobbySpawn"));
			}
		}
		if (SpeedCW.manager.phase.equals(GamePhase.INGAME)) {
			if (p.getLocation().getY() < MapManager.getLocation("DeathHeight").getY()) {
				p.setHealth(0);
				p.spigot().respawn();
				return;
			}
		}
	}

	@EventHandler
	public void ArmorStandDestroy(EntityDamageByEntityEvent e) {
		if (e.getEntity().getType().equals(EntityType.VILLAGER)) {
			e.setCancelled(true);
		}
		if (!(e.getEntity() instanceof LivingEntity)) {
			return;
		}

		final LivingEntity livingEntity = (LivingEntity) e.getEntity();
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			if (SpeedCW.manager.getSpeedPlayer((Player) e.getDamager()).getTeam()
					.equals(SpeedCW.manager.getSpeedPlayer((Player) e.getEntity()).getTeam())) {
				e.setCancelled(true);
			}
		}
		if (e.getDamager() instanceof Player && livingEntity.getType().equals(EntityType.ARMOR_STAND)) {
			e.setCancelled(true);
			return;

		}
		if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
			Arrow arr = (Arrow) e.getDamager();
			if (e.getEntity() == arr.getShooter()) {
				e.setCancelled(true);
			}
		}
	}

}
