package de.padel.speedCW.Manager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.Utils.Utils;

public class InventoryManager {

	public static void loadInvSort(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "§cInventar Sortierer");
		inv.setContents(loadPlayerinv(p).getContents());
		p.openInventory(inv);
	}
	public static void loadTeam(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "§cTeam");
		for (int i = 0; i < 27; i++) {
			inv.setItem(i, Utils.createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, " ", Arrays.asList("")));
		}
		inv.setItem(12, Utils.createItem2(Material.WOOL, (short) 11, 1, "§7Team §1Blue", Arrays.asList("")));
		inv.setItem(14, Utils.createItem2(Material.WOOL, (short) 14, 1, "§7Team §4Red", Arrays.asList("")));
		p.openInventory(inv);
	}
	public static void loadLobbyInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setItem(3, Utils.createItem(Material.BED, 1, "§eTeam auswählen"));
		p.getInventory().setItem(5, Utils.createItem(Material.REDSTONE_COMPARATOR, 1, "§cInventar Sortierer"));
		p.updateInventory();
	}
	public static void loadGameInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setContents(loadPlayerinv(p).getContents());
		Color c = SpeedCW.manager.getSpeedPlayer(p).getTeam().getColor();
		p.getInventory().setBoots(Utils.createColoredLeatherItem(Material.LEATHER_BOOTS, c, 1, (c.equals(Color.BLUE) ? "§1" : "§c") +"Boots",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		p.getInventory().setLeggings(Utils.createColoredLeatherItem(Material.LEATHER_LEGGINGS, c, 1, (c.equals(Color.BLUE) ? "§1" : "§c") +"Hose",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		p.getInventory().setChestplate(Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, (c.equals(Color.BLUE) ? "§1" : "§c") +"Chestplate",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		p.getInventory().setHelmet(Utils.createColoredLeatherItem(Material.LEATHER_HELMET, c, 1, (c.equals(Color.BLUE) ? "§1" : "§c") +"Helm",
				Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		p.updateInventory();
	}
	public static void loadEndInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.updateInventory();
	}
	public static void loadShopInv(String name, Player p) {
		Inventory inv = Bukkit.createInventory(null, 18, "§cShop");
		inv.setItem(0, Utils.createItem2(Material.SANDSTONE, (short) 2, 1, "§7Blöcke", Arrays.asList("")));
		inv.setItem(1, Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7Rüstung"));
		inv.setItem(2, Utils.createItem(Material.STONE_PICKAXE, 1, "§7Spitzhacken"));
		inv.setItem(3, Utils.createItem(Material.GOLD_SWORD, 1, "§7Schwerter"));
		inv.setItem(4, Utils.createItem(Material.BOW, 1, "§7Bögen"));
		inv.setItem(5, Utils.createItem(Material.APPLE, 1, "§7Essen"));
		inv.setItem(6, Utils.createItem(Material.POTION, 1, "§7Tränke"));
		inv.setItem(7, Utils.createItem(Material.SULPHUR, 1, "§7Spezials"));
		if (name.contains("Blöcke")) {
			inv.setItem(10,
					Utils.createItem2(Material.SANDSTONE, (short) 2, 2, "§6Blöcke", Arrays.asList("§71 §cBronze")));
			inv.setItem(11, Utils.createItem2(Material.ENDER_STONE, (short) 1, 1, "§5End-Stone",
					Arrays.asList("§77 §cBronze")));
			inv.setItem(12,
					Utils.createItem2(Material.IRON_BLOCK, (short) 1, 1, "§7Eisenblock", Arrays.asList("§73 §7Eisen")));
			inv.setItem(14, Utils.createItem2(Material.SANDSTONE_STAIRS, (short) 1, 1, "§eStairs",
					Arrays.asList("§72 §cBronze")));
			inv.setItem(15, Utils.createItem2(Material.GLASS, (short) 1, 1, "§fGlas", Arrays.asList("§75 §cBronze")));
			inv.setItem(16, Utils.createItem2(Material.WEB, (short) 1, 1, "§fWeb", Arrays.asList("§716 §cBronze")));
			inv.setItem(17,
					Utils.createItem2(Material.LADDER, (short) 1, 1, "§cLeiter", Arrays.asList("§71 §cBronze")));
		} else if (name.contains("Rüstung")) {
			Color c = SpeedCW.manager.getSpeedPlayer(p).getTeam().getColor();
			inv.setItem(10, Utils.createColoredLeatherItem(Material.LEATHER_HELMET, c, 1, (c.equals(Color.BLUE) ? "§1" : "§c") +"Helm",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("§71 §cBronze")));
			inv.setItem(11, Utils.createColoredLeatherItem(Material.LEATHER_LEGGINGS, c, 1, (c.equals(Color.BLUE) ? "§1" : "§c") +"Hose",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("§71 §cBronze")));
			inv.setItem(12, Utils.createColoredLeatherItem(Material.LEATHER_BOOTS, c, 1, (c.equals(Color.BLUE) ? "§1" : "§c") +"Boots",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("§71 §cBronze")));
			inv.setItem(14, Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, c + "Chestplate I",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("§71 §7Eisen")));
			inv.setItem(15, Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, c + "Chestplate II",
					Enchantment.PROTECTION_ENVIRONMENTAL, 2, Enchantment.DURABILITY, 3, Arrays.asList("§73 §7Eisen")));
			inv.setItem(16, Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, c + "Chestplate III",
					Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 3, Arrays.asList("§77 §7Eisen")));
		} else if (name.contains("Spitzhacken")) {
			inv.setItem(11, Utils.createItem(Material.WOOD_PICKAXE, 1, "§cHolz Spitzhacke", Enchantment.DIG_SPEED, 1, Arrays.asList("§74 §cBronze")));
			inv.setItem(13, Utils.createItem(Material.STONE_PICKAXE, 1, "§8Stein Spitzhacke",
					Enchantment.DIG_SPEED, 1, Arrays.asList("§72 §7Eisen")));
			inv.setItem(15, Utils.createItem(Material.IRON_PICKAXE, 1, "§7Eisen Spitzhacke",
					Enchantment.DIG_SPEED, 1, Arrays.asList("§71 §6Gold")));
		} else if (name.contains("Schwerter")) {
			inv.setItem(11, Utils.createItem(Material.STICK, 1, "§cStick", Enchantment.KNOCKBACK, 1,
					Enchantment.DURABILITY, 3, Arrays.asList("§78 §cBronze")));
			inv.setItem(13, Utils.createItem(Material.GOLD_SWORD, 1, "§eSchwert I", Enchantment.DURABILITY, 3,
					Enchantment.DAMAGE_ALL, 1, Arrays.asList("§71 §7Eisen")));
			inv.setItem(14, Utils.createItem(Material.GOLD_SWORD, 1, "§eSchwert II", Enchantment.DURABILITY, 3,
					Enchantment.DAMAGE_ALL, 2, Arrays.asList("§72 §7Eisen")));
			inv.setItem(15, Utils.createItem(Material.IRON_SWORD, 1, "§eSchwert III", Enchantment.DURABILITY, 3,
					Enchantment.DAMAGE_ALL, 1, Enchantment.KNOCKBACK, 1, Arrays.asList("§75 §6Gold")));
		} else if (name.contains("Bögen")) {
			inv.setItem(11, Utils.createItem(Material.ARROW, 1, "§fPfeil", Arrays.asList("§71 §6Gold")));
			inv.setItem(13, Utils.createItem(Material.BOW, 1, "§cBogen I", Enchantment.ARROW_INFINITE, 1, Arrays.asList("§73 §6Gold")));
			inv.setItem(14, Utils.createItem(Material.BOW, 1, "§cBogen II", Enchantment.ARROW_INFINITE, 1,Enchantment.ARROW_DAMAGE, 1, Arrays.asList("§77 §6Gold")));
			inv.setItem(15, Utils.createItem(Material.BOW, 1, "§cBogen III", Enchantment.ARROW_INFINITE, 1,Enchantment.ARROW_DAMAGE,1,Enchantment.ARROW_KNOCKBACK,1, Arrays.asList("§713 §6Gold")));
		} else if (name.contains("Essen")) {
			inv.setItem(11, Utils.createItem(Material.APPLE, 1, "§aApfel", Arrays.asList("§71 §cBronze")));
			inv.setItem(12, Utils.createItem(Material.GRILLED_PORK, 1, "§fFleisch", Arrays.asList("§72 §cBronze")));
			inv.setItem(13, Utils.createItem(Material.CAKE, 1, "§fKuchen", Arrays.asList("§71 §7Eisen")));
			inv.setItem(16, Utils.createItem(Material.GOLDEN_APPLE, 1, "§6Apfel", Arrays.asList("§72 §6Gold")));
		} else if (name.contains("Tränke")) {
			inv.setItem(10, Utils.getPotionItemStack(PotionType.SPEED, 1, false, false, "§bSpeed", 1, Arrays.asList("§77 §7Eisen")));
			inv.setItem(12, Utils.getPotionItemStack(PotionType.INSTANT_HEAL, 1, false, false, "§dHeal I", 1, Arrays.asList("§73 §7Eisen")));
			inv.setItem(13, Utils.getPotionItemStack(PotionType.INSTANT_HEAL, 2, false, false, "§dHeal II", 1, Arrays.asList("§75 §7Eisen")));
			inv.setItem(15, Utils.getPotionItemStack(PotionType.STRENGTH, 1, false, false, "§cStärke", 1, Arrays.asList("§77 §6Gold")));
		} else if (name.contains("Spezials")) {
			inv.setItem(10, Utils.createItem(Material.ENDER_PEARL, 1, "§5Enderperle", Arrays.asList("§711 §6Gold")));
			inv.setItem(12, Utils.createItem(Material.BLAZE_ROD, 1, "§cRettungsplattform", Arrays.asList("§73 §6Gold")));
			inv.setItem(13, Utils.createItem(Material.CHEST,  1, "§7Truhe", Arrays.asList("§71 §7Eisen")));
			inv.setItem(14, Utils.createItem(Material.ENDER_CHEST,  1, "§5Endertruhe", Arrays.asList("§71 §6Gold")));
			inv.setItem(15, Utils.createItem(Material.SULPHUR, 1, "§5Teleporter", Arrays.asList("§73 §7Eisen")));
		}
		p.openInventory(inv);
	}
	public static Inventory loadPlayerinv(Player p) {
		if (invExist(p)) {
			String invcontents = "";
			ResultSet rs = SpeedCW.mysql
					.query("SELECT * FROM Invs WHERE UUID = '" + p.getUniqueId().toString() + "';");
			try {
				if (rs.next()) {
					invcontents = rs.getString("inv");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {

				Inventory inv = BukkitSerialization.fromBase64(invcontents);
				Inventory x = Bukkit.createInventory(p, 9);
				for (int i = 0; i < 9; i++) {
					if (inv.getItem(i) == null) {
						x.setItem(i, new ItemStack(Material.AIR));
					} else {
						x.setItem(i, inv.getItem(i));
					}
				}
				return x;
			} catch (IOException e) {
				e.printStackTrace();
			}
			Inventory inv1 = Bukkit.createInventory(p, 9);
			inv1.setItem(0, Utils.createItem(Material.GOLD_SWORD, 1, "§eSchwert", Enchantment.DURABILITY, 3,
					Enchantment.DAMAGE_ALL, 1));
			inv1.setItem(1, Utils.createItem(Material.STONE_PICKAXE, 1, "§8Stein Spitzhacke",
					Enchantment.DIG_SPEED, 1, Arrays.asList("")));
			inv1.setItem(2, Utils.createItem(Material.STICK, 1, "§cStick", Enchantment.KNOCKBACK, 1));
			inv1.setItem(3, Utils.createItem(Material.GRILLED_PORK, 4, "§7Fleisch"));
			inv1.setItem(4, Utils.createItem(Material.LADDER, 3, "§aLeiter"));
			inv1.setItem(5,
					Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
			inv1.setItem(6,
					Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
			inv1.setItem(7,
					Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
			inv1.setItem(8,
					Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
			saveInv(p, inv1);
			return inv1;
		} else {
			Inventory inv1 = Bukkit.createInventory(p, 9);
			inv1.setItem(0, Utils.createItem(Material.GOLD_SWORD, 1, "§eSchwert", Enchantment.DURABILITY, 3,
					Enchantment.DAMAGE_ALL, 1));
			inv1.setItem(1, Utils.createItem(Material.STONE_PICKAXE, 1, "§8Stein Spitzhacke",
					Enchantment.DIG_SPEED, 1, Arrays.asList("")));
			inv1.setItem(2, Utils.createItem(Material.STICK, 1, "§cStick", Enchantment.KNOCKBACK, 1));
			inv1.setItem(3, Utils.createItem(Material.GRILLED_PORK, 4, "§7Fleisch"));
			inv1.setItem(4, Utils.createItem(Material.LADDER, 3, "§aLeiter"));
			inv1.setItem(5,
					Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
			inv1.setItem(6,
					Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
			inv1.setItem(7,
					Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
			inv1.setItem(8,
					Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
			saveInv(p, inv1);
			return inv1;
		}
	}
	public static void saveInv(Player p, Inventory inv) {
		if (invExist(p)) {
			if(checkContents(inv)) {
				String invcontents = BukkitSerialization.toBase64(inv);
				SpeedCW.mysql.update("UPDATE Invs SET inv = '" + invcontents + "' WHERE UUID = '"
						+ p.getUniqueId().toString() + "';");
				p.sendMessage(SpeedCW.prefix+"§aInventar gesichert");
			}else {
				p.sendMessage(SpeedCW.prefix+"§cAbgebrochen");
			}
		} else {
			if(checkContents(inv)) {
				String invcontents = BukkitSerialization.toBase64(inv);
				SpeedCW.mysql.update("INSERT INTO Invs (UUID, inv) VALUES ('" + p.getUniqueId().toString() + "','"
						+ invcontents + "');");
				p.sendMessage(SpeedCW.prefix+"§aInventar gesichert");
			}else {
				p.sendMessage(SpeedCW.prefix+"§cAbgebrochen");
			}
		}
	}
	public static boolean checkContents(Inventory inv)  {
		if(inv.contains(Utils.createItem(Material.GOLD_SWORD, 1, "§eSchwert", Enchantment.DURABILITY, 3, Enchantment.DAMAGE_ALL, 1), 1) && 
				inv.contains(Utils.createItem(Material.STONE_PICKAXE, 1, "§8Stein Spitzhacke",
						Enchantment.DIG_SPEED, 1, Arrays.asList("")), 1) &&
				inv.contains(Utils.createItem(Material.STICK, 1, "§cStick", Enchantment.KNOCKBACK, 1), 1) &&
				inv.contains(Utils.createItem(Material.GRILLED_PORK, 4, "§7Fleisch"), 1) &&
				inv.contains(Utils.createItem(Material.LADDER, 3, "§aLeiter"), 1) &&
				inv.contains(Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")), 4)
				) {
			return true;
		}else {
			return false;
		}
	}

	public static boolean invExist(Player p) {
		ResultSet rs = SpeedCW.mysql.query("SELECT * FROM Invs WHERE UUID = '" + p.getUniqueId().toString() + "';");
		try {
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
