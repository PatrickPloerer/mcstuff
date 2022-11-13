package de.padel.buildffa.events;

import java.io.File;
import java.util.ArrayList;

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
import de.padel.buildffa.stats.InvMysql;
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
		if (p.getItemInHand().hasItemMeta()
				&& p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§cItems")
				&& (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if (p.getLocation().getBlockY() < LocationManager.getSpawnHeight() - 2.0D) {
				p.closeInventory();
				e.setCancelled(true);
				return;
			}
			openItemsInv(p);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		String uuid = p.getUniqueId().toString();
		if(e.getView().getTopInventory().getName().contains("§cInventory Sort")) {
			if(e.getCurrentItem().getType() == Material.GOLD_BLOCK) {
				e.setCancelled(true);
			}else if(e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || e.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD)){
				e.setCancelled(true);
			}else {
				e.setCancelled(false);
			}
		}
		if(e.getView().getTopInventory().getName().contains("§cItems")) {
			
			if(e.getCurrentItem() != null &&  e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eBlöcke")) {
					openBlockInv((Player)e.getWhoClicked());
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aSounds")) {
								openSoundInv(p);	
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cSticks")) {
					openStickInv(p);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSchwerter")) {
					openSwordInv(p);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cRüstungs Farbe")) {
					openColorInv(p);
				}
			}
		}
		if(e.getView().getTopInventory().getName().contains("§eBlöcke")) {
			if(e.getCurrentItem() != null &&  e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSandstone")) {
					InvMysql.setAktivBlock(uuid, 0);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cRedsandstone")) {
					InvMysql.setAktivBlock(uuid, 1);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cBrick")) {
					InvMysql.setAktivBlock(uuid, 2);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eEndstone")) {
					InvMysql.setAktivBlock(uuid, 3);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fQuarzblock")) {
					InvMysql.setAktivBlock(uuid, 4);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Glowstone")) {
					InvMysql.setAktivBlock(uuid, 5);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Eisenblock")) {
					InvMysql.setAktivBlock(uuid, 6);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Emeraldblock")) {
					InvMysql.setAktivBlock(uuid, 7);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cRedstoneblock")) {
					InvMysql.setAktivBlock(uuid, 8);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bDiamntblock")) {
					InvMysql.setAktivBlock(uuid, 9);
				}
				p.sendMessage(Main.prefix+"§aBlock erfolgreich gesetzt");
			}
		}
		if(e.getView().getTopInventory().getName().contains("§aSounds")) {
			if(e.getCurrentItem() != null &&  e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eLevel up")) {
					InvMysql.setAktivSound(uuid, 0);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cVillager")) {
					InvMysql.setAktivSound(uuid, 1);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Anvil")) {
					InvMysql.setAktivSound(uuid, 2);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fItem")) {
					InvMysql.setAktivSound(uuid, 3);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Donner")) {
					InvMysql.setAktivSound(uuid, 4);
				}
				p.sendMessage(Main.prefix+"§aSound erfolgreich gesetzt");
			}
		}
		if(e.getView().getTopInventory().getName().contains("§cSticks")) {
			if(e.getCurrentItem() != null &&  e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cStick")) {
					InvMysql.setAktivStick(uuid, 0);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fKnochen")) {
					InvMysql.setAktivStick(uuid, 1);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fFeder")) {
					InvMysql.setAktivStick(uuid, 2);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Blazerod")) {
					InvMysql.setAktivStick(uuid, 3);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cHolzhoe")) {
					InvMysql.setAktivStick(uuid, 4);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cRose")) {
					InvMysql.setAktivStick(uuid, 5);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Schere")) {
					InvMysql.setAktivStick(uuid, 6);
				}
				p.sendMessage(Main.prefix+"§aStick erfolgreich gesetzt");
			}
		}
		if(e.getView().getTopInventory().getName().contains("§eSchwerter")) {
			if(e.getCurrentItem() != null &&  e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Gold Schwert")) {
					InvMysql.setAktivSword(uuid, 0);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cHolz Schwert")) {
					InvMysql.setAktivSword(uuid, 1);
				}
				p.sendMessage(Main.prefix+"§aSchwert erfolgreich gesetzt");
			}
		}
		if(e.getView().getTopInventory().getName().contains("§cRüstungs Farbe")) {
			if(e.getCurrentItem() != null &&  e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cRot")) {
					InvMysql.setAktivColor(uuid, 0);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bAqua")) {
					InvMysql.setAktivColor(uuid, 1);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§0Schwarz")) {
					InvMysql.setAktivColor(uuid, 2);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fWeiß")) {
					InvMysql.setAktivColor(uuid, 3);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLime")) {
					InvMysql.setAktivColor(uuid, 4);
				}
				p.sendMessage(Main.prefix+"§aRüstungsfarbe erfolgreich gesetzt");
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
	public void openColorInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 18, "§cRüstungs Farbe");
		ArrayList<Integer> colors = InvMysql.getRüstungsFarbe(p.getUniqueId().toString());
		for(Integer num : colors) {
			if(num == 0) {
				inv.addItem(createEnchantedItem3(Material.LEATHER_CHESTPLATE, Color.RED, 1, "§cRot", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
			}
			if(num == 1) {
				inv.addItem(createEnchantedItem3(Material.LEATHER_CHESTPLATE, Color.AQUA, 1, "§bAqua", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
			}
			if(num == 2) {
				inv.addItem(createEnchantedItem3(Material.LEATHER_CHESTPLATE, Color.BLACK, 1, "§0Schwarz", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));			
			}
			if(num == 3) {
				inv.addItem(createEnchantedItem3(Material.LEATHER_CHESTPLATE, Color.WHITE, 1, "§fWeiß", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
			}
			if(num == 4) {
				inv.addItem(createEnchantedItem3(Material.LEATHER_CHESTPLATE, Color.LIME, 1, "§aLime", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
			}
		}
		p.openInventory(inv);
	}
	public void openBlockInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 18, "§eBlöcke");
		ArrayList<Integer> colors = InvMysql.getBlocks(p.getUniqueId().toString());
		for(Integer num : colors) {
			if(num == 0) {
				inv.addItem(createItem(Material.SANDSTONE, 1, "§eSandstone"));
			}
			if(num == 1) {
				inv.addItem(createItem(Material.RED_SANDSTONE, 1, "§cRedsandstone"));
			}
			if(num == 2) {
				inv.addItem(createItem(Material.BRICK, 1, "§cBrick"));			
			}
			if(num == 3) {
				inv.addItem(createItem(Material.ENDER_STONE, 1, "§eEndstone"));
			}
			if(num == 4) {
				inv.addItem(createItem(Material.QUARTZ_BLOCK, 1, "§fQuarzblock"));
			}
			if(num == 5) {
				inv.addItem(createItem(Material.GLOWSTONE, 1, "§6Glowstone"));
			}
			if(num == 6) {
				inv.addItem(createItem(Material.IRON_BLOCK, 1, "§7Eisenblock"));
			}
			if(num == 7) {
				inv.addItem(createItem(Material.EMERALD_BLOCK, 1, "§2Emeraldblock"));
			}
			if(num == 8) {
				inv.addItem(createItem(Material.REDSTONE_BLOCK, 1, "§cRedstoneblock"));
			}
			if(num == 9) {
				inv.addItem(createItem(Material.DIAMOND_BLOCK, 1, "§bDiamntblock"));
			}
		}
		p.openInventory(inv);
	}
	public void openStickInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 18, "§cSticks");
		ArrayList<Integer> colors = InvMysql.getSticks(p.getUniqueId().toString());
		for(Integer num : colors) {
			if(num == 0) {
				inv.addItem(createItem(Material.STICK, 1, "§cStick"));
			}
			if(num == 1) {
				inv.addItem(createItem(Material.BONE, 1, "§fKnochen"));
			}
			if(num == 2) {
				inv.addItem(createItem(Material.FEATHER, 1, "§fFeder"));			
			}
			if(num == 3) {
				inv.addItem(createItem(Material.BLAZE_ROD, 1, "§6Blazerod"));
			}
			if(num == 4) {
				inv.addItem(createItem(Material.WOOD_HOE, 1, "§cHolzhoe"));
			}
			if(num == 5) {
				inv.addItem(createItem(Material.RED_ROSE, 1, "§cRose"));
			}
			if(num == 6) {
				inv.addItem(createItem(Material.SHEARS, 1, "§7Schere"));
			}
		}
		p.openInventory(inv);
	}
	public void openSoundInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 18, "§aSounds");
		ArrayList<Integer> colors = InvMysql.getSounds(p.getUniqueId().toString());
		for(Integer num : colors) {
			if(num == 0) {
				inv.addItem(createItem(Material.NOTE_BLOCK, 1, "§eLevel up"));
			}
			if(num == 1) {
				inv.addItem(createItem(Material.NOTE_BLOCK, 1, "§cVillager"));
			}
			if(num == 2) {
				inv.addItem(createItem(Material.NOTE_BLOCK, 1, "§7Anvil"));			
			}
			if(num == 3) {
				inv.addItem(createItem(Material.NOTE_BLOCK, 1, "§fItem"));
			}
			if(num == 4) {
				inv.addItem(createItem(Material.NOTE_BLOCK, 1, "§1Donner"));
			}
		}
		p.openInventory(inv);
	}
	public void openSwordInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 18, "§eSchwerter");
		ArrayList<Integer> colors = InvMysql.getSwords(p.getUniqueId().toString());
		for(Integer num : colors) {
			if(num == 0) {
				inv.addItem(createItem(Material.GOLD_SWORD, 1, "§6Gold Schwert"));
			}
			if(num == 1) {
				inv.addItem(createItem(Material.WOOD_SWORD, 1, "§cHolz Schwert"));
			}
		}
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
