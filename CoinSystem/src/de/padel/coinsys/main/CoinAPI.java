package de.padel.coinsys.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;

public class CoinAPI {

	public static interface Callback<T> {
		void onSuccess(T data);
	}

	public static HashMap<String, Boolean> exists = new HashMap<>();

	public static boolean playerExists(String uuid) {
		uuid = uuid.replace("-", "");
		if (exists.containsKey(uuid)) {
			return exists.get(uuid);
		} else {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Coins WHERE UUID= '" + uuid + "'");
				if (rs.next()) {
					exists.put(uuid, true);
					return true;
				}
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	public static void createPlayer(String uuid) {
		uuid = uuid.replace("-", "");
		if (!playerExists(uuid)) {
			long current = System.currentTimeMillis();
			long current1 = System.currentTimeMillis();
			long current2 = System.currentTimeMillis();
			Main.mysql.update("INSERT INTO Coins (UUID, COINS, DAILY, DAILY1, DAILY2) VALUES ('" + uuid + "', '0', '"
					+ current + "', '" + current1 + "', '" + current2 + "');");
		}
	}

	@SuppressWarnings("rawtypes")
	public static void getCoins(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				HashMap<String, Integer> hash = new HashMap<>();
				hash.put("Value", 0);
				if (playerExists(uuid.replace("-", ""))) {
					try {
						ResultSet rs = Main.mysql
								.query("SELECT * FROM Coins WHERE UUID= '" + uuid.replace("-", "") + "'");
						if (rs.next()) {
							hash.replace("Value", rs.getInt("COINS"));
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
					getCoins(uuid, callback);
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public static void getDaily(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				HashMap<String, Long> hash = new HashMap<>();
				hash.put("Value", System.currentTimeMillis());
				if (playerExists(uuid.replace("-", ""))) {
					try {
						ResultSet rs = Main.mysql
								.query("SELECT * FROM Coins WHERE UUID= '" + uuid.replace("-", "") + "'");
						if (rs.next()) {
							hash.replace("Value", rs.getLong("DAILY"));
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
					getDaily(uuid, callback);
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public static void getDaily1(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				HashMap<String, Long> hash = new HashMap<>();
				hash.put("Value", System.currentTimeMillis());
				if (playerExists(uuid.replace("-", ""))) {
					try {
						ResultSet rs = Main.mysql
								.query("SELECT * FROM Coins WHERE UUID= '" + uuid.replace("-", "") + "'");
						if (rs.next()) {
							hash.replace("Value", rs.getLong("DAILY1"));
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
					getDaily(uuid, callback);
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public static void getDaily2(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				HashMap<String, Long> hash = new HashMap<>();
				hash.put("Value", System.currentTimeMillis());
				if (playerExists(uuid.replace("-", ""))) {
					try {
						ResultSet rs = Main.mysql
								.query("SELECT * FROM Coins WHERE UUID= '" + uuid.replace("-", "") + "'");
						if (rs.next()) {
							hash.replace("Value", rs.getLong("DAILY2"));
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
					getDaily(uuid, callback);
				}
			}
		});
	}

	public static void setCoins(String uuid, int coins) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (playerExists(uuid.replace("-", ""))) {
					Main.mysql.update(
							"UPDATE Coins SET COINS = '" + coins + "' WHERE UUID= '" + uuid.replace("-", "") + "';");
				} else {
					createPlayer(uuid);
					setCoins(uuid, coins);
				}
			}
		});
	}

	public static void setDaily(String uuid, long cur) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (playerExists(uuid.replace("-", ""))) {
					Main.mysql.update(
							"UPDATE Coins SET DAILY= '" + cur + "' WHERE UUID= '" + uuid.replace("-", "") + "';");
				} else {
					createPlayer(uuid);
					setDaily(uuid, cur);
				}
			}
		});
	}

	public static void setDaily1(String uuid, long cur) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (playerExists(uuid.replace("-", ""))) {
					Main.mysql.update(
							"UPDATE Coins SET DAILY1= '" + cur + "' WHERE UUID= '" + uuid.replace("-", "") + "';");
				} else {
					createPlayer(uuid);
					setDaily(uuid, cur);
				}
			}
		});
	}

	public static void setDaily2(String uuid, long cur) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (playerExists(uuid.replace("-", ""))) {
					Main.mysql.update(
							"UPDATE Coins SET DAILY2= '" + cur + "' WHERE UUID= '" + uuid.replace("-", "") + "';");
				} else {
					createPlayer(uuid);
					setDaily(uuid, cur);
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public static void addCoins(String uuid, int coins) {
		Integer i = Integer.valueOf(coins);
		if (playerExists(uuid)) {
			getCoins(uuid, new Callback<HashMap>() {
				@Override
				public void onSuccess(HashMap data) {
					int coins2 = (Integer) data.get("Value");
					setCoins(uuid, coins2 + i);
				}
			});
		} else {
			createPlayer(uuid);
			addCoins(uuid, coins);
		}
	}
}