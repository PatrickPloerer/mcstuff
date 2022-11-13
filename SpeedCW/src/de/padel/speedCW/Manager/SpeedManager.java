package de.padel.speedCW.Manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.padel.speedCW.GamePhase;
import de.padel.speedCW.GameRole;
import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.SpeedPlayer;
import de.padel.speedCW.SpeedTeam;
import de.padel.speedCW.Utils.Utils;
import de.padel.tab.Main;
import net.minecraft.server.v1_8_R3.EnumChatFormat;

public class SpeedManager {
	

	public Player enemy = null;
	public ArrayList<SpeedPlayer> team1;
	public ArrayList<SpeedPlayer> team2;
	public ArrayList<SpeedPlayer> specs;
	public SpeedTeam Team1;
	public SpeedTeam Team2;
	public SpeedTeam spec;
	public int LobbyTime;
	public GamePhase phase;
	public Location team1ArmorStand;
	public Location team2ArmorStand;
	
	public boolean timerRunning = false;
	
	public HashMap<String, Block> beds = new HashMap<>();
	
	public SpeedManager(SpeedTeam Team1, SpeedTeam Team2, int LobbyTime, GamePhase phase) {
		this.Team1 = Team1;
		this.Team2 = Team2;
		this.LobbyTime = LobbyTime;
		this.phase = phase;
		team1ArmorStand = MapManager.getLocation("SelectTeam1");
		team2ArmorStand = MapManager.getLocation("SelectTeam2");
		Location loc = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		spec = new SpeedTeam("spec", Color.GRAY, true, MapManager.getLocation("SpecSpawn"), loc, loc, loc);
		specs = new ArrayList<>();
		team2 = new ArrayList<>();
		team1 = new ArrayList<>();
	}
	
	public void addPlayerToTeam(SpeedPlayer player, String team) {
		if(team.contains("team1")) {
			if(!team1.contains(player)) {
				team1.add(player);
			}
		}else if(team.contains("team2")) {
			if(!team2.contains(player)) {
				team2.add(player);
			}
		}else if(team.contains("spec")) {
			if(!specs.contains(player)) {
				specs.add(player);
			}
		}
	}
	public void setPlayerTeam(Player player, String team) {
		if(team.contains("team1")) {
			if(!team1.contains(getSpeedPlayer(player))) {
				team1.add(new SpeedPlayer(Team1, 0, player, GameRole.PLAYER));
			}
			if(team2.contains(getSpeedPlayer(player))) {
				team2.remove(getSpeedPlayer(player));
			}
			if(specs.contains(getSpeedPlayer(player))) {
				specs.remove(getSpeedPlayer(player));
			}
		}else if(team.contains("team2")) {
			if(!team2.contains(getSpeedPlayer(player))) {
				team2.add(new SpeedPlayer(Team2, 0, player, GameRole.PLAYER));
			}
			if(specs.contains(getSpeedPlayer(player))) {
				specs.remove(getSpeedPlayer(player));
			}
			if(team1.contains(getSpeedPlayer(player))) {
				team1.remove(getSpeedPlayer(player));
			}
		}else if(team.contains("spec")) {
			if(!specs.contains(getSpeedPlayer(player))) {
				specs.add(new SpeedPlayer(spec, 0, player, GameRole.SPEC));
			}
			if(team1.contains(getSpeedPlayer(player))) {
				team1.remove(getSpeedPlayer(player));
			}
			if(team2.contains(getSpeedPlayer(player))) {
				team2.remove(getSpeedPlayer(player));
			}
		}
	}
	public ArrayList<SpeedPlayer> getPlayersFromteam(String team) {
		if(team.contains("team1")) {
			return team1;
		}else if(team.contains("team2")){
			return team2;
		}else {
			return specs;
		}
	}
	public void removePlayerFromTeam(String team, SpeedPlayer player) {
		if(team.contains("team1")) {
			if(team1.contains(player)) {
				team1.remove(player);
			}
		}else if(team.contains("team2")){
			if(team2.contains(player)) {
				team2.remove(player);
			}
		}else if(team.contains("spec")) {
			if(specs.contains(player)) {
				specs.remove(player);
			}
		}
	}
	public SpeedPlayer getSpeedPlayer(Player p) {
		SpeedPlayer player = null;
		if(team1.size() > 0) {
			for(SpeedPlayer f : team1) {
				if(f.getPlayer().getUniqueId().equals(p.getUniqueId())) {
					player = f;
				}
			}
		}
		if(team2.size() > 0) {
			for(SpeedPlayer f : team2) {
				if(f.getPlayer().getUniqueId().equals(p.getUniqueId())) {
					player = f;
				}
			}	
		}
		if(specs.size() > 0) {
			for(SpeedPlayer f : specs) {
				if(f.getPlayer().getUniqueId().equals(p.getUniqueId())) {
					player = f;
				}
			}
		}
		return player;
	}

