package net.bote.training.frontend.listener;

import net.bote.training.Training;
import net.bote.training.backend.enums.SpectatorState;
import net.bote.training.backend.game.TrainingPlayer;
import net.bote.training.frontend.item.ItemAPI;
import net.bote.training.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Map;

/*
 * Sorry für diesen Code, ehrlich
 */
public class ShopListener implements Listener {
	
	public static boolean goldEnabled = true;

	@EventHandler
	public void onInteractAtEntity(PlayerInteractEntityEvent e) {

		TrainingPlayer trainingPlayer = Training.getInstance().getController().getCWTPlayer(e.getPlayer());

		if(!trainingPlayer.getSpectatorState().equals(SpectatorState.NONE)) {
			e.setCancelled(true);
			return;
		}
		try {
			if(e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
				Player p = e.getPlayer();
				e.setCancelled(true);
				if(!trainingPlayer.getSpectatorState().equals(SpectatorState.NONE)) return;
				Inventory inv = p.getPlayer().getServer().createInventory(null, 9, "�8�� �bShop");
				fillShopLine(inv);
				p.openInventory(inv);
			}
		} catch (NullPointerException ignored) {}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		try {
			if(e.getClickedInventory().getName().equalsIgnoreCase("�8� �bShop")) {
				Inventory inv = Bukkit.createInventory(null, 9*2, "�8� �bShop");
				fillShopLine(inv);
				Player p = (Player) e.getWhoClicked();
				e.setCancelled(true);

				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�a� Bl�cke")) {
					inv.setItem(11, ItemAPI.create(Material.SANDSTONE, "�7Sandstein �e� 1 Bronze", 2));
					inv.setItem(12, ItemAPI.create(Material.EMERALD_BLOCK, "�7Emeraldblock �e� 2 Eisen"));
					inv.setItem(13, ItemAPI.create(Material.ENDER_STONE, "�7End-Stein �e� 8 Bronze"));
					inv.setItem(14, ItemAPI.create(Material.GLASS, "�7Glas �e� 8 Bronze"));
					inv.setItem(15, ItemAPI.create(Material.GLOWSTONE, "�7Glowstone �e� 4 Bronze", 4));

					p.openInventory(inv);
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�a�� Rüstung")) {
					Inventory inv1 = Bukkit.createInventory(null, 9*2, "�8�� �bShop");
					fillShopLine(inv1);
					inv1.setItem(10, ItemAPI.create(Material.LEATHER_HELMET, "�7Helm �e� 1 Bronze" ,1, Enchantment.PROTECTION_ENVIRONMENTAL, 1));
					inv1.setItem(11, ItemAPI.create(Material.LEATHER_LEGGINGS, "�7Hose �e� 1 Bronze" ,1, Enchantment.PROTECTION_ENVIRONMENTAL, 1));
					inv1.setItem(12, ItemAPI.create(Material.LEATHER_BOOTS, "�7Schuhe �e� 1 Bronze" ,1, Enchantment.PROTECTION_ENVIRONMENTAL, 1));
					
					inv1.setItem(14, ItemAPI.create(Material.CHAINMAIL_CHESTPLATE, "�7Brustplatte 1 �e� 1 Eisen", Enchantment.PROTECTION_ENVIRONMENTAL, 1));
					inv1.setItem(15, ItemAPI.create(Material.CHAINMAIL_CHESTPLATE, "�7Brustplatte 2 �e� 3 Eisen", Enchantment.PROTECTION_ENVIRONMENTAL, 2));

					inv1.setItem(16,
							new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
									.setName("�7Brustplatte 3 �e� 7 Eisen")
									.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
									.addEnchantment(Enchantment.THORNS, 1)
									.build());
					
					p.openInventory(inv1);
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�a�� Spitzhacken")) {
					inv.setItem(12, ItemAPI.create(Material.WOOD_PICKAXE, "�7Holz-Spitzhacke �e� 6 Bronze", Enchantment.DIG_SPEED, 1));
					inv.setItem(13, ItemAPI.create(Material.STONE_PICKAXE, "�7Stein-Spitzhacke �e� 2 Eisen", Enchantment.DIG_SPEED, 1));
					if(goldEnabled) {
						inv.setItem(14, ItemAPI.create(Material.IRON_PICKAXE, "�7Eisen-Spitzhacke �e� 1 Gold", Enchantment.DIG_SPEED, 1));
					} else {
						inv.setItem(14, ItemAPI.create(Material.BARRIER, "�cGold ist deaktiviert!"));
					}
						
					p.openInventory(inv);
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�a�� Schwerter")) {
					inv.setItem(11, ItemAPI.create(Material.STICK, "�7Knüppel �e� 8 Bronze", Enchantment.KNOCKBACK, 1));

					inv.setItem(13, new ItemBuilder(Material.GOLD_SWORD).setName("�7Schwert 1 �e� 1 Eisen").addEnchantment(Enchantment.DURABILITY, 1).addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
					inv.setItem(14, new ItemBuilder(Material.GOLD_SWORD).setName("�7Schwert 1 �e� 3 Eisen").addEnchantment(Enchantment.DURABILITY, 1).addEnchantment(Enchantment.DAMAGE_ALL, 3).build());
					
					if(goldEnabled)
						inv.setItem(15, new ItemBuilder(Material.IRON_SWORD).setName("�7Eisenschwert �e� 3 Gold").addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
					else inv.setItem(15, ItemAPI.create(Material.BARRIER, "�cGold ist deaktiviert!"));
					
					p.openInventory(inv);
					
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�a�� Bögen")) {
					
					inv.setItem(12, ItemAPI.create(Material.BOW, "�7Bogen 1 �e� 3 Gold", Enchantment.DURABILITY, 1));
					
					ArrayList<Enchantment> enchantments = new ArrayList<>();
					ArrayList<Integer> strenght = new ArrayList<>();
					
					enchantments.add(Enchantment.ARROW_DAMAGE);
					strenght.add(1);
					enchantments.add(Enchantment.DURABILITY);
					strenght.add(1);
					
					inv.setItem(13, ItemAPI.create(Material.BOW, "�7Bogen 2 �e� 7 Gold", 1, enchantments, strenght));
					
					enchantments.clear();
					
					enchantments.add(Enchantment.ARROW_DAMAGE);
					strenght.add(2);
					enchantments.add(Enchantment.DURABILITY);
					strenght.add(1);
					enchantments.add(Enchantment.ARROW_KNOCKBACK);
					strenght.add(1);
					
					inv.setItem(14, ItemAPI.create(Material.BOW, "�7Bogen 3 �e� 13 Gold", 1, enchantments, strenght));
					
					inv.setItem(16, ItemAPI.create(Material.ARROW, "�7Pfeil �e� 1 Eisen"));
					
					p.openInventory(inv);
					
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�a�� Essen")) {
					
					inv.setItem(9, ItemAPI.createAmount(Material.COOKED_CHICKEN, "�7Hühnchenfleisch �e� 2 Bronze", 4));
					inv.setItem(10, ItemAPI.createAmount(Material.COOKED_BEEF, "�7Steak �e� 2 Bronze", 4));
					inv.setItem(11, ItemAPI.createAmount(Material.COOKIE, "�7Kekse �e� 1 Bronze", 4));
					inv.setItem(13, ItemAPI.createAmount(Material.GOLDEN_APPLE, "�7Goldener Apfel �e� 5 Eisen", 1));
					inv.setItem(15, ItemAPI.create(Material.CAKE, "�7Kuchen �e� 1 Eisen"));
					inv.setItem(16, ItemAPI.createAmount(Material.COOKED_RABBIT, "�7Hasenkeule �e� 2 Bronze", 4));
					inv.setItem(17, ItemAPI.createAmount(Material.COOKED_MUTTON, "�7Rinderschinken �e� 2 Bronze", 4));
					
					p.openInventory(inv);
					
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�a�� Kisten")) {
					
					inv.setItem(13, ItemAPI.createAmount(Material.CHEST, "�7Kiste �e� 2 Eisen", 1));
					
					p.openInventory(inv);
					
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�a�� Tränke")) {
					
					inv.setItem(11, ItemAPI.create(Material.POTION, 1, (short) 8197, "�7Heilung 1 �e� 5 Eisen"));
					inv.setItem(12, ItemAPI.create(Material.POTION, 1, (short) 8229, "�7Heilung 2 �e� 8 Eisen"));
					if(goldEnabled) {
						inv.setItem(13, ItemAPI.create(Material.POTION, 1, (short) 8201, "�7Stärke �e� 10 Gold"));
					} else {
						inv.setItem(13, ItemAPI.create(Material.BARRIER, "�cGold ist deaktiviert!"));
					}
					inv.setItem(14, ItemAPI.create(Material.POTION, 1, (short) 8193, "�7Regeneration �e� 7 Eisen"));
					inv.setItem(15, ItemAPI.create(Material.POTION, 1, (short) 8203, "�7Sprungkraft �e� 3 Eisen"));
					
					
					p.openInventory(inv);
					
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�a�� Spezial")) {
					inv.setItem(12, ItemAPI.create(Material.LADDER, "�7Leiter �e� 16 Bronze"));
					if(goldEnabled) {
						inv.setItem(13, ItemAPI.create(Material.ENDER_PEARL, "�7Enderperle �e� 14 Gold"));
					} else {
						inv.setItem(13, ItemAPI.create(Material.BARRIER, "�cGold ist deaktiviert!"));
					}
					inv.setItem(14, ItemAPI.create(Material.WEB, "�7Spinnweben �e� 20 Bronze"));
					
					p.openInventory(inv);
					
				}
				
				
				if(e.getCurrentItem().getItemMeta().getDisplayName().contains("�e�")) {
					// is a shop item
					
					// get datas
					
					String displayname = e.getCurrentItem().getItemMeta().getDisplayName();
					
					String itemname = displayname.split(" �e� ")[0];
					
					int price = Integer.parseInt(displayname.split(" �e� ")[1].split(" ")[0]);
					
					Material material;
					
					if(displayname.split(" �e� ")[1].split(" ")[1].equalsIgnoreCase("Gold")) {
						material = Material.GOLD_INGOT;
					} else if(displayname.split(" �e� ")[1].split(" ")[1].equalsIgnoreCase("Eisen")) {
						material = Material.IRON_INGOT;
					} else {
						material = Material.CLAY_BRICK;
					}
					
					if(p.getInventory().contains(material, price)) {
						
						p.playSound(p.getLocation(), Sound.NOTE_STICKS, 2, 3);
						
						if(e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && p.getInventory().contains(material, (price * 64) / e.getCurrentItem().getAmount()) && (e.getCurrentItem().getAmount() != 1 || isBlock(material))) {
							
							ItemStack i = new ItemStack(e.getCurrentItem());
							
							int amount = e.getCurrentItem().getAmount() * 64;
							
							if(amount > 64) {
								amount = 64;
							}
							
							i.setAmount(amount);
							ItemMeta meta = i.getItemMeta();
							
							meta.setDisplayName(itemname);
							
							i.setItemMeta(meta);
							
							p.getInventory().addItem(i);
							
							removeItems(p.getInventory(), material, (price * 64) / e.getCurrentItem().getAmount());
							p.updateInventory();
						} else if(e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && p.getInventory().contains(material, price * 32) && (e.getCurrentItem().getAmount() != 1 || isBlock(material))) {
							
							ItemStack i = new ItemStack(e.getCurrentItem());
							i.setAmount(32);
							ItemMeta meta = i.getItemMeta();
							
							meta.setDisplayName(itemname);
							
							i.setItemMeta(meta);
							
							p.getInventory().addItem(i);
							
							removeItems(p.getInventory(), material, price * 32);
							p.updateInventory();
							
						} else if(e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && p.getInventory().contains(material, price * 16) && (e.getCurrentItem().getAmount() != 1 || isBlock(material))) {
							
							ItemStack i = new ItemStack(e.getCurrentItem());
							i.setAmount(16);
							ItemMeta meta = i.getItemMeta();
							
							meta.setDisplayName(itemname);
							
							i.setItemMeta(meta);
							
							p.getInventory().addItem(i);
							
							removeItems(p.getInventory(), material, price * 16);
							p.updateInventory();
							
						} else {
							
							if(e.getCurrentItem().getType() == Material.LEATHER_HELMET || e.getCurrentItem().getType() == Material.LEATHER_LEGGINGS || e.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
								
								ItemStack item = new ItemStack(e.getCurrentItem());
                                LeatherArmorMeta itemm = (LeatherArmorMeta) item.getItemMeta();

                                itemm.setColor(Training.getInstance().getController().getCWTPlayer(p).getTeam().getColor());
                                itemm.setDisplayName(itemname);

                                Map<Enchantment, Integer> enchanments = e.getCurrentItem().getItemMeta().getEnchants();

                                for(Enchantment ench : enchanments.keySet()) itemm.addEnchant(ench, enchanments.get(ench), true);

                                item.setItemMeta(itemm);
                                p.getInventory().addItem(item);
                                
                                removeItems(p.getInventory(), material, price);
								
							} else {
								ItemStack i = new ItemStack(e.getCurrentItem());
								ItemMeta meta = i.getItemMeta();
								
								meta.setDisplayName(itemname);
								
								i.setItemMeta(meta);
								
								p.getInventory().addItem(i);
								
								removeItems(p.getInventory(), material, price);
							}
							
						}
						
					} else {
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 2, 3);
					}
					
				}
				
			}
		} catch (NullPointerException ignored) {}
	}
	
