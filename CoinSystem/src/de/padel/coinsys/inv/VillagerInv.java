package de.padel.coinsys.inv;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.padel.coinsys.main.CoinAPI.Callback;
import de.padel.coinsys.main.Main;

public class VillagerInv {
	@SuppressWarnings("rawtypes")
	public static void loadInv(Player p) {
		if (Main.isMySQLEnabled()) {
			Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§6Tägliche Belohnung§7(§6en§7)");
			for (int f = 0; f <= 26; f++) {
				i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-"));
			}
			Main.utils.getAllAllowRewads(p, new Callback<HashMap>() {
				@Override
				public void onSuccess(HashMap data) {
					boolean one = (boolean) data.get("1");
					boolean two = (boolean) data.get("2");
					boolean thr = (boolean) data.get("3");
					if(one) {
						i.setItem(11, createItem(Material.GLOWSTONE_DUST, 1, "§7Spieler"));
					}else {
						i.setItem(11, createItem(Material.SULPHUR, 1, "§cKomm später wieder!"));
					}
					if(two) {
						i.setItem(13, createItem(Material.GLOWSTONE_DUST, 1, "§6Premium"));	
					}else {
						i.setItem(13, createItem(Material.SULPHUR, 1, "§cKomm später wieder!"));
					}
					if(thr) {
						i.setItem(15, createItem(Material.GLOWSTONE_DUST, 1, "§5Youtuber"));
					}else {
						i.setItem(15, createItem(Material.SULPHUR, 1, "§cKomm später wieder!"));
					}
					p.openInventory(i);
				}
			});
		}
	}

	private static ItemStack createItem(Material mat, int anzahl, String name) {
		ItemStack i = new ItemStack(mat, anzahl);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.spigot().setUnbreakable(true);
		i.setItemMeta(m);
		return i;
	}

	private static ItemStack createItem2(Material mat, short h, int anzahl, String name) {
		ItemStack i = new ItemStack(mat, anzahl, h);
		i.setAmount(anzahl);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.spigot().setUnbreakable(true);
		i.setItemMeta(m);
		return i;
	}
}
