package net.bote.training.frontend.item;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemAPI {

	public static ItemStack create(Material m, String ItemName) {
		ItemStack itemstackinvoid = new ItemStack(m);
		ItemMeta itemmetainvoid = itemstackinvoid.getItemMeta();
		
		if(ItemName != null) itemmetainvoid.setDisplayName(ItemName);
		
		itemstackinvoid.setItemMeta(itemmetainvoid);
		return itemstackinvoid;
	}
	
	public static ItemStack createAmount(Material m, String ItemName, int amount) {
		ItemStack itemstackinvoid = new ItemStack(m, amount);
		ItemMeta itemmetainvoid = itemstackinvoid.getItemMeta();
		
		if(ItemName != null) itemmetainvoid.setDisplayName(ItemName);
		
		itemstackinvoid.setItemMeta(itemmetainvoid);
		return itemstackinvoid;
	}
	
	public static ItemStack create(Material m, int anzahl, short subid, String displayname) {
		ItemStack itemstackinvoid = new ItemStack(m, anzahl, subid);
		ItemMeta itemmetainvoid = itemstackinvoid.getItemMeta();
		
		itemmetainvoid.setDisplayName(displayname);
		
		itemstackinvoid.setItemMeta(itemmetainvoid);
		return itemstackinvoid;
	}
	
	public static ItemStack create(Material m, String ItemName, int amount) {
		ItemStack itemstackinvoid = new ItemStack(m, amount);
		ItemMeta itemmetainvoid = itemstackinvoid.getItemMeta();
		
		if(ItemName != null) itemmetainvoid.setDisplayName(ItemName);
		
		itemstackinvoid.setItemMeta(itemmetainvoid);
		return itemstackinvoid;
	}
	
	public static ItemStack create(Material m, String ItemName, Enchantment e, int strenght) {
		ItemStack itemstackinvoid = new ItemStack(m);
		ItemMeta itemmetainvoid = itemstackinvoid.getItemMeta();
		
		if(ItemName != null) itemmetainvoid.setDisplayName(ItemName);
		itemmetainvoid.addEnchant(e, strenght, true);
		itemstackinvoid.setItemMeta(itemmetainvoid);
		return itemstackinvoid;
	}
	
	public static ItemStack create(Material m, String displayname ,int amount, Enchantment e, int strenght) {
		ItemStack itemstackinvoid = new ItemStack(m);
		ItemMeta itemmetainvoid = itemstackinvoid.getItemMeta();
		
		itemmetainvoid.addEnchant(e, strenght, true);
		itemmetainvoid.setDisplayName(displayname);
		itemstackinvoid.setItemMeta(itemmetainvoid);
		return itemstackinvoid;
	}
	
	public static ItemStack create(Material m, String displayname ,int amount, ArrayList<Enchantment> ens, ArrayList<Integer> ints) {
		ItemStack itemstackinvoid = new ItemStack(m);
		ItemMeta itemmetainvoid = itemstackinvoid.getItemMeta();
		
		for(int i = 0; i < ens.size(); i++) {
			itemmetainvoid.addEnchant(ens.get(i), ints.get(i), true);
		}
		itemmetainvoid.setDisplayName(displayname);
		itemstackinvoid.setItemMeta(itemmetainvoid);
		return itemstackinvoid;
	}
}
