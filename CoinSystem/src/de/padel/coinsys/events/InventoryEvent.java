package de.padel.coinsys.events;

import de.padel.bffa.api.InvMysql;
import de.padel.coinsys.inv.ShopInv;
import de.padel.coinsys.inv.VillagerInv;
import de.padel.coinsys.main.CoinAPI;
import de.padel.coinsys.main.CoinAPI.Callback;
import de.padel.coinsys.main.Main;
import de.padel.itemsystem.main.ItemAPI;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryEvent implements Listener {
	
	public HashMap<UUID, Integer> daily = new HashMap<>();
	public HashMap<UUID, Integer> shop = new HashMap<>();
	
	public boolean isVill = false;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(!isVill) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					Main.getInstance().checkVillager();
					Main.getInstance().checkShop();		
				}
			}.runTaskLater(Main.getInstance(), 40);
			isVill = true;
		}
	}
	
	@EventHandler
	public void onInteractAtEntity(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked().getCustomName() == null)
			return;
		if (e.getRightClicked().getCustomName().equalsIgnoreCase("§6Tägliche Belohnung§7(§6en§7)")) {
			e.setCancelled(true);
			if(daily.containsKey(p.getUniqueId())) {
				if(daily.get(p.getUniqueId()) == 0) {
					VillagerInv.loadInv(p);
					daily.replace(p.getUniqueId(), 5);
				}else {
					p.sendMessage(Main.prefix +"§7Bitte warte noch §6"+daily.get(p.getUniqueId())+" §7Sekunden!");
				}
			}else {
				VillagerInv.loadInv(p);
				daily.put(p.getUniqueId(), 5);
			}
		}
		if (e.getRightClicked().getCustomName().equalsIgnoreCase("§cShop")) {
			e.setCancelled(true);
			if(shop.containsKey(p.getUniqueId())) {
				if(shop.get(p.getUniqueId()) == 0) {
					ShopInv.loadInv(p);
					shop.replace(p.getUniqueId(), 5);
				}else {
					p.sendMessage(Main.prefix +"§7Bitte warte noch §6"+shop.get(p.getUniqueId())+" §7Sekunden!");
				}
			}else {
				ShopInv.loadInv(p);
				shop.put(p.getUniqueId(), 5);
			}
		}
	}
	public void startTimer() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				Iterator<?> it =  daily.entrySet().iterator();
				while(it.hasNext()) {
					Map.Entry<UUID, Integer> pair = (Map.Entry<UUID, Integer>) it.next();
					if(pair.getValue() > 0) {
						pair.setValue(pair.getValue()-1);
					}
				}
				Iterator<?> itt =  shop.entrySet().iterator();
				while(itt.hasNext()) {
					Map.Entry<UUID, Integer> pairr = (Map.Entry<UUID, Integer>) itt.next();
					if(pairr.getValue() > 0) {
						pairr.setValue(pairr.getValue()-1);
					}
				}
			}
		}, 0, 20);
	}
	

	@SuppressWarnings("rawtypes")
	@EventHandler
	public void onInvInteract(InventoryClickEvent e) {
		if (e != null && e.getCurrentItem() != null) {
			if (!(e.getCurrentItem().hasItemMeta()))
				return;
			if (!(e.getCurrentItem().getItemMeta().hasDisplayName()))
				return;
			if ((e.getWhoClicked().getItemInHand() != null && e.getWhoClicked().getItemOnCursor() != null
					&& !e.getClick().equals(ClickType.WINDOW_BORDER_LEFT))
					|| (!e.getClick().equals(ClickType.WINDOW_BORDER_RIGHT) && e.getCurrentItem().getType() != null
							&& e.getCurrentItem().hasItemMeta())) {
				Material mat = e.getCurrentItem().getType();
				Player p = (Player) e.getWhoClicked();
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cKomm später wieder!")
					e.setCancelled(true);
				if (mat == Material.GLOWSTONE_DUST && e.getCurrentItem().getItemMeta().getDisplayName() == "§7Spieler")
					if (e.getWhoClicked().hasPermission("daily.default")) {
						e.setCurrentItem(createItem(Material.SULPHUR, 1, "§cKomm später wieder!"));
						p.sendMessage(String.valueOf(Main.prefix) + "§7Dir wurden §630 Coins §7hinzugefügt!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0F, 2.0F);
						CoinAPI.addCoins(p.getUniqueId().toString(), 30);
						Main.utils.setReward(p);
						e.setCancelled(true);
					} else {
						p.playSound(p.getLocation(), Sound.VILLAGER_HIT, 2.0F, 2.0F);
						e.setCancelled(true);
					}
				if (mat == Material.GLOWSTONE_DUST && e.getCurrentItem().getItemMeta().getDisplayName() == "§6Premium")
					if (e.getWhoClicked().hasPermission("daily.premium")) {
						e.setCurrentItem(createItem(Material.SULPHUR, 1, "§cKomm später wieder!"));
						p.sendMessage(String.valueOf(Main.prefix) + "§7Dir wurden §630 Coins §7hinzugefügt!");
						CoinAPI.addCoins(p.getUniqueId().toString(), 30);
						Main.utils.setReward1(p);
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0F, 2.0F);
						e.setCancelled(true);
					} else {
						p.playSound(p.getLocation(), Sound.VILLAGER_HIT, 2.0F, 2.0F);
						e.setCancelled(true);
					}
				if (mat == Material.GLOWSTONE_DUST && e.getCurrentItem().getItemMeta().getDisplayName() == "§5Youtuber")
					if (e.getWhoClicked().hasPermission("daily.youtuber")) {
						e.setCurrentItem(createItem(Material.SULPHUR, 1, "§cKomm später wieder!"));
						p.sendMessage(String.valueOf(Main.prefix) + "§7Dir wurden §630 Coins §7hinzugefügt!");
						CoinAPI.addCoins(p.getUniqueId().toString(), 30);
						Main.utils.setReward2(p);
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0F, 2.0F);
						e.setCancelled(true);
					} else {
						p.playSound(p.getLocation(), Sound.VILLAGER_HIT, 2.0F, 2.0F);
						e.setCancelled(true);
					}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cFeuer Schuhe")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {

						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 600) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "1")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -600);
									ItemAPI.addItem(p.getUniqueId().toString(), "1");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §cFeuer Schuhe §7für §6600 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§5Liebes Schuhe")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {

						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 600) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "2")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -600);
									ItemAPI.addItem(p.getUniqueId().toString(), "2");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §5Liebes Schuhe §7für §6600 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}

					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§8Rauch Schuhe")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 600) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "3")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -600);
									ItemAPI.addItem(p.getUniqueId().toString(), "3");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §8Rauch Schuhe §7für §6600 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§bRegen Schuhe")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 600) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "4")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -600);
									ItemAPI.addItem(p.getUniqueId().toString(), "4");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §bRegen Schuhe §7für §6600 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aMusik Schuhe")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 600) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "5")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -600);
									ItemAPI.addItem(p.getUniqueId().toString(), "5");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §aMusik Schuhe §7für §6600 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§6Lava Schuhe")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 600) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "6")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -600);
									ItemAPI.addItem(p.getUniqueId().toString(), "6");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §6Lava Schuhe §7für §6600 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§7Geister Schuhe")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 900) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "7")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -900);
									ItemAPI.addItem(p.getUniqueId().toString(), "7");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §7Geister Schuhe §7für §6900 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§dBaby-Schwein")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 800) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "8")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -800);
									ItemAPI.addItem(p.getUniqueId().toString(), "8");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §dBaby-Schwein §7für §6800 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§dSchwein")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 600) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "9")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -600);
									ItemAPI.addItem(p.getUniqueId().toString(), "9");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §dSchwein §7für §6600 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§7Baby-Kuh")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 800) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "10")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -800);
									ItemAPI.addItem(p.getUniqueId().toString(), "10");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §7Baby-Kuh §7für §6800 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§7Kuh")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 600) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "11")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -600);
									ItemAPI.addItem(p.getUniqueId().toString(), "11");
									p.sendMessage(
											String.valueOf(Main.prefix) + "§7Du hast dir §7Kuh §7für §6600 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§fHühnchen")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 800) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "12")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -800);
									ItemAPI.addItem(p.getUniqueId().toString(), "12");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §fHühnchen §7für §6800 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§fHuhn")
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 600) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "13")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -600);
									ItemAPI.addItem(p.getUniqueId().toString(), "13");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §fHuhn §7für §6600 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§bWächter") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");

							if (coins >= 1200) {
								if (ItemAPI.hasItem(p.getUniqueId().toString(), "14")) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1200);
									ItemAPI.addItem(p.getUniqueId().toString(), "14");
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §bWächter §7für §61200 §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				UUID uuid = e.getWhoClicked().getUniqueId();
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§croter Sandstein") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 800) {
								if (InvMysql.hasBlock(e.getWhoClicked().getUniqueId().toString(), 1)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -800);
									InvMysql.addBlock(uuid.toString(), 1);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §cRoter Sandstein für §6800 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cBrick") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1000) {
								if (InvMysql.hasBlock(e.getWhoClicked().getUniqueId().toString(), 2)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1000);
									InvMysql.addBlock(uuid.toString(), 2);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §cBrick §7für §61000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§eEndstone") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1000) {
								if (InvMysql.hasBlock(e.getWhoClicked().getUniqueId().toString(), 3)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1000);
									InvMysql.addBlock(uuid.toString(), 3);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §eEndstein §7für §61000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});

				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§fQuarz Block") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1200) {
								if (InvMysql.hasBlock(e.getWhoClicked().getUniqueId().toString(), 4)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1200);
									InvMysql.addBlock(uuid.toString(), 4);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §fQuarz Block §7für §61200 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§eGlowstone") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1200) {
								if (InvMysql.hasBlock(e.getWhoClicked().getUniqueId().toString(), 5)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1200);
									InvMysql.addBlock(uuid.toString(), 5);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §eGlowstone §7für §61200 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§7Eisen Block") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1500) {
								if (InvMysql.hasBlock(e.getWhoClicked().getUniqueId().toString(), 6)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1500);
									InvMysql.addBlock(uuid.toString(), 6);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §7Eisen Block §7für §61500 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§2Emerald Block") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1800) {
								if (InvMysql.hasBlock(e.getWhoClicked().getUniqueId().toString(), 7)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1800);
									InvMysql.addBlock(uuid.toString(), 7);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §2Emerald Block §7für §61800 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});

				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cRedstone Block") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1800) {
								if (InvMysql.hasBlock(e.getWhoClicked().getUniqueId().toString(), 8)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1800);
									InvMysql.addBlock(uuid.toString(), 8);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §cRedstone Block §7für §61800 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});

				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§bDiamant Block") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 2000) {
								if (InvMysql.hasBlock(e.getWhoClicked().getUniqueId().toString(), 9)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -2000);
									InvMysql.addBlock(uuid.toString(), 9);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §bDiamant Block §7für §62000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});

				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§fKnochen") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1000) {
								if (InvMysql.hasStick(e.getWhoClicked().getUniqueId().toString(), 1)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1000);
									InvMysql.addStick(uuid.toString(), 1);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §fKnochen §7für §61000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});

				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§fFeder") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1000) {
								if (InvMysql.hasStick(e.getWhoClicked().getUniqueId().toString(), 2)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1000);
									InvMysql.addStick(uuid.toString(), 2);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §fFeder §7für §61000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§eBlaze Rod") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1500) {
								if (InvMysql.hasStick(e.getWhoClicked().getUniqueId().toString(), 3)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1500);
									InvMysql.addStick(uuid.toString(), 3);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §eBlaze Rod §7für §61500 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cHolzhoe") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1500) {
								if (InvMysql.hasStick(e.getWhoClicked().getUniqueId().toString(), 4)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1500);
									InvMysql.addStick(uuid.toString(), 4);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §cHolzhoe §7für §61500 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§crote Rose") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 2000) {
								if (InvMysql.hasStick(e.getWhoClicked().getUniqueId().toString(), 5)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -2000);
									InvMysql.addStick(uuid.toString(), 5);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §crote Rose §7für §62000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§7Schere") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 2000) {
								if (InvMysql.hasStick(e.getWhoClicked().getUniqueId().toString(), 6)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -2000);
									InvMysql.addStick(uuid.toString(), 6);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §7Schere §7für §62000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});

				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§bBlau") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 400) {
								if (InvMysql.hasColor(e.getWhoClicked().getUniqueId().toString(), 1)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -400);
									InvMysql.addRüstungsFarbe(uuid.toString(), 1);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §bBlau §7für §6400 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});

				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§0Schwarz") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 800) {
								if (InvMysql.hasColor(e.getWhoClicked().getUniqueId().toString(), 2)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -800);
									InvMysql.addRüstungsFarbe(uuid.toString(), 2);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §0Schwarz §7für §6800 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§fWeiß") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 800) {
								if (InvMysql.hasColor(e.getWhoClicked().getUniqueId().toString(), 3)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -800);
									InvMysql.addRüstungsFarbe(uuid.toString(), 3);
									p.sendMessage(
											String.valueOf(Main.prefix) + "§7Du hast dir §fWeiß §7für §6800 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
					
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aGrün") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
					
							if (coins >= 1200) {
								if (InvMysql.hasColor(e.getWhoClicked().getUniqueId().toString(), 4)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1200);
									InvMysql.addRüstungsFarbe(uuid.toString(), 4);
									p.sendMessage(
											String.valueOf(Main.prefix) + "§7Du hast dir §aGrün §7für §61200 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
					
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cVillager Tod") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1000) {
								if (InvMysql.hasSound(e.getWhoClicked().getUniqueId().toString(), 1)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1000);
									InvMysql.addSounds(uuid.toString(), 1);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §cVillager Tod §7für §61000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§7Sound, wenn ein Anvil kaputt geht") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1000) {
								if (InvMysql.hasSound(e.getWhoClicked().getUniqueId().toString(), 2)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1000);
									InvMysql.addSounds(uuid.toString(), 2);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §7Sound, wenn ein Anvil kaputt geht §7für §61000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
					
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aSound wenn das Item kaputt geht") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 1000) {
								if (InvMysql.hasSound(e.getWhoClicked().getUniqueId().toString(), 3)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1000);
									InvMysql.addSounds(uuid.toString(), 3);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §aSound wenn das Item kaputt geht §7für §61000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§1Donner") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins>= 1000) {
								if (InvMysql.hasSound(e.getWhoClicked().getUniqueId().toString(), 4)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -1000);
									InvMysql.addSounds(uuid.toString(), 4);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §1Donner §7für §61000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cHolzschwert") {
					CoinAPI.getCoins(e.getWhoClicked().getUniqueId().toString(), new Callback<HashMap>() {
						@Override
						public void onSuccess(HashMap data) {
							int coins = (int) data.get("Value");
							if (coins >= 3000) {
								if (InvMysql.hasSword(e.getWhoClicked().getUniqueId().toString(), 1)) {
									p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast das Item schon!");
									e.setCancelled(true);
								} else {
									CoinAPI.addCoins(p.getUniqueId().toString(), -3000);
									InvMysql.addSwords(uuid.toString(), 1);
									p.sendMessage(String.valueOf(Main.prefix)
											+ "§7Du hast dir §cHolzschwert §7für §63000 Coins §7gekauft!");
									e.setCancelled(true);
								}
							} else {
								e.setCancelled(true);
								e.getWhoClicked().closeInventory();
								p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast nicht genug Coins!");
							}
						}
					});
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cZurück") {
					ShopInv.loadInv(p);
					e.setCancelled(true);
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cSchuhe") {
					ShopInv.loadInvBoots(p);
					e.setCancelled(true);
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§eBuildFFA") {
					ShopInv.loadInvBFFA(p);
					e.setCancelled(true);
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§6Schwerter") {
					ShopInv.loadInvBFFASword(p);
					e.setCancelled(true);
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cSticks") {
					ShopInv.loadInvBFFASticks(p);
					e.setCancelled(true);
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§fKillsound") {
					ShopInv.loadInvBFFASound(p);
					e.setCancelled(true);
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aRüstungsfarbe") {
					ShopInv.loadInvBFFARuestungColor(p);
					e.setCancelled(true);
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§eBlöcke") {
					ShopInv.loadInvBFFABlocks(p);
					e.setCancelled(true);
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aPets") {
					ShopInv.loadInvEGG(p);
					e.setCancelled(true);
				}
			}
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
}
