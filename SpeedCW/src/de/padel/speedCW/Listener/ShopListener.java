package de.padel.speedCW.Listener;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import de.padel.speedCW.GamePhase;
import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.SpeedPlayer;
import de.padel.speedCW.SpeedTeam;
import de.padel.speedCW.Manager.InventoryManager;
import de.padel.speedCW.Manager.MapManager;
import de.padel.speedCW.Utils.Rettungsplattform;
import de.padel.speedCW.Utils.Utils;
import de.padel.tab.Main;
import net.minecraft.server.v1_8_R3.EnumChatFormat;

public class ShopListener implements Listener{
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if(e.getView().getTitle().contains("§cInventar Sortierer")) {
			InventoryManager.saveInv((Player)e.getPlayer(), e.getView().getTopInventory());
			Player p = (Player)e.getPlayer();
			if(SpeedCW.manager.phase.equals(GamePhase.LOBBY)) {
				InventoryManager.loadLobbyInv(p);
			}
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 3, 3);
		}
	}
	@EventHandler
	public void onItemInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.BED_BLOCK && e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		      e.setCancelled(true);
		int slot = p.getInventory().getHeldItemSlot();
		if(item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			String displayname = item.getItemMeta().getDisplayName();
			Material itemMat = item.getType();
			if(itemMat.equals(Material.BLAZE_ROD)) {
				if(displayname.contains("Rettungsplattform")) {
					Location loc = p.getLocation();
					if(loc.getBlock().getType().equals(Material.AIR)) {
						Rettungsplattform.createRettungsplattform(Material.GLASS, -2, p, true, 300);
					}else {
						p.sendMessage(SpeedCW.prefix+ "§cDu kannst die Rettungsplattform hier nicht einsetzen.");
					}
				}
			}else if(itemMat.equals(Material.SULPHUR)) {
				Location loc = p.getLocation();
				new BukkitRunnable() {
					int i = 3;
					@SuppressWarnings("deprecation")
					@Override
					public void run() {
						Location home = new Location(p.getWorld(), 0, 0, 0);
						if(SpeedCW.manager.getSpeedPlayer(p).getTeam().getName().contains("team1")) {
							home = MapManager.getLocation("Team1Spawn");
						}else {
							home = MapManager.getLocation("Team2Spawn");
						}
						if(loc.equals(p.getLocation())) {
							if(i == 0) {
								p.teleport(home);
								p.getInventory().setItem(slot, new ItemStack(Material.AIR));
								p.updateInventory();
								this.cancel();
							}else {
								p.getInventory().setItem(slot, new ItemStack(Material.GLOWSTONE_DUST, 1));
								p.sendTitle("§5Du wirst in §b"+i+" §5teleportiert", "§cBitte bewege dich nicht!");
								i--;
							}
						}else {
							this.cancel();
							p.sendMessage(SpeedCW.prefix+"§cVorgang abgebrochen.");
							p.getInventory().setItem(slot, item);
						}
					}
				}.runTaskTimer(SpeedCW.getInstance(), 0, 20);
			}else if(displayname.contains("§eTeam auswählen")) {
				InventoryManager.loadTeam(p);
			}else if(displayname.contains("§cInventar Sortierer")) {
				InventoryManager.loadInvSort(p);
			}
		}
	}
	@EventHandler
	public void onInventoryInteract(InventoryClickEvent e) {
		if(e.getView().getTitle().contains("§cShop")) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			if(item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
				if(item.getItemMeta().getDisplayName().contains("§7Blöcke")) {
					InventoryManager.loadShopInv("Blöcke", p);
				}else if(item.getItemMeta().getDisplayName().contains("§7Rüstung")) {
					InventoryManager.loadShopInv("Rüstung", p);
				}else if(item.getItemMeta().getDisplayName().contains("§7Spitzhacken")) {
					InventoryManager.loadShopInv("Spitzhacken", p);
				}else if(item.getItemMeta().getDisplayName().contains("§7Schwerter")) {
					InventoryManager.loadShopInv("Schwerter", p);
				}else if(item.getItemMeta().getDisplayName().contains("§7Bögen")) {
					InventoryManager.loadShopInv("Bögen", p);
				}else if(item.getItemMeta().getDisplayName().contains("§7Essen")) {
					InventoryManager.loadShopInv("Essen", p);
				}else if(item.getItemMeta().getDisplayName().contains("§7Tränke")) {
					InventoryManager.loadShopInv("Tränke", p);
				}else if(item.getItemMeta().getDisplayName().contains("§7Spezials")) {
					InventoryManager.loadShopInv("Spezials", p);
				}else if(item.getItemMeta().getDisplayName().contains("§6Blöcke")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 1)) {
						if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
							if(p.getInventory().contains(Material.CLAY_BRICK, 64)) {
								p.getInventory().addItem(Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
								p.getInventory().addItem(Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 64);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 32)) {
								p.getInventory().addItem(Utils.createItem2(Material.SANDSTONE, (short) 2, 64, "§6Blöcke", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 32);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 16)) {
								p.getInventory().addItem(Utils.createItem2(Material.SANDSTONE, (short) 2, 32, "§6Blöcke", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 16);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 8)) {
								p.getInventory().addItem(Utils.createItem2(Material.SANDSTONE, (short) 2, 16, "§6Blöcke", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 8);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 4)) {
								p.getInventory().addItem(Utils.createItem2(Material.SANDSTONE, (short) 2, 8, "§6Blöcke", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 4);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 2)) {
								p.getInventory().addItem(Utils.createItem2(Material.SANDSTONE, (short) 2, 4, "§6Blöcke", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 2);
							}
						}else {
							p.getInventory().addItem(Utils.createItem2(Material.SANDSTONE, (short) 2, 2, "§6Blöcke", Arrays.asList("")));
							removeItems(p.getInventory(), Material.CLAY_BRICK, 1);
						}
					}
				}else if(item.getItemMeta().getDisplayName().contains("§5End-Stone")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 7)) {
						if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
							if(p.getInventory().contains(Material.CLAY_BRICK, 63)) {
								p.getInventory().addItem(Utils.createItem2(Material.ENDER_STONE, (short) 1, 9, "§5End-Stone",
										Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 63);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 35)) {
								p.getInventory().addItem(Utils.createItem2(Material.ENDER_STONE, (short) 1, 5, "§5End-Stone",
										Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 35);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 28)) {
								p.getInventory().addItem(Utils.createItem2(Material.ENDER_STONE, (short) 1, 4, "§5End-Stone",
										Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 28);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 21)) {
								p.getInventory().addItem(Utils.createItem2(Material.ENDER_STONE, (short) 1, 3, "§5End-Stone",
										Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 21);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 14)) {
								p.getInventory().addItem(Utils.createItem2(Material.ENDER_STONE, (short) 1, 2, "§5End-Stone",
										Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 14);
							}
						}else {
							p.getInventory().addItem(Utils.createItem2(Material.ENDER_STONE, (short) 1, 1, "§5End-Stone",
									Arrays.asList("")));
							removeItems(p.getInventory(), Material.CLAY_BRICK, 7);
						}
					}
				}else if(item.getItemMeta().getDisplayName().contains("§7Eisenblock")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 3)) {
						if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
							if(p.getInventory().contains(Material.IRON_INGOT, 63)) {
								p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 21, "§7Eisenblock", Arrays.asList("")));
								removeItems(p.getInventory(), Material.IRON_INGOT, 63);
							}else if(p.getInventory().contains(Material.IRON_INGOT, 30)) {
								p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 10, "§7Eisenblock", Arrays.asList("")));
								removeItems(p.getInventory(), Material.IRON_INGOT, 30);
							}else if(p.getInventory().contains(Material.IRON_INGOT, 27)) {
								p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 9, "§7Eisenblock", Arrays.asList("")));
								removeItems(p.getInventory(), Material.IRON_INGOT, 27);
							}else if(p.getInventory().contains(Material.IRON_INGOT, 24)) {
								p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 8, "§7Eisenblock", Arrays.asList("")));
								removeItems(p.getInventory(), Material.IRON_INGOT, 24);
							}else if(p.getInventory().contains(Material.IRON_INGOT, 21)) {
								p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 7, "§7Eisenblock", Arrays.asList("")));
								removeItems(p.getInventory(), Material.IRON_INGOT, 21);
							}else if(p.getInventory().contains(Material.IRON_INGOT, 18)) {
								p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 6, "§7Eisenblock", Arrays.asList("")));
								removeItems(p.getInventory(), Material.IRON_INGOT, 18);
							}else if(p.getInventory().contains(Material.IRON_INGOT, 15)) {
								p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 5, "§7Eisenblock", Arrays.asList("")));
								removeItems(p.getInventory(), Material.IRON_INGOT, 15);
							}else if(p.getInventory().contains(Material.IRON_INGOT, 12)) {
								p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 4, "§7Eisenblock", Arrays.asList("")));
								removeItems(p.getInventory(), Material.IRON_INGOT, 12);
							}else if(p.getInventory().contains(Material.IRON_INGOT, 9)) {
								p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 3, "§7Eisenblock", Arrays.asList("")));
								removeItems(p.getInventory(), Material.IRON_INGOT, 9);
							}else if(p.getInventory().contains(Material.IRON_INGOT, 6)) {
								p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 2, "§7Eisenblock", Arrays.asList("")));
								removeItems(p.getInventory(), Material.IRON_INGOT, 6);
							}
						}else {
							p.getInventory().addItem(Utils.createItem2(Material.IRON_BLOCK, (short) 1, 1, "§7Eisenblock", Arrays.asList("")));
							removeItems(p.getInventory(), Material.IRON_INGOT, 3);
						}
					}
				}else if(item.getItemMeta().getDisplayName().contains("§fGlas")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 5)) {
						if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
							if(p.getInventory().contains(Material.CLAY_BRICK, 60)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 12, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 60);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 55)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 11, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 55);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 50)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 10, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 50);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 45)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 9, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 45);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 40)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 8, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 40);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 35)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 7, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 35);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 30)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 6, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 30);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 25)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 5, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 25);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 20)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 4, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 20);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 15)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 3, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 15);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 10)) {
								p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 2, "§fGlas", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 10);
							}
						}else {
							p.getInventory().addItem(Utils.createItem2(Material.GLASS, (short) 1, 1, "§fGlas", Arrays.asList("")));
							removeItems(p.getInventory(), Material.CLAY_BRICK, 5);
						}
					}
				}else if(item.getItemMeta().getDisplayName().contains("§fWeb")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 16)) {
						if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
							if(p.getInventory().contains(Material.CLAY_BRICK, 64)) {
								p.getInventory().addItem(Utils.createItem2(Material.WEB, (short) 1, 4, "§fWeb", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 64);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 48)) {
								p.getInventory().addItem(Utils.createItem2(Material.WEB, (short) 1, 3, "§fWeb", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 48);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 32)) {
								p.getInventory().addItem(Utils.createItem2(Material.WEB, (short) 1, 2, "§fWeb", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 32);
							}
						}else {
							p.getInventory().addItem(Utils.createItem2(Material.WEB, (short) 1, 1, "§fWeb", Arrays.asList("")));
							removeItems(p.getInventory(), Material.CLAY_BRICK, 16);
						}
					}
				}else if(item.getItemMeta().getDisplayName().contains("§cLeiter")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 1)) {
						if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
							if(p.getInventory().contains(Material.CLAY_BRICK, 64)) {
								p.getInventory().addItem(Utils.createItem2(Material.LADDER, (short) 1, 64, "§cLeiter", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 64);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 32)) {
								p.getInventory().addItem(Utils.createItem2(Material.LADDER, (short) 1, 32, "§cLeiter", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 32);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 16)) {
								p.getInventory().addItem(Utils.createItem2(Material.LADDER, (short) 1, 16, "§cLeiter", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 16);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 8)) {
								p.getInventory().addItem(Utils.createItem2(Material.LADDER, (short) 1, 8, "§cLeiter", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 8);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 4)) {
								p.getInventory().addItem(Utils.createItem2(Material.LADDER, (short) 1, 4, "§cLeiter", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 4);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 2)) {
								p.getInventory().addItem(Utils.createItem2(Material.LADDER, (short) 1, 2, "§cLeiter", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 2);
							}
						}else {
							p.getInventory().addItem(Utils.createItem2(Material.LADDER, (short) 1, 1, "§cLeiter", Arrays.asList("")));
							removeItems(p.getInventory(), Material.CLAY_BRICK, 1);
						}
					}
				}else if(item.getItemMeta().getDisplayName().contains("Helm")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 1)) {
						Color c = SpeedCW.manager.getSpeedPlayer(p).getTeam().getColor();
						if(c == Color.BLUE) {
							p.getInventory().addItem(Utils.createColoredLeatherItem(Material.LEATHER_HELMET, c, 1, "§1Helm",
									Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("")));
						}else {
							p.getInventory().addItem(Utils.createColoredLeatherItem(Material.LEATHER_HELMET, c, 1, "§cHelm",
									Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("")));
						}
						removeItems(p.getInventory(), Material.CLAY_BRICK, 1);
					}
				}else if(item.getItemMeta().getDisplayName().contains("Hose")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 1)) {
						Color c = SpeedCW.manager.getSpeedPlayer(p).getTeam().getColor();
						if(c == Color.BLUE) {
							p.getInventory().addItem(Utils.createColoredLeatherItem(Material.LEATHER_LEGGINGS, c, 1, "§1Hose",
									Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("")));
						}else {
							p.getInventory().addItem(Utils.createColoredLeatherItem(Material.LEATHER_LEGGINGS, c, 1, "§cHose",
									Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("")));
						}
						removeItems(p.getInventory(), Material.CLAY_BRICK, 1);
					}
				}else if(item.getItemMeta().getDisplayName().contains("Boots")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 1)) {
						Color c = SpeedCW.manager.getSpeedPlayer(p).getTeam().getColor();
						if(c == Color.BLUE) {
							p.getInventory().addItem(Utils.createColoredLeatherItem(Material.LEATHER_BOOTS, c, 1, "§1Boots",
									Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("")));
						}else {
							p.getInventory().addItem(Utils.createColoredLeatherItem(Material.LEATHER_BOOTS, c, 1, "§cBoots",
									Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("")));
						}
						removeItems(p.getInventory(), Material.CLAY_BRICK, 1);
					}
				}else if(item.getItemMeta().getDisplayName().contains("Chestplate III")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 7)) {
						p.getInventory().addItem(Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "Chestplate III",
								Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 3, Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 7);
					}
				}else if(item.getItemMeta().getDisplayName().contains("Chestplate II")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 3)) {
						p.getInventory().addItem(Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1,  "Chestplate II",
								Enchantment.PROTECTION_ENVIRONMENTAL, 2, Enchantment.DURABILITY, 3, Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 3);
					}
				}else if(item.getItemMeta().getDisplayName().contains("Chestplate I")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 1)) {
						p.getInventory().addItem(Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "Chestplate I",
								Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3, Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 1);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§cHolz Spitzhacke")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 4)) {
						p.getInventory().addItem( Utils.createItem(Material.WOOD_PICKAXE, 1, "§cHolz Spitzhacke", Enchantment.DIG_SPEED, 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.CLAY_BRICK, 4);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§8Stein Spitzhacke")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 2)) {
						p.getInventory().addItem(Utils.createItem(Material.STONE_PICKAXE, 1, "§8Stein Spitzhacke",
								Enchantment.DIG_SPEED, 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 2);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§7Eisen Spitzhacke")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 1)) {
						p.getInventory().addItem(Utils.createItem(Material.IRON_PICKAXE, 1, "§7Eisen Spitzhacke",
								Enchantment.DIG_SPEED, 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 1);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§cStick")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 8)) {
						p.getInventory().addItem(Utils.createItem(Material.STICK, 1, "§cStick", Enchantment.KNOCKBACK, 1,
								Enchantment.DURABILITY, 3, Arrays.asList("")));
						removeItems(p.getInventory(), Material.CLAY_BRICK, 8);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§eSchwert III")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 5)) {
						p.getInventory().addItem(Utils.createItem(Material.IRON_SWORD, 1, "§eSchwert III", Enchantment.DURABILITY, 3,
								Enchantment.DAMAGE_ALL, 1, Enchantment.KNOCKBACK, 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 5);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§eSchwert II")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 2)) {
						p.getInventory().addItem(Utils.createItem(Material.GOLD_SWORD, 1, "§eSchwert II", Enchantment.DURABILITY, 3,
								Enchantment.DAMAGE_ALL, 2, Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 2);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§eSchwert I")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 1)) {
						p.getInventory().addItem(Utils.createItem(Material.GOLD_SWORD, 1, "§eSchwert I", Enchantment.DURABILITY, 3,
								Enchantment.DAMAGE_ALL, 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 1);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§fPfeil")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 1)) {
						p.getInventory().addItem(Utils.createItem(Material.ARROW, 1, "§fPfeil", Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 1);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§cBogen III")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 13)) {
						p.getInventory().addItem(Utils.createItem(Material.BOW, 1, "§cBogen III", Enchantment.ARROW_INFINITE, 1,Enchantment.ARROW_DAMAGE,1,Enchantment.ARROW_KNOCKBACK,1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 13);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§cBogen II")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 7)) {
						p.getInventory().addItem(Utils.createItem(Material.BOW, 1, "§cBogen II", Enchantment.ARROW_INFINITE, 1,Enchantment.ARROW_DAMAGE, 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 7);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§cBogen I")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 3)) {
						p.getInventory().addItem(Utils.createItem(Material.BOW, 1, "§cBogen I", Enchantment.ARROW_INFINITE, 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 3);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§aApfel")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 1)) {
						if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
							if(p.getInventory().contains(Material.CLAY_BRICK, 64)) {
								p.getInventory().addItem(Utils.createItem(Material.APPLE, 64, "§aApfel", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 64);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 32)) {
								p.getInventory().addItem(Utils.createItem(Material.APPLE, 32, "§aApfel", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 32);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 16)) {
								p.getInventory().addItem(Utils.createItem(Material.APPLE, 16, "§aApfel", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 16);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 8)) {
								p.getInventory().addItem(Utils.createItem(Material.APPLE, 8, "§aApfel", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 8);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 4)) {
								p.getInventory().addItem(Utils.createItem(Material.APPLE, 4, "§aApfel", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 4);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 2)) {
								p.getInventory().addItem(Utils.createItem(Material.APPLE, 2, "§aApfel", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 2);
							}
						}else {
							p.getInventory().addItem(Utils.createItem(Material.APPLE, 1, "§aApfel", Arrays.asList("")));
							removeItems(p.getInventory(), Material.CLAY_BRICK, 1);
						}
					}
				}else if(item.getItemMeta().getDisplayName().contains("§fFleisch")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 2)) {
						if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
							if(p.getInventory().contains(Material.CLAY_BRICK, 64)) {
								p.getInventory().addItem(Utils.createItem(Material.GRILLED_PORK, 32, "§fFleisch", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 64);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 32)) {
								p.getInventory().addItem(Utils.createItem(Material.GRILLED_PORK, 16, "§fFleisch", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 32);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 16)) {
								p.getInventory().addItem(Utils.createItem(Material.GRILLED_PORK, 8, "§fFleisch", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 16);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 8)) {
								p.getInventory().addItem(Utils.createItem(Material.GRILLED_PORK, 4, "§fFleisch", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 8);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 4)) {
								p.getInventory().addItem(Utils.createItem(Material.GRILLED_PORK, 2, "§fFleisch", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 4);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 2)) {
								p.getInventory().addItem(Utils.createItem(Material.GRILLED_PORK, 1, "§fFleisch", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 2);
							}
						}else {
							p.getInventory().addItem(Utils.createItem(Material.GRILLED_PORK, 1, "§fFleisch", Arrays.asList("")));
							removeItems(p.getInventory(), Material.CLAY_BRICK, 2);
						}
					}
				}else if(item.getItemMeta().getDisplayName().contains("§fKuchen")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 1)) {
						p.getInventory().addItem(Utils.createItem(Material.CAKE, 1, "§fKuchen", Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 1);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§6Apfel")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 2)) {
						p.getInventory().addItem(Utils.createItem(Material.GOLDEN_APPLE,  1, "§6Apfel", Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 2);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§bSpeed")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 7)) {
						p.getInventory().addItem( Utils.getPotionItemStack(PotionType.SPEED, 1, false, false, "§bSpeed", 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 7);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§dHeal II")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 5)) {
						p.getInventory().addItem(Utils.getPotionItemStack(PotionType.INSTANT_HEAL, 2, false, false, "§dHeal II", 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 5);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§dHeal I")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 3)) {
						p.getInventory().addItem(Utils.getPotionItemStack(PotionType.INSTANT_HEAL, 1, false, false, "§dHeal I", 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 3);
					}
					
				}else if(item.getItemMeta().getDisplayName().contains("§cStärke")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 7)) {
						p.getInventory().addItem(Utils.getPotionItemStack(PotionType.STRENGTH, 1, false, false, "§cStärke", 1, Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 7);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§5Enderperle")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 11)) {
						p.getInventory().addItem(Utils.createItem(Material.ENDER_PEARL, 1, "§5Enderperle", Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 11);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§cRettungsplattform")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 3)) {
						p.getInventory().addItem(Utils.createItem(Material.BLAZE_ROD, 1, "§cRettungsplattform", Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 3);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§7Truhe")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 1)) {
						p.getInventory().addItem( Utils.createItem(Material.CHEST,  1, "§7Truhe", Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 1);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§5Endertruhe")) {
					if(p.getInventory().contains(Material.GOLD_INGOT, 1)) {
						p.getInventory().addItem(Utils.createItem(Material.ENDER_CHEST,  1, "§5Endertruhe", Arrays.asList("")));
						removeItems(p.getInventory(), Material.GOLD_INGOT, 1);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§5Teleporter")) {
					if(p.getInventory().contains(Material.IRON_INGOT, 3)) {
						p.getInventory().addItem(Utils.createItem(Material.SULPHUR, 1, "§5Teleporter", Arrays.asList("")));
						removeItems(p.getInventory(), Material.IRON_INGOT, 3);
					}
				}else if(item.getItemMeta().getDisplayName().contains("§eStairs")) {
					if(p.getInventory().contains(Material.CLAY_BRICK, 2)) {
						if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
							if(p.getInventory().contains(Material.CLAY_BRICK, 64)) {
								p.getInventory().addItem(Utils.createItem(Material.SANDSTONE_STAIRS, 32, "§eStairs", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 64);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 32)) {
								p.getInventory().addItem(Utils.createItem(Material.SANDSTONE_STAIRS, 16, "§eStairs", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 32);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 16)) {
								p.getInventory().addItem(Utils.createItem(Material.SANDSTONE_STAIRS, 8, "§eStairs", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 16);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 8)) {
								p.getInventory().addItem(Utils.createItem(Material.SANDSTONE_STAIRS, 4, "§eStairs", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 8);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 4)) {
								p.getInventory().addItem(Utils.createItem(Material.SANDSTONE_STAIRS, 2, "§eStairs", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 4);
							}else if(p.getInventory().contains(Material.CLAY_BRICK, 2)) {
								p.getInventory().addItem(Utils.createItem(Material.SANDSTONE_STAIRS, 1, "§eStairs", Arrays.asList("")));
								removeItems(p.getInventory(), Material.CLAY_BRICK, 2);
							}
						}else {
							p.getInventory().addItem(Utils.createItem(Material.SANDSTONE_STAIRS, 1, "§eStairs", Arrays.asList("")));
							removeItems(p.getInventory(), Material.CLAY_BRICK, 2);
						}
					}
				}
			}
		}else if(e.getView().getTitle().contains("§cInventar Sortierer")) {
			if(e.getClickedInventory() != null  && !e.getClickedInventory().equals(e.getView().getTopInventory()) || e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
				e.setCancelled(true);
			}else {
				e.setCancelled(false);
			}
		}else if(e.getView().getTitle().contains("§cTeam")) {
			e.setCancelled(true);
			ItemStack item = e.getCurrentItem();
			Location team1ArmorStand = MapManager.getLocation("SelectTeam1");
			Location team2ArmorStand = MapManager.getLocation("SelectTeam2");
			if(item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
				Player p = (Player)e.getWhoClicked();
				SpeedTeam blue = SpeedCW.manager.Team1;
				SpeedTeam red = SpeedCW.manager.Team2;
				SpeedPlayer sp = SpeedCW.manager.getSpeedPlayer((Player)e.getWhoClicked());
				if(item.getItemMeta().getDisplayName().contains("§7Team §1Blue")) {
					if(SpeedCW.manager.team1.size() < SpeedCW.cfg.getInt("PlayerPerTeam")) {
						if(!sp.getTeam().equals(blue)) {
							if(InteractListener.armors.containsKey(p.getUniqueId())) {
								ArmorStand as = InteractListener.armors.get(p.getUniqueId());
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
								as.setHelmet(InteractListener.getHead(p));
								as.setChestplate(Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§1Chestplate", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
								as.setLeggings(Utils.createColoredLeatherItem(Material.LEATHER_LEGGINGS, Color.BLUE, 1,  "§1Hose", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
								as.setBoots(Utils.createColoredLeatherItem(Material.LEATHER_BOOTS, Color.BLUE, 1, "§1Boots", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
								p.playSound(team1ArmorStand, Sound.PISTON_EXTEND, 2, 2);
								InteractListener.armors.put(p.getUniqueId(), as);
								Main.getInstance().tM.changeTeam(p, "§1", EnumChatFormat.DARK_BLUE,
										(Main.isUsingBAC.get(p.getUniqueId()) ? " §4✔" : ""), 0);
								Main.getInstance().tM.update();
						}
					}else {
						p.sendMessage(SpeedCW.prefix + "§cDas Team ist voll.");
					}
				}else if(item.getItemMeta().getDisplayName().contains("§7Team §4Red")) {
					if(SpeedCW.manager.team2.size() < SpeedCW.cfg.getInt("PlayerPerTeam")) {
						if(!sp.getTeam().equals(red)) {
							if(InteractListener.armors.containsKey(p.getUniqueId())) {
								ArmorStand as = InteractListener.armors.get(p.getUniqueId());
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
								as.setHelmet(InteractListener.getHead(p));
								as.setChestplate(Utils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§cChestplate", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
								as.setLeggings(Utils.createColoredLeatherItem(Material.LEATHER_LEGGINGS, Color.RED, 1,  "§cHose", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
								as.setBoots(Utils.createColoredLeatherItem(Material.LEATHER_BOOTS, Color.RED, 1, "§cBoots", Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 3));
								p.playSound(team2ArmorStand, Sound.PISTON_EXTEND, 2, 2);
								InteractListener.armors.put(p.getUniqueId(), as);
								Main.getInstance().tM.changeTeam(p, "§c", EnumChatFormat.DARK_RED,
										(Main.isUsingBAC.get(p.getUniqueId()) ? " §4✔" : ""), 1);
								Main.getInstance().tM.update();
						}
					}else {
						p.sendMessage(SpeedCW.prefix + "§cDas Team ist voll.");
					}
				}
			}
		}
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
                amount = -newamount;
                if (amount == 0) break;
            }
            ++n2;
        }
        return b;
    }
}
