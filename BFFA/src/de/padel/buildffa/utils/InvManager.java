package de.padel.buildffa.utils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.stats.InvMysql;

public class InvManager {
	public static void setSpawnInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(3, createItem(Material.GOLD_NUGGET, 1, "§cInventory Sort"));
		p.getInventory().setItem(5, createItem(Material.REDSTONE_COMPARATOR, 1, "§cItems"));
		p.getInventory().clear(0);
	}
	public static Color getAktiveColor(Player p) {
		switch(InvMysql.getAktivColor(p.getUniqueId().toString())) {
			case 0:
				return Color.RED;
			case 1:
				return Color.AQUA;
			case 2:
				return Color.BLACK;
			case 3:
				return Color.WHITE;
			case 4:
				return Color.LIME;
			default:
				return Color.RED;
		}
	}
	public static ItemStack getAktivStick(Player p) {
		switch(InvMysql.getAktivStick(p.getUniqueId().toString())) {
			case 0:
				return createEnchantedItem(Material.STICK, 1, "§c« KnockBack Stick »", Enchantment.KNOCKBACK, 1);
			case 1:
				return createEnchantedItem(Material.BONE, 1, "§c« KnockBack Stick »", Enchantment.KNOCKBACK, 1);
			case 2:
				return createEnchantedItem(Material.FEATHER, 1, "§c« KnockBack Stick »", Enchantment.KNOCKBACK, 1);
			case 3:
				return createEnchantedItem(Material.BLAZE_ROD, 1, "§c« KnockBack Stick »", Enchantment.KNOCKBACK, 1);
			case 4:
				return createEnchantedItem(Material.WOOD_HOE, 1, "§c« KnockBack Stick »", Enchantment.KNOCKBACK, 1);
			case 5:
				return createEnchantedItem(Material.RED_ROSE, 1, "§c« KnockBack Stick »", Enchantment.KNOCKBACK, 1);
			case 6:
				return createEnchantedItem(Material.SHEARS, 1, "§c« KnockBack Stick »", Enchantment.KNOCKBACK, 1);
			default:
				return createEnchantedItem(Material.STICK, 1, "§c« KnockBack Stick »", Enchantment.KNOCKBACK, 1);
		}
	}
	public static ItemStack getAktivBlock(Player p) {
		switch(InvMysql.getAktivBlock(p.getUniqueId().toString())) {
			case 0:
				return createItem2(Material.SANDSTONE, (short) 2, 64, "§e« Blöcke »");
			case 1:
				return createItem2(Material.RED_SANDSTONE, (short) 2, 64, "§e« Blöcke »");
			case 2:
				return createItem2(Material.BRICK, (short) 1, 64, "§e« Blöcke »");
			case 3:
				return createItem2(Material.ENDER_STONE, (short) 1, 64, "§e« Blöcke »");
			case 4:
				return createItem2(Material.QUARTZ_BLOCK, (short) 1, 64, "§e« Blöcke »");
			case 5:
				return createItem2(Material.GLOWSTONE, (short) 1, 64, "§e« Blöcke »");
			case 6:
				return createItem2(Material.IRON_BLOCK, (short) 1, 64, "§e« Blöcke »");
			case 7:
				return createItem2(Material.EMERALD_BLOCK, (short) 1, 64, "§e« Blöcke »");
			case 8:
				return createItem2(Material.REDSTONE_BLOCK, (short) 1, 64, "§e« Blöcke »");
			case 9:
				return createItem2(Material.DIAMOND_BLOCK, (short) 1, 64, "§e« Blöcke »");
			default:
				return createItem2(Material.SANDSTONE, (short) 1, 64, "§e« Blöcke »");
		}
	}
	public static ItemStack getAktivSword(Player p) {
		switch(InvMysql.getAktivSword(p.getUniqueId().toString())) {
			case 0:
				return createEnchantedItem2(Material.GOLD_SWORD, 1, "§6« Schwert »",
						Enchantment.DAMAGE_ALL, 1, Enchantment.DURABILITY, 3);
			case 1:
				return createEnchantedItem2(Material.WOOD_SWORD, 1, "§6« Schwert »",
						Enchantment.DAMAGE_ALL, 1, Enchantment.DURABILITY, 3);
			default:
				return createEnchantedItem2(Material.GOLD_SWORD, 1, "§6« Schwert »",
						Enchantment.DAMAGE_ALL, 1, Enchantment.DURABILITY, 3);
		}
	}
	

	public static void setGameInv(Player p) {
		if (invExist(p)) {
			int swordSlot = 0;
			int enderpearlSlot = 2;
			int blockSlot = 8;
			int stickSlot = 1;
			Inventory PlayerInv = loadPlayerinv(p);
			for(int i = 0; i < PlayerInv.getContents().length; i++) {
				if(PlayerInv.getItem(i) != null) {
					if(PlayerInv.getItem(i).equals(createEnchantedItem2(Material.GOLD_SWORD, 1, "§6« Schwert »",
							Enchantment.DAMAGE_ALL, 1, Enchantment.DURABILITY, 3))) {
						swordSlot = i;
					}
					if(PlayerInv.getItem(i).equals(createItem2(Material.SANDSTONE, (short) 2, 64, "§e« Blöcke »"))) {
						blockSlot = i;
					}
					if(PlayerInv.getItem(i).equals(createEnchantedItem(Material.STICK, 1, "§c« KnockBack Stick »", Enchantment.KNOCKBACK, 1))) {
						stickSlot = i;
					}
					if(PlayerInv.getItem(i).equals(createItem(Material.ENDER_PEARL, 1, "§5« Enderperle »"))) {
						enderpearlSlot = i;
					}
				}
			}
			p.getInventory().setItem(stickSlot, getAktivStick(p));
			p.getInventory().setItem(blockSlot, getAktivBlock(p));
			p.getInventory().setItem(enderpearlSlot, createItem(Material.ENDER_PEARL, 1, "§5« Enderperle »"));
			p.getInventory().setItem(swordSlot, getAktivSword(p));
			
			
			
			p.getInventory().setHelmet(createEnchantedItem3(Material.LEATHER_HELMET, getAktiveColor(p) ,1, "§b§9Helm",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
			p.getInventory().setChestplate(createEnchantedItem2(Material.CHAINMAIL_CHESTPLATE, 1, "§b§9Brustplatte",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
			p.getInventory().setLeggings(createEnchantedItem3(Material.LEATHER_LEGGINGS, getAktiveColor(p), 1, "§b§9Hose",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
			p.getInventory().setBoots(createEnchantedItem3(Material.LEATHER_BOOTS, getAktiveColor(p), 1, "§b§9Schuhe",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		} else {
			p.getInventory().setItem(0, getAktivSword(p));
			p.getInventory().setItem(1, getAktivStick(p));
			p.getInventory().setItem(7, createItem(Material.ENDER_PEARL, 1, "§5« Enderperle »"));
			p.getInventory().setItem(8, getAktivBlock(p));

			Inventory inv = p.getInventory();
			saveInv(p, inv);

			p.getInventory().setHelmet(createEnchantedItem3(Material.LEATHER_HELMET, getAktiveColor(p) ,1, "§b§9Helm",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
			p.getInventory().setChestplate(createEnchantedItem2(Material.CHAINMAIL_CHESTPLATE, 1, "§b§9Brustplatte",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
			p.getInventory().setLeggings(createEnchantedItem3(Material.LEATHER_LEGGINGS, getAktiveColor(p), 1, "§b§9Hose",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
			p.getInventory().setBoots(createEnchantedItem3(Material.LEATHER_BOOTS, getAktiveColor(p), 1, "§b§9Schuhe",
					Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
		}
	}

	public static Inventory loadPlayerinv(Player p) {
		if (invExist(p)) {
			String invcontents = "";
			ResultSet rs = Main.mysql
					.query("SELECT * FROM BFFAInvs WHERE UUID = '" + p.getUniqueId().toString() + "';");
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
			inv1.setItem(0, getAktivSword(p));
			inv1.setItem(1, getAktivStick(p));
			inv1.setItem(7, createItem(Material.ENDER_PEARL, 1, "§5« Enderperle »"));
			inv1.setItem(8, getAktivBlock(p));
			saveInv(p, inv1);
			return inv1;
		} else {
			Inventory inv1 = Bukkit.createInventory(p, 9);
			inv1.setItem(0, getAktivSword(p));
			inv1.setItem(1, getAktivStick(p));
			inv1.setItem(7, createItem(Material.ENDER_PEARL, 1, "§5« Enderperle »"));
			inv1.setItem(8, getAktivBlock(p));
			saveInv(p, inv1);
			return inv1;
		}
	}

	public static void saveInv(Player p, Inventory inv) {
		if (invExist(p)) {
			if(checkContents(inv, p)) {
				String invcontents = BukkitSerialization.toBase64(inv);
				Main.mysql.update("UPDATE BFFAInvs SET inv = '" + invcontents + "' WHERE UUID = '"
						+ p.getUniqueId().toString() + "';");
				p.sendMessage(Main.prefix+"§aInventar gesichert!");
			}else {
				p.sendMessage(Main.prefix+"§cAbgebrochen");
			}
		} else {
			if(checkContents(inv, p)) {
				String invcontents = BukkitSerialization.toBase64(inv);
				Main.mysql.update("INSERT INTO BFFAInvs (UUID, inv) VALUES ('" + p.getUniqueId().toString() + "','"
						+ invcontents + "');");
				p.sendMessage(Main.prefix+"§aInventar gesichert!");
			}else {
				p.sendMessage(Main.prefix+"§cAbgebrochen");
			}
		}
	}

	public static boolean invExist(Player p) {
		ResultSet rs = Main.mysql.query("SELECT * FROM BFFAInvs WHERE UUID = '" + p.getUniqueId().toString() + "';");
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

	public static ItemStack createItem(Material mat, int anzahl, String name) {
		ItemStack i = new ItemStack(mat, anzahl);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.spigot().setUnbreakable(true);
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack createItem2(Material mat, short h, int anzahl, String name) {
		ItemStack i = new ItemStack(mat, anzahl, h);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.spigot().setUnbreakable(true);
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack createEnchantedItem(Material mat, int anzahl, String name, Enchantment en, int power) {
		ItemStack i = new ItemStack(mat, anzahl);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.addEnchant(en, power, true);
		m.spigot().setUnbreakable(true);
		i.setItemMeta(m);
		return i;
	}
	public static ItemStack createEnchantedItem2(Material mat, int anzahl, String name, Enchantment en, int power,
			Enchantment en2, int power2) {
		ItemStack i = new ItemStack(mat, anzahl);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.addEnchant(en, power, true);
		m.addEnchant(en2, power2, true);
		m.spigot().setUnbreakable(true);
		i.setItemMeta(m);
		return i;
	}
	public static ItemStack createEnchantedItem3(Material mat, Color color,int anzahl, String name, Enchantment en, int power,
			Enchantment en2, int power2) {
		ItemStack i = new ItemStack(mat, anzahl);
		LeatherArmorMeta m = (LeatherArmorMeta) i.getItemMeta();
		m.setDisplayName(name);
		m.setColor(color);
		m.addEnchant(en, power, true);
		m.addEnchant(en2, power2, true);
		m.spigot().setUnbreakable(true);
		i.setItemMeta(m);
		return i;
	}

	public static boolean checkContents(Inventory inv, Player p) {
		if(inv.contains(createEnchantedItem2(Material.GOLD_SWORD, 1, "§6« Schwert »",
				Enchantment.DAMAGE_ALL, 1, Enchantment.DURABILITY, 3), 1) &&
		inv.contains(createEnchantedItem(Material.STICK, 1, "§c« KnockBack Stick »", Enchantment.KNOCKBACK, 1), 1) &&
		inv.contains(createItem(Material.ENDER_PEARL, 1, "§5« Enderperle »"), 1) &&
		inv.contains(createItem2(Material.SANDSTONE, (short) 2, 64, "§e« Blöcke »"))) {
			return true;
		}else {
			return false;
		}
	}
}