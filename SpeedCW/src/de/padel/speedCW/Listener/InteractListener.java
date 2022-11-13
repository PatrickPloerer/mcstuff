package de.padel.speedCW.Listener;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.SpeedPlayer;
import de.padel.speedCW.SpeedTeam;
import de.padel.speedCW.Manager.InventoryManager;
import de.padel.speedCW.Manager.MapManager;
import de.padel.speedCW.Utils.Utils;
import de.padel.tab.Main;
import net.minecraft.server.v1_8_R3.EnumChatFormat;

public class InteractListener implements Listener{
	
	
	Location team1ArmorStand = null;
	Location team2ArmorStand = null;
	
	public static HashMap<UUID, ArmorStand> armors = new HashMap<UUID, ArmorStand>();
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e) {
		if(MapManager.isLocation("SelectTeam1")) {
			team1ArmorStand = MapManager.getLocation("SelectTeam1");
		}
		if(MapManager.isLocation("SelectTeam2")) {
			team2ArmorStand = MapManager.getLocation("SelectTeam2");
		}
		Entity en = e.getRightClicked();
		Player p = e.getPlayer();
		SpeedPlayer sp = SpeedCW.manager.getSpeedPlayer(p);
		SpeedTeam blue = SpeedCW.manager.Team1;
		SpeedTeam red = SpeedCW.manager.Team2;
		if(en.getType().equals(EntityType.VILLAGER)) {
			e.setCancelled(true);
		}
		
		if(en.getCustomName() != null) {
			if(en instanceof ArmorStand || en instanceof Villager) {
				e.setCancelled(true);
			}
			if(en.getCustomName().contains("§cShop")) {
				e.setCancelled(true);
				InventoryManager.loadShopInv("Blöcke", p);
			}else if(en.getCustomName().contains("§1Team Blau")) {
				if(SpeedCW.manager.team1.size() < SpeedCW.cfg.getInt("PlayerPerTeam")) {
					if(!sp.getTeam().equals(blue)) {
						if(armors.containsKey(p.getUniqueId())) {
							ArmorStand as = armors.get(p.getUniqueId());
							as.setHelmet(null);
							as.setBoots(null);
							as.setChestplate(null);
							as.setLeggings(null);
							as.setHealth(0);
							as.remove();
						}
					}
					if(!sp.getTeam().equals(blue)) {
						SpeedCW.manager.removePlayerFromTeam(sp.getTeam().getName(), sp);
							SpeedCW.manager.setPlayerTeam(p, "team1");
							Location team1 = new Location(p.getWorld(), team1ArmorStand.getX()+3, team1ArmorStand.getY()+1, team1ArmorStand.getZ(), team1ArmorStand.getYaw(), team1ArmorStand.getPitch());
							ArmorStand as = (ArmorStand) team1.getWorld().spawn(team1, ArmorStand.class);
							as.setCanPickupItems(false);
							as.setGravity(false);
							as.setCustomName("§1"+p.getName());
							as.setCustomNameVisible(true);
							as.setVisible(true);
							as.setBasePlate(false);
							as.setHelmet(getHead(p));
							as.setChestplate(Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§1Chestplate", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
							as.setLeggings(Utils.createColoredLeatherItem(Material.LEATHER_LEGGINGS, Color.BLUE, 1,  "§1Hose", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
							as.setBoots(Utils.createColoredLeatherItem(Material.LEATHER_BOOTS, Color.BLUE, 1, "§1Boots", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
							p.playSound(team1ArmorStand, Sound.PISTON_EXTEND, 5, 5);
							armors.put(p.getUniqueId(), as);
							String bac = "";
							if(Main.isUsingBAC.containsKey(p.getUniqueId())) {
								bac = " §e✔";
							}
							Main.getInstance().tM.changeTeam(p, "§1", EnumChatFormat.DARK_BLUE,
									bac, 0);
							Main.getInstance().tM.update();
					}
				}else {
					p.sendMessage(SpeedCW.prefix + "§cDas Team ist voll.");
				}
			}else if(en.getCustomName().contains("§cTeam Rot")) {
				if(SpeedCW.manager.team2.size() < SpeedCW.cfg.getInt("PlayerPerTeam")) {
					if(!sp.getTeam().equals(red)) {
						if(armors.containsKey(p.getUniqueId())) {
							ArmorStand as = armors.get(p.getUniqueId());
							as.setHelmet(null);
							as.setBoots(null);
							as.setChestplate(null);
							as.setLeggings(null);
							as.setHealth(0);
							as.remove();
						}
					}
					if(!sp.getTeam().equals(red)) {
						SpeedCW.manager.removePlayerFromTeam(sp.getTeam().getName(), sp);
							SpeedCW.manager.setPlayerTeam(p, "team2");
							Location team2 = new Location(p.getWorld(), team2ArmorStand.getX()+3, team2ArmorStand.getY()+1, team2ArmorStand.getZ(), team2ArmorStand.getYaw(), team2ArmorStand.getPitch());
							ArmorStand as = (ArmorStand) team2.getWorld().spawn(team2, ArmorStand.class);
							as.setCanPickupItems(false);
							as.setGravity(false);
							as.setCustomName("§c"+p.getName());
							as.setCustomNameVisible(true);
							as.setVisible(true);
							as.setBasePlate(false);
							as.setHelmet(getHead(p));
							as.setChestplate(Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§cChestplate", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
							as.setLeggings(Utils.createColoredLeatherItem(Material.LEATHER_LEGGINGS, Color.RED, 1,  "§cHose", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
							as.setBoots(Utils.createColoredLeatherItem(Material.LEATHER_BOOTS, Color.RED, 1, "§cBoots", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
							p.playSound(team2ArmorStand, Sound.PISTON_EXTEND, 5, 5);
							String bac = "";
							if(Main.isUsingBAC.containsKey(p.getUniqueId())) {
								bac = " §e✔";
							}
							armors.put(p.getUniqueId(), as);
							Main.getInstance().tM.changeTeam(p, "§c", EnumChatFormat.DARK_RED,
									bac, 1);
							Main.getInstance().tM.update();
					}
				}else {
					p.sendMessage(SpeedCW.prefix + "§cDas Team ist voll.");
				}
			}else if(en instanceof ArmorStand) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onInter(PlayerInteractEntityEvent e) {
		if(e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
			e.setCancelled(true);
			InventoryManager.loadShopInv("Blöcke", e.getPlayer());
		}
	}
	
	public static ItemStack getHead(Player player) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setOwner(player.getName());
        item.setItemMeta(skull);
        return item;
    }

}
