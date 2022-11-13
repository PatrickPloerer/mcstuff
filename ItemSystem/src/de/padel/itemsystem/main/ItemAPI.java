package de.padel.itemsystem.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

public class ItemAPI {
	
	public static interface Callback<T> {
		void onSuccess(T data);
	}
	
	public static HashMap<String, Boolean> exists = new HashMap<>();

	public static boolean playerExists(String uuid) {
		uuid = uuid.replace("-", "");
		if (!exists.containsKey(uuid)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Items WHERE UUID= '" + uuid + "'");
				if (rs.next()) {
					exists.put(uuid, rs.getString("UUID") != null);
					return (rs.getString("UUID") != null);
				}
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			if (exists.get(uuid)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static void createPlayer(String uuid) {
		uuid = uuid.replace("-", "");
		if (!playerExists(uuid))
			Main.mysql.update("INSERT INTO Items (UUID, ITEMS, NAMES, HIDE) VALUES ('" + uuid + "', '', '', 0);");
	}

	@SuppressWarnings("rawtypes")
	public static void getItems(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, String> hash = new HashMap<>();
				hash.put("Value", "");
				if (playerExists(uuid.replace("-", ""))) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM Items WHERE UUID= '" + uuid.replace("-", "") + "'");
						if (rs.next()) {
						 hash.replace("Value", rs.getString("ITEMS"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
						
						@Override
						public void run() {
							callback.onSuccess(hash);
						}
					});
				} else {
					createPlayer(uuid.replace("-", ""));
					getItems(uuid, callback);
				}
			}
		}); 
	}

	public static void setItems(String uuid, String items) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (playerExists(uuid.replace("-", ""))) {
					Main.mysql.update("UPDATE Items SET ITEMS = '" + items + "' WHERE UUID= '" + uuid.replace("-", "") + "';");
				} else {
					createPlayer(uuid);
					setItems(uuid, items);
				}
			}
		});
	}

	public static void addItem(String uuid, String item) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public void run() {
				if (playerExists(uuid.replace("-", ""))) {
					getItems(uuid.replace("-", ""), new Callback<HashMap>() {

						@Override
						public void onSuccess(HashMap data) {
							String newItems = String.valueOf(data.get("Value")) + "," + item;
							Main.mysql.update("UPDATE Items SET ITEMS = '" + newItems + "' WHERE UUID= '" + uuid.replace("-", "") + "';");
						}
						
					});
				} else {
					createPlayer(uuid);
					setItems(uuid, item);
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public static void hasItem(String uuid, String item, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, Boolean> hash = new HashMap<>();
				hash.put("Value", false);
				if (playerExists(uuid.replace("-", ""))) {
					getItems(uuid.replace("-", ""), new Callback<HashMap>() {
						public void onSuccess(HashMap data) {
							String Items = (String)data.get("Value");
							String[] str = Items.split(",");
							List<String> i = new ArrayList<>();
							i = Arrays.asList(str);
							if (i.contains(item)) {
								hash.replace("Value", true);
							}
							Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
								
								@Override
								public void run() {
									callback.onSuccess(hash);
								}
							});
						};
					});
				}else {
					createPlayer(uuid);
					hasItem(uuid, item, callback);
				}
			}
		});
	}

	public static void setName(String uuid, String item, String name) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public void run() {
				if(playerExists(uuid.replace("-", ""))) {
					getNames(uuid.replace("-", ""), new Callback<HashMap>() {
						public void onSuccess(HashMap data) {
							String newNames = (String)data.get("Value") + "," + item + ":" + name;
							Main.mysql.update("UPDATE Items SET NAMES = '" + newNames + "' WHERE UUID= '" + uuid.replace("-", "") + "';");
						};
					});
				}else {
					createPlayer(uuid);
					setName(uuid, item, name);
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public static void getNames(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, String> hash = new HashMap<>();
				hash.put("Value", "");
				if (playerExists(uuid.replace("-", ""))) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM Items WHERE UUID= '" + uuid.replace("-", "") + "'");
						if (rs.next()) {
						 hash.replace("Value", rs.getString("NAMES"));
						}
						Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								callback.onSuccess(hash);
							}
						});
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					createPlayer(uuid.replace("-", ""));
					getItems(uuid, callback);
				}
			}
		}); 
	}

	@SuppressWarnings("rawtypes")
	public static void getName(String uuid, String item, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, String> hash = new HashMap<>();
				hash.put("Value", "");
				if (playerExists(uuid.replace("-", ""))) {
					try {
						String r = "";
						ResultSet rs = Main.mysql.query("SELECT * FROM Items WHERE UUID= '" + uuid.replace("-", "") + "'");
						if (rs.next()) {
							r = rs.getString("NAMES");
						}
						String[] str = r.split(",");
						for (int h = 0; h < str.length; h++) {
							if (str[h].startsWith(item)) {
								String[] cor = str[h].split(":");
								hash.replace("Value", cor[1]);
							}
						}
						Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								callback.onSuccess(hash);
							}
						});
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					createPlayer(uuid);
					getName(uuid, item, callback);
				}
			}
		});
	}

	public static void removeName(String uuid, String item) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (playerExists(uuid.replace("-", ""))) {
					try {
						String r = "";
						ResultSet rs = Main.mysql.query("SELECT * FROM Items WHERE UUID= '" + uuid.replace("-", "") + "'");
						if (rs.next())
							rs.getString("NAMES");
						r = rs.getString("NAMES");
						String[] str = r.split(",");
						List<String> newName = new ArrayList<>();
						for (int h = 0; h < str.length; h++) {
							if (!str[h].startsWith(item))
								newName.add(str[h]);
						}
						StringBuilder sb = new StringBuilder();
						int o = 0;
						while (o < newName.size() - 1) {
							sb.append(newName.get(o));
							sb.append(",");
							o++;
						}
						sb.append(newName.get(o));
						String newNames = sb.toString();
						Main.mysql.update("UPDATE Items SET NAMES = '" + newNames + "' WHERE UUID= '" + uuid.replace("-", "") + "';");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					createPlayer(uuid);
					removeName(uuid, item);
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public static void getHide(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, Boolean> hash = new HashMap<>();
				hash.put("Value", false);
				if (playerExists(uuid.replace("-", ""))) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM Items WHERE UUID= '" + uuid.replace("-", "") + "'");
						if (rs.next()) {
							int s = Integer.valueOf(rs.getInt("HIDE"));
							if(s == 0) {
								hash.replace("Value",false);	
							}else {
								hash.replace("Value", true);
							}
							
						}
						Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								callback.onSuccess(hash);
							}
						});
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					createPlayer(uuid);
					getHide(uuid, callback);
				}
			}
		});
	}

	public static void setHide(String uuid, boolean hide) {
		uuid = uuid.replace("-", "");
		if (hide) {
			Main.mysql.update("UPDATE Items SET HIDE = 1 WHERE UUID= '" + uuid + "';");
		} else {
			Main.mysql.update("UPDATE Items SET HIDE = 0 WHERE UUID= '" + uuid + "';");
		}
	}
}
