package de.padel.buildffa.events;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.padel.buildffa.main.Main;
import de.padel.buildffa.utils.InvManager;
import de.padel.buildffa.utils.LocationManager;

public class InvSortListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().hasItemMeta()
				&& p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§cInventory Sort")
				&& (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if (p.getLocation().getBlockY() < LocationManager.getSpawnHeight() - 2.0D) {
				p.closeInventory();
				e.setCancelled(true);
				return;
			}
			Inventory inv = Bukkit.createInventory(p, 9, "§cInventory Sort");
			inv.clear();
			p.getInventory().clear();
			inv.setContents(InvManager.loadPlayerinv(p).getContents());
			p.openInventory(inv);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemClick(InventoryClickEvent e) {
		if(e.getView().getTopInventory().getName().contains("§cInventory Sort")) {
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != null && e.getCurrentItem().getType() == Material.GOLD_BLOCK) {
				e.setCancelled(true);
			}else if(e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || e.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD)){
				e.setCancelled(true);
			}else {
				e.setCancelled(false);
			}
		}
		
	}
	public void openItemsInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "§cItems");
		inv.setItem(2, createItem(Material.SANDSTONE, 1, "§eBlöcke"));
		inv.setItem(3, createItem(Material.NOTE_BLOCK, 1, "§aSounds"));
		inv.setItem(4, createItem(Material.STICK, 1, "§cSticks"));
		inv.setItem(5, createItem(Material.GOLD_SWORD, 1, "§eSchwerter"));
		inv.setItem(6, createItem(Material.LEATHER_CHESTPLATE, 1, "§cRüstungs Farbe"));
		p.openInventory(inv);
	}

	public static ItemStack createItem(Material mat, int anzahl, String name) {
		ItemStack i = new ItemStack(mat, anzahl);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
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
	
	public void checkOrdner() {
		File file = new File("plugins/BFFA/invs");
		if (!file.exists())
			file.mkdir();
	}
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		final Player p = (Player) e.getPlayer();
		if (e.getInventory().getName().equalsIgnoreCase("§cInventory Sort")) {
			if (e.getView().getCursor().getType() != null) {
				Inventory inv = InvManager.loadPlayerinv(p);
				for (int i = 0; i < inv.getSize(); i++) {
					if (e.getView().getCursor().equals(inv.getItem(i))) {
						e.getInventory().setItem(i, inv.getItem(i));
					}
				}
			}
			Inventory inve = e.getInventory();
			InvManager.saveInv(p, inve);
			new BukkitRunnable() {
				
				public void run() {
					p.getInventory().clear();
					InvManager.setSpawnInv(p);
				}
			}.runTaskLater(Main.getInstance(), 1);
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0F, 2.0F);
		}
	}
}