	private boolean isBlock(Material mat) {
		if(mat.equals(Material.SANDSTONE) || mat.equals(Material.GLASS) || mat.equals(Material.ENDER_STONE) || mat.equals(Material.GLOWSTONE) || mat.equals(Material.IRON_BLOCK)) {
			return true;
		}
		return false;
	}
	
	public static boolean removeItems(Inventory inv, Material type, int amount) {
        boolean b = false;
        ItemStack[] arritemStack = inv.getContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack is = arritemStack[n2];
            if (is != null && is.getType() == type) {
                b = true;
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                }
                inv.remove(is);
                amount = - newamount;
                if (amount == 0) break;
            }
            ++n2;
        }
        return b;
    }
	
	public void fillShopLine(Inventory inv) {
		
		ItemStack item1 = new ItemStack(Material.SANDSTONE);
		ItemMeta meta1 = item1.getItemMeta();
		meta1.setDisplayName("�a�� Blöcke");
		ArrayList<String> lore1 = new ArrayList<>();
		lore1.add("�8� �cHier kannst du Blöcke kaufen.");
        meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		
		ItemStack item2 = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta meta2 = item2.getItemMeta();
		meta2.setDisplayName("�a�� Rüstung");
		ArrayList<String> lore2 = new ArrayList<String>();
		lore2.add("�8� �cHier kannst du dich mit Rüstungen ausrüsten.");
		meta2.setLore(lore2);
		item2.setItemMeta(meta2);
		
		ItemStack item3 = new ItemStack(Material.STONE_PICKAXE);
		ItemMeta meta3 = item3.getItemMeta();
		meta3.setDisplayName("�a�� Spitzhacken");
		ArrayList<String> lore3 = new ArrayList<String>();
		lore3.add("�8� �cHier kannst du Spitzhacken erwerben.");
		meta3.setLore(lore3);
		item3.setItemMeta(meta3);
		
		ItemStack item4 = new ItemStack(Material.GOLD_SWORD);
		ItemMeta meta4 = item4.getItemMeta();
		meta4.setDisplayName("�a�� Schwerter");
		ArrayList<String> lore4 = new ArrayList<String>();
		lore4.add("�8� �cRüste dich mit Schwertern aus.");
		meta4.setLore(lore4);
		item4.setItemMeta(meta4);
		
		ItemStack item5;
		
		if(goldEnabled) {
			item5 = new ItemStack(Material.BOW);
			ItemMeta meta5 = item5.getItemMeta();
			meta5.setDisplayName("�a�� Bögen");
			ArrayList<String> lore5 = new ArrayList<String>();
			lore5.add("�8� �cKaufe hier einen Bogen.");
			meta5.setLore(lore5);
			item5.setItemMeta(meta5);
		} else {
			item5 = new ItemStack(Material.BARRIER);
			ItemMeta meta5 = item5.getItemMeta();
			meta5.setDisplayName("�cKein Gold!");
			ArrayList<String> lore5 = new ArrayList<String>();
			lore5.add("�cGold ist in dieser Runde verboten!");
			meta5.setLore(lore5);
			item5.setItemMeta(meta5);
		}
		
		ItemStack item6 = new ItemStack(Material.APPLE);
		ItemMeta meta6 = item6.getItemMeta();
		meta6.setDisplayName("�a�� Essen");
		ArrayList<String> lore6 = new ArrayList<String>();
		lore6.add("�8� �cHier kannst du Essen kaufen.");
		meta6.setLore(lore6);
		item6.setItemMeta(meta6);
		
		ItemStack item7 = new ItemStack(Material.CHEST);
		ItemMeta meta7 = item7.getItemMeta();
		meta7.setDisplayName("�a�� Kisten");
		ArrayList<String> lore7 = new ArrayList<String>();
		lore7.add("�8� �cHier kannst du Truhen kaufen.");
		meta7.setLore(lore7);
		item7.setItemMeta(meta7);
		
		ItemStack item8 = new ItemStack(Material.POTION, 1,(short) 8225);
		ItemMeta meta8 = item8.getItemMeta();
		meta8.setDisplayName("�a�� Tränke");
		meta8.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		ArrayList<String> lore8 = new ArrayList<String>();
		lore8.add("�8� �cHier kannst du dir Tränke kaufen.");
		meta8.setLore(lore8);
		item8.setItemMeta(meta8);
		
		ItemStack item9 = new ItemStack(Material.TNT);
		ItemMeta meta9 = item9.getItemMeta();
		meta9.setDisplayName("�a�� Spezial");
		ArrayList<String> lore9 = new ArrayList<String>();
		lore9.add("�8� �cKaufe spezielle Items.");
		meta9.setLore(lore9);
		item9.setItemMeta(meta9);
		
		inv.setItem(0, item1);
		inv.setItem(1, item2);
		inv.setItem(2, item3);
		inv.setItem(3, item4);
		inv.setItem(4, item5);
		inv.setItem(5, item6);
		inv.setItem(6, item7);
		inv.setItem(7, item8);
		inv.setItem(8, item9);
		
	}
	
}