	public void startLobbyTime() {
		if(!timerRunning) {
			timerRunning = true;
			new BukkitRunnable() {
				int i = LobbyTime;
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					if(i == 0) {
						if(Bukkit.getOnlinePlayers().size() >= (SpeedCW.cfg.getInt("PlayerPerTeam")*2)) {
							startGame();
							this.cancel();
						}else{
							this.cancel();
							for(Player all : Bukkit.getOnlinePlayers()) {
								all.sendMessage(SpeedCW.prefix+"§7Es sind nicht genug Spieler online!");
								all.playSound(all.getLocation(), Sound.VILLAGER_DEATH, 2, 2);
								timerRunning = false;
							}
						}
					}else if(i == 30) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(SpeedCW.prefix+"§7Noch §b30 §7Sekunden bis zum Spielstart");
							all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
						}
					}else if(i == 20) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(SpeedCW.prefix+"§7Noch §b20 §7Sekunden bis zum Spielstart");
							all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
						}
					}else if(i == 10) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(SpeedCW.prefix+"§7Noch §b10 §7Sekunden bis zum Spielstart");
							all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
						}
					}else if(i == 5) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendTitle("§7Noch §b5 §7Sekunden", "§7bis zum Spielstart");
							all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
						}
					}else if(i == 4) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendTitle("§7Noch §b4 §7Sekunden", "§7bis zum Spielstart");
							all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
						}
					}else if(i == 3) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendTitle("§7Noch §b3 §7Sekunden", "§7bis zum Spielstart");
							all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
						}
					}else if(i == 2) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendTitle("§7Noch §b2 §7Sekunden", "§7bis zum Spielstart");
							all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
						}
					}else if(i == 1) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendTitle("§7Noch §beine §7Sekunde", "§7bis zum Spielstart");
							all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
						}
					}
					i--;
				}
			}.runTaskTimer(SpeedCW.getInstance(), 0, 20);
		}
	}
	@SuppressWarnings("deprecation")
	public void startGame() {
		phase = GamePhase.INGAME;
		SpeedCW.getInstance().setStatus("INGAME");
		SpeedCW.getInstance().setMaxPlayers(16);
		boolean tea1 = false;
		boolean tea2 = false;
		if(team1.size() < SpeedCW.cfg.getInt("PlayerPerTeam")) {
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(tea1 == false) {
					if(getSpeedPlayer(all).getTeam() != Team1 && getSpeedPlayer(all).getTeam() != Team2) {
						setPlayerTeam(all, "team1");
						tea1 = true;
					}
				}
			}
		}
		if(team2.size() < SpeedCW.cfg.getInt("PlayerPerTeam")) {
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(tea2 == false) {
					if(getSpeedPlayer(all).getTeam() != Team1 && getSpeedPlayer(all).getTeam() != Team2) {
						setPlayerTeam(all, "team2");
						tea2 = true;
					}
				}
			}
		}
		for(SpeedPlayer sp : getPlayersFromteam("team1")) {
			sp.getPlayer().closeInventory();
			InventoryManager.loadGameInv(sp.getPlayer());
			Player p = sp.getPlayer();
			p.setGameMode(GameMode.SURVIVAL);
			p.setFallDistance(0);
			p.teleport(MapManager.getLocation("Team1Spawn"));
			p.sendTitle("§1Team Blau", "§bViel Glück!");
		}
		for(SpeedPlayer sp : getPlayersFromteam("team2")) {
			sp.getPlayer().closeInventory();
			InventoryManager.loadGameInv(sp.getPlayer());
			Player p = sp.getPlayer();
			p.setGameMode(GameMode.SURVIVAL);
			p.setFallDistance(0);
			p.teleport(MapManager.getLocation("Team2Spawn"));
			p.sendTitle("§cTeam Rot", "§bViel Glück!");
		}
		for(SpeedPlayer sp : getPlayersFromteam("spec")) {
			sp.getPlayer().closeInventory();
			Player p = sp.getPlayer();
			p.sendTitle("§7Spectator", "");
			p.setFallDistance(0);
			p.teleport(MapManager.getLocation("SpecSpawn"));
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(specs.contains(getSpeedPlayer(all))) {
					all.showPlayer(p);
				}else {
					all.hidePlayer(p);
				}
			}
		}
		for(Player all : Bukkit.getOnlinePlayers()) {
			String bac = "";
			if(Main.isUsingBAC.containsKey(all.getUniqueId())) {
				bac = " §e✔";
			}
			if(SpeedCW.manager.getSpeedPlayer(all).getTeam().equals(Team1)) {
				Main.getInstance().tM.changeTeam(all, "§1", EnumChatFormat.DARK_BLUE, bac, 0);
			}else if(SpeedCW.manager.getSpeedPlayer(all).getTeam().equals(Team2)) {
				Main.getInstance().tM.changeTeam(all, "§c", EnumChatFormat.DARK_RED, bac, 1);
			}else {
				Main.getInstance().tM.changeTeam(all, "§7", EnumChatFormat.GRAY, bac, 2);
			}
		}
		startSpawner((Player) Bukkit.getOnlinePlayers().toArray()[0]);
		ScoreboardHandler.startAnimation();
		if(team1.size() < SpeedCW.cfg.getInt("PlayerPerTeam")) {
			endGame(Team2);
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.sendMessage(SpeedCW.prefix+ "§7Es war kein Spieler in Team Rot");
			}
		}
		if(team2.size() < SpeedCW.cfg.getInt("PlayerPerTeam")) {
			endGame(Team1);
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.sendMessage(SpeedCW.prefix+ "§7Es war kein Spieler in Team Blau");
			}
		}
	}
	public void endGame(SpeedTeam winner) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.teleport(MapManager.getLocation("LobbySpawn"));
					InventoryManager.loadEndInv(all);
				}
			}
		}.runTaskLater(SpeedCW.getInstance(), 20);
		phase = GamePhase.RESTART;
		SpeedCW.getInstance().setMaxPlayers(0);
		if(winner.equals(Team1)) {
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.sendMessage(SpeedCW.prefix+"§7Team §1Blau §7hat §bgewonnen!");
			}
			for(SpeedPlayer sp : team1) {
				MysqlManager.addWin(sp.getPlayer().getUniqueId());
			}
			if(SpeedCW.cfg.getBoolean("Elo")) {
				Player play1 = team1.get(0).getPlayer();
				Player play2 = enemy;
				Utils.calculateElo(play1, play2);
			}
		}else if(winner.equals(Team2)) {
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.sendMessage(SpeedCW.prefix+"§7Team §cRot §7hat §bgewonnen!");
			}
			for(SpeedPlayer sp : team2) {
				MysqlManager.addWin(sp.getPlayer().getUniqueId());
			}
			if(SpeedCW.cfg.getBoolean("Elo")) {
				Player play1 = enemy;
				Player play2 = team2.get(0).getPlayer();
				Utils.calculateElo(play2, play1);
			}
		}else {
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.sendMessage(SpeedCW.prefix+"§7Unentschieden!");
			}
		}
		new BukkitRunnable() {
			int i = 7;
			@Override
			public void run() {
				if(i == 0) {
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.kickPlayer("§cRestart");
					}
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "stop");
				}else {
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage("§bRestart in §7"+i+ " §bSekunde§7(§bn§7)");
					}
					i--;
				}
				
			}
		}.runTaskTimer(SpeedCW.getInstance(), 0, 20);
	}
	public void spawnArmorStands() {
		ArmorStand as = (ArmorStand) team1ArmorStand.getWorld().spawn(team1ArmorStand, ArmorStand.class);
		ArmorStand as1 = (ArmorStand) team2ArmorStand.getWorld().spawn(team2ArmorStand, ArmorStand.class);
		as.setCanPickupItems(false);
		as.setGravity(false);
		as.setCustomName("§1Team Blau");
		as.setCustomNameVisible(true);
		as.setBasePlate(false);
		as.setHelmet(Utils.createColoredLeatherItem(Material.LEATHER_HELMET, Color.BLUE, 1, "§1Helm",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		as.setChestplate(Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§1Chestplate",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		as.setLeggings(Utils.createColoredLeatherItem(Material.LEATHER_LEGGINGS, Color.BLUE, 1,  "§1Hose",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		as.setBoots(Utils.createColoredLeatherItem(Material.LEATHER_BOOTS, Color.BLUE, 1, "§1Boots",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		as.teleport(team1ArmorStand);
		as1.setCanPickupItems(false);
		as1.setGravity(false);
		as1.setCustomName("§cTeam Rot");
		as1.setCustomNameVisible(true);
		as1.setBasePlate(false);
		as1.setHelmet(Utils.createColoredLeatherItem(Material.LEATHER_HELMET, Color.RED, 1, "§cHelm",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		as1.setChestplate(Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§cChestplate",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		as1.setLeggings(Utils.createColoredLeatherItem(Material.LEATHER_LEGGINGS, Color.RED, 1,  "§cHose",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		as1.setBoots(Utils.createColoredLeatherItem(Material.LEATHER_BOOTS, Color.RED, 1, "§cBoots",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		as1.teleport(team2ArmorStand);
	}
	public void startSpawner(Player rnd) {
		Location bronze = MapManager.getLocation("Team1Bronze");
		Location bronze2 = MapManager.getLocation("Team2Bronze");
		Location silver = MapManager.getLocation("Team1Silver");
		Location silver2 = MapManager.getLocation("Team2Silver");
		Location gold = MapManager.getLocation("Team1Gold");
		Location gold2 = MapManager.getLocation("Team2Gold");
		new BukkitRunnable() {
			@Override
			public void run() {
				rnd.getWorld().dropItemNaturally(bronze, Utils.createItem(Material.CLAY_BRICK, 1, "§cBronze"));
				rnd.getWorld().dropItemNaturally(bronze2, Utils.createItem(Material.CLAY_BRICK, 1, "§cBronze"));
			}
		}.runTaskTimer(SpeedCW.getInstance(), 0, 10);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				rnd.getWorld().dropItemNaturally(silver, Utils.createItem(Material.IRON_INGOT, 1, "§7Eisen"));
				rnd.getWorld().dropItemNaturally(silver2, Utils.createItem(Material.IRON_INGOT, 1, "§7Eisen"));
			}
		}.runTaskTimer(SpeedCW.getInstance(), 0, 20*15);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				rnd.getWorld().dropItemNaturally(gold, Utils.createItem(Material.GOLD_INGOT, 1, "§6Gold"));
				rnd.getWorld().dropItemNaturally(gold2, Utils.createItem(Material.GOLD_INGOT, 1, "§6Gold"));
			}
		}.runTaskTimer(SpeedCW.getInstance(), 0, 20*30);
		
	}
	@SuppressWarnings("deprecation")
	public void spawnBeds() {
		Location bed1 = MapManager.getLocation("Team1Bed");
		Location bed2 = MapManager.getLocation("Team2Bed");
			BlockState bedFoot = bed1.getBlock().getRelative(bed1.getBlock().getFace(bed1.getBlock())).getState();
            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.SOUTH).getState();
            bedFoot.setType(Material.BED_BLOCK);
            bedHead.setType(Material.BED_BLOCK);
            bedFoot.setRawData((byte) (0x2 | 0x8));
            bedHead.setRawData((byte) 0x2);
            bedFoot.update(true, false);
            bedHead.update(true, false);
            beds.put("bed1Foot", bedFoot.getBlock());
            beds.put("bed1Head", bedHead.getBlock());
            
			BlockState bedFoot2 = bed2.getBlock().getRelative(bed2.getBlock().getFace(bed2.getBlock())).getState();
            BlockState bedHead2 = bedFoot2.getBlock().getRelative(BlockFace.SOUTH).getState();
            bedFoot2.setType(Material.BED_BLOCK);
            bedHead2.setType(Material.BED_BLOCK);
            bedFoot2.setRawData((byte) 0x0);
            bedHead2.setRawData((byte) 0x8);
            bedFoot2.update(true, false);
            bedHead2.update(true, true);
            beds.put("bed2Foot", bedFoot2.getBlock());
            beds.put("bed2Head", bedHead2.getBlock());
	}
}
