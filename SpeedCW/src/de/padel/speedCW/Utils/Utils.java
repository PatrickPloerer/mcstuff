package de.padel.speedCW.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import de.padel.speedCW.Manager.MysqlManager;

public class Utils {

	public static ArrayList<UUID>  build = new ArrayList<>();
	public static ArrayList<Block> buildBlocks = new ArrayList<>();
	
	public static ItemStack createItem2(Material mat, short h, int anzahl, String name, List<String> lore) {
		ItemStack i = new ItemStack(mat, anzahl, h);
		i.setAmount(anzahl);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public static ItemStack createItem(Material mat, int amount, String name) {
		ItemStack i = new ItemStack(mat, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}
	public static ItemStack createItem(Material mat, int amount, String name, List<String> lore) {
		ItemStack i = new ItemStack(mat, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}
	public static ItemStack createItem(Material mat, int amount, String name, Enchantment enchant1, int levl, Enchantment enchant2, int levl2) {
		ItemStack i = new ItemStack(mat, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.addEnchant(enchant1, levl, true);
		im.addEnchant(enchant2, levl2, true);
		i.setItemMeta(im);
		return i;
	}
	public static ItemStack createItem(Material mat, int amount, String name, Enchantment enchant1, int levl, Enchantment enchant2, int levl2, List<String> lore) {
		ItemStack i = new ItemStack(mat, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.addEnchant(enchant1, levl, true);
		im.addEnchant(enchant2, levl2, true);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}
	public static ItemStack createItem(Material mat, int amount, String name, Enchantment enchant1, int levl) {
		ItemStack i = new ItemStack(mat, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.addEnchant(enchant1, levl, true);
		i.setItemMeta(im);
		return i;
	}
	public static ItemStack createItem(Material mat, int amount, String name, Enchantment enchant1, int levl, List<String> lore) {
		ItemStack i = new ItemStack(mat, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.addEnchant(enchant1, levl, true);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}
	public static ItemStack createItem(Material mat, int amount, String name, Enchantment enchant1, int levl, Enchantment enchant2, int levl2, Enchantment enchant3, int levl3) {
		ItemStack i = new ItemStack(mat, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.addEnchant(enchant1, levl, true);
		im.addEnchant(enchant2, levl2, true);
		im.addEnchant(enchant3, levl3, true);
		i.setItemMeta(im);
		return i;
	}
	public static ItemStack createItem(Material mat, int amount, String name, Enchantment enchant1, int levl, Enchantment enchant2, int levl2, Enchantment enchant3, int levl3, List<String> Lore) {
		ItemStack i = new ItemStack(mat, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.addEnchant(enchant1, levl, true);
		im.addEnchant(enchant2, levl2, true);
		im.addEnchant(enchant3, levl3, true);
		im.setLore(Lore);
		i.setItemMeta(im);
		return i;
	}
	public static ItemStack createColoredLeatherItem(Material mat, Color c, int anzahl, String name, List<String> lore) {
		ItemStack i = new ItemStack(mat, anzahl);
		i.setAmount(anzahl);
		LeatherArmorMeta m = (LeatherArmorMeta)i.getItemMeta();
		m.setLore(lore);
		m.setColor(c);
		m.setDisplayName(name);
		m.spigot().setUnbreakable(true);
		i.setItemMeta((ItemMeta)m);
		return i;
	}
	public static ItemStack createColoredLeatherItem(Material mat, Color c, int anzahl, String name, Enchantment ench, int levl, Enchantment ench2, int levl2) {
		ItemStack i = new ItemStack(mat, anzahl);
		i.setAmount(anzahl);
		LeatherArmorMeta m = (LeatherArmorMeta)i.getItemMeta();
		m.setColor(c);
		m.setDisplayName(name);
		m.spigot().setUnbreakable(true);
		m.addEnchant(ench, levl, true);
		m.addEnchant(ench2, levl2, true);
		i.setItemMeta((ItemMeta)m);
		return i;
	}
	public static ItemStack createColoredLeatherItem(Material mat, Color c, int anzahl, String name, Enchantment ench, int levl, Enchantment ench2, int levl2, List<String> lore) {
		ItemStack i = new ItemStack(mat, anzahl);
		i.setAmount(anzahl);
		LeatherArmorMeta m = (LeatherArmorMeta)i.getItemMeta();
		m.setColor(c);
		m.setDisplayName(name);
		m.spigot().setUnbreakable(true);
		m.addEnchant(ench, levl, true);
		m.addEnchant(ench2, levl2, true);
		m.setLore(lore);
		i.setItemMeta((ItemMeta)m);
		return i;
	}
	@SuppressWarnings("deprecation")
	public static ItemStack getPotionItemStack(PotionType type, int level, boolean extend, boolean splash, String displayName, int amount, List<String> lore){
        ItemStack potion = new Potion(type, level, splash, extend).toItemStack(amount);
        ItemMeta pm = potion.getItemMeta();
        pm.setDisplayName(displayName);
        pm.setLore(lore);
        potion.setItemMeta(pm);
        return potion;
    }
	public static void calculateElo(Player winner, Player loser) {
		double R1 = Math.pow(10, (MysqlManager.getElo(winner.getUniqueId())/400));
		double R2 = Math.pow(10, (MysqlManager.getElo(loser.getUniqueId())/400));
		double E1 = (R1 / (R1+R2));
		double E2 = (R2 / (R1+R2));
		int S1 = 1;
		int S2 = 0;
		int r1 = (int)(R1 + 40 * (S1-E1));
		int r2 = (int)(R2 + 40 * (S2-E2));
		MysqlManager.setElo(winner.getUniqueId(), r1);
		MysqlManager.setElo(loser.getUniqueId(), r2);
	}
}
