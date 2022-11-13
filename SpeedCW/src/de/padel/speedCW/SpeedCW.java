package de.padel.speedCW;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.dytanic.cloudnet.ext.bridge.BridgeHelper;
import de.dytanic.cloudnet.ext.bridge.bukkit.BukkitCloudNetHelper;
import de.padel.speedCW.Commands.Build;
import de.padel.speedCW.Commands.Setup;
import de.padel.speedCW.Commands.Stats;
import de.padel.speedCW.Listener.AsyncChatListener;
import de.padel.speedCW.Listener.BasicListener;
import de.padel.speedCW.Listener.DeathListener;
import de.padel.speedCW.Listener.InteractListener;
import de.padel.speedCW.Listener.JoinListener;
import de.padel.speedCW.Listener.ShopListener;
import de.padel.speedCW.Manager.MapManager;
import de.padel.speedCW.Manager.SpeedManager;
import de.padel.speedCW.Stats.MySQL;
import de.padel.speedCW.Utils.Config;
import de.padel.speedCW.Utils.EntityModifier;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;

public class SpeedCW extends JavaPlugin {

	public static MySQL mysql;
	public static SpeedCW instance;

	public static SpeedManager manager;

	public static File f = new File("plugins/SpeedCW", "config.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

	public static String prefix = "§bSpeedCW §8| §r";

	@Override
	public void onEnable() {
		instance = this;
		Config.Setup();
		MapManager.Setup();
		ConnectToMySQL();
		registerCommands();
		registerListener();
		if(MapManager.isLocation("Team1Spawn")) {
			manager = new SpeedManager(
					new SpeedTeam("team1", Color.BLUE, true, MapManager.getLocation("Team1Spawn"),
							MapManager.getLocation("Team1Bronze"), MapManager.getLocation("Team1Silver"),
							MapManager.getLocation("Team1Gold")),
					new SpeedTeam("team2", Color.RED, true, MapManager.getLocation("Team2Spawn"),
							MapManager.getLocation("Team2Bronze"), MapManager.getLocation("Team2Silver"),
							MapManager.getLocation("Team2Gold")),
					cfg.getInt("LobbyTime"), GamePhase.LOBBY);
			
			setMaxPlayers(SpeedCW.cfg.getInt("PlayerPerTeam")*2);
		}
	}

	@Override
	public void onDisable() {
		if (mysql.isConnected()) {
			mysql.close();
		}
	}

	public void registerCommands() {
		getCommand("build").setExecutor(new Build());
		getCommand("setup").setExecutor(new Setup());
		getCommand("stats").setExecutor(new Stats());
	}

	public void registerListener() {
		Bukkit.getPluginManager().registerEvents(new BasicListener(), instance);
		Bukkit.getPluginManager().registerEvents(new JoinListener(), instance);
		Bukkit.getPluginManager().registerEvents(new ShopListener(), instance);
		Bukkit.getPluginManager().registerEvents(new InteractListener(), instance);
		Bukkit.getPluginManager().registerEvents(new DeathListener(), instance);
		Bukkit.getPluginManager().registerEvents(new AsyncChatListener(), instance);
	}

	public void ConnectToMySQL() {
		if (cfg.getBoolean("Config.MySQL")) {
			mysql = new MySQL(cfg.getString("MySQL.ip"), cfg.getString("MySQL.database"), cfg.getString("MySQL.name"),
					cfg.getString("MySQL.password"));
		}
		if (mysql.isConnected()) {
			mysql.update("CREATE TABLE IF NOT EXISTS Stats(UUID varchar(100), Kills int(100), Deaths int(100), Wins int(100), Elo int(100));");
			mysql.update("CREATE TABLE IF NOT EXISTS Invs(UUID varchar(100), inv varchar(5000));");
		}
	}

	public static SpeedCW getInstance() {
		return instance;
	}

	public void checkVillager() {
		if (MapManager.isLocation("VillagerSpawn1")) {
			Location loc = MapManager.getLocation("VillagerSpawn1");
			Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			villager.setCustomName("");
			villager.teleport(loc);
			villager.setCustomNameVisible(false);
			villager.setFallDistance(0.0F);
			Location l = new Location(loc.getWorld(),
					loc.getX(),loc.getY() - 0.18D,
					loc.getZ());
			ArmorStand test = (ArmorStand) Bukkit.getWorld("world").spawnEntity(l, EntityType.ARMOR_STAND);
			test.setVisible(false);
			test.setCustomName("§cShop");
			test.setGravity(false);
			test.setCustomNameVisible(true);
			setRotation(villager, loc.getYaw(), loc.getPitch());
			((LivingEntity) villager).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999191, 500));
			(new EntityModifier(villager, (Plugin) getInstance())).modify().setCanDespawn(false).setInvulnerable(true)
			.setNoAI(true).build();
		}
		if (MapManager.isLocation("VillagerSpawn2")) {
			Location loc = MapManager.getLocation("VillagerSpawn2");
			Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			villager.setCustomName("");
			villager.setCustomNameVisible(false);
			villager.setFallDistance(0.0F);
			Location l = new Location(loc.getWorld(),
					loc.getX(),loc.getY() - 0.18D,
					loc.getZ());
			ArmorStand test = (ArmorStand) Bukkit.getWorld("world").spawnEntity(l, EntityType.ARMOR_STAND);
			test.setVisible(false);
			test.setCustomName("§cShop");
			test.setGravity(false);
			test.setCustomNameVisible(true);
			setRotation(villager, loc.getYaw(), loc.getPitch());
			((LivingEntity) villager).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999191, 500));
			(new EntityModifier(villager, (Plugin) getInstance())).modify().setCanDespawn(false).setInvulnerable(true)
					.setNoAI(true).build();
		}
		if (MapManager.isLocation("VillagerSpawn3")) {
			Location loc = MapManager.getLocation("VillagerSpawn3");
			Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			villager.setCustomName("");
			villager.teleport(loc);
			villager.setCustomNameVisible(false);
			villager.setFallDistance(0.0F);
			Location l = new Location(loc.getWorld(),
					loc.getX(),loc.getY() - 0.18D,
					loc.getZ());
			ArmorStand test = (ArmorStand) Bukkit.getWorld("world").spawnEntity(l, EntityType.ARMOR_STAND);
			test.setVisible(false);
			test.setCustomName("§cShop");
			test.setGravity(false);
			test.setCustomNameVisible(true);
			setRotation(villager, loc.getYaw(), loc.getPitch());
			((LivingEntity) villager).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999191, 500));
			(new EntityModifier(villager, (Plugin) getInstance())).modify().setCanDespawn(false).setInvulnerable(true)
			.setNoAI(true).build();
		}
		if (MapManager.isLocation("VillagerSpawn4")) {
			Location loc = MapManager.getLocation("VillagerSpawn4");
			Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			villager.setCustomName("");
			villager.teleport(loc);
			villager.setCustomNameVisible(false);
			villager.setFallDistance(0.0F);
			Location l = new Location(loc.getWorld(),
					loc.getX(),loc.getY() - 0.18D,
					loc.getZ());
			ArmorStand test = (ArmorStand) Bukkit.getWorld("world").spawnEntity(l, EntityType.ARMOR_STAND);
			test.setVisible(false);
			test.setCustomName("§cShop");
			test.setGravity(false);
			test.setCustomNameVisible(true);
			setRotation(villager, loc.getYaw(), loc.getPitch());
			((LivingEntity) villager).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999191, 500));
			(new EntityModifier(villager, (Plugin) getInstance())).modify().setCanDespawn(false).setInvulnerable(true)
			.setNoAI(true).build();
		} 
	}
	public void setRotation(Entity entity, float yaw, float pitch) {
	    if(entity instanceof LivingEntity) {
	        EntityLiving handle = ((CraftLivingEntity) entity).getHandle();
	        yaw = clampYaw(yaw);
	        handle.yaw = yaw;
	        setHeadYaw(entity, yaw);
	        handle.pitch = pitch;
	    }
	}

	private void setHeadYaw(Entity entity, float yaw) {
	    EntityLiving handle = ((CraftLivingEntity) entity).getHandle();
	    yaw = clampYaw(yaw);
	    handle.yaw = yaw;
	    if(!(handle instanceof EntityHuman)) {
	        handle.aK = yaw;
	    }
	    handle.aK = yaw;
	}

	private float clampYaw(float yaw) {
	    while(yaw < -180.0F) {
	        yaw += 360.0F;
	    }

	    while(yaw >= 180.0F) {
	        yaw -= 360.0F;
	    }

	    return yaw;
	}
	public void setMaxPlayers(int amount) {
		String bukkitversion = Bukkit.getServer().getClass().getPackage()
                .getName().substring(23);
		try {
			Object playerlist = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".CraftServer").getDeclaredMethod("getHandle").invoke(Bukkit.getServer());
			Field maxplayers = playerlist.getClass().getSuperclass().getDeclaredField("maxPlayers");
	        maxplayers.setAccessible(true);
	        maxplayers.set(playerlist, amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BukkitCloudNetHelper.setMaxPlayers(amount);
		BridgeHelper.updateServiceInfo();
	}
	public void setStatus(String status) {
		BukkitCloudNetHelper.setApiMotd(status);
		BukkitCloudNetHelper.setExtra(status);
		BukkitCloudNetHelper.setState(status);
		BridgeHelper.updateServiceInfo();
	}

}
