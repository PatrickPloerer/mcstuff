package de.padel.coinsys.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;

import de.padel.coinsys.main.CoinAPI;
import de.padel.coinsys.main.CoinAPI.Callback;
import de.padel.coinsys.main.Main;

/*    */
/*    */ public class Utils {
	/*    */ @SuppressWarnings("rawtypes")
			public void getAllowReward(Player player, Callback<HashMap> callback) {
		/* 10 */ long current = System.currentTimeMillis();
				getTime(player, new Callback<HashMap>() {
					@Override
					public void onSuccess(HashMap data) 
					{
						long millis = (long)data.get("Value");
						HashMap<String, Boolean> hash = new HashMap<>();
						boolean allow = (current > millis);
						hash.put("Value", allow);
						Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								callback.onSuccess(hash);
							}
						});
					};	
				});	
		   }

	/*    */
	/*    */  @SuppressWarnings("rawtypes")
	public void getAllowReward1(Player player, Callback<HashMap> callback) {
/* 10 */ long current = System.currentTimeMillis();
		getTime1(player, new Callback<HashMap>() {
			@Override
			public void onSuccess(HashMap data) 
			{
				long millis = (long)data.get("Value");
				HashMap<String, Boolean> hash = new HashMap<>();
				boolean allow = (current > millis);
				hash.put("Value", allow);
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			};	
		});	
   }

	/*    */
	/*    */  @SuppressWarnings("rawtypes")
	public void getAllowReward2(Player player, Callback<HashMap> callback) {
/* 10 */ long current = System.currentTimeMillis();
		getTime2(player, new Callback<HashMap>() {
			@Override
			public void onSuccess(HashMap data) 
			{
				long millis = (long)data.get("Value");
				HashMap<String, Boolean> hash = new HashMap<>();
				boolean allow = (current > millis);
				hash.put("Value", allow);
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			};	
		});	
   }
	@SuppressWarnings("rawtypes")
	public void getAllAllowRewads(Player player, Callback<HashMap> callback) {
		HashMap<String, Boolean> all = new HashMap<>();
		all.put("1", false);
		all.put("2", false);
		all.put("3", false);
		getAllowReward(player, new Callback<HashMap>() {
			 @Override
			public void onSuccess(HashMap data) {
				 all.replace("1", (boolean)data.get("Value"));
				 getAllowReward1(player, new Callback<HashMap>() {
					 @Override
					public void onSuccess(HashMap data) {
						 all.replace("2", (boolean) data.get("Value"));
						 getAllowReward2(player, new Callback<HashMap>() {
							 @Override
							public void onSuccess(HashMap data) {
								 all.replace("3",(boolean)data.get("Value"));
								 callback.onSuccess(all);
							}
						});
					}
				});
			}
		});
		
	}
	
	
	/*    */
	/*    */ public void setReward(Player player) {
		/* 26 */ long toSet = System.currentTimeMillis() + 36000000L;
		/* 27 */ CoinAPI.setDaily(player.getUniqueId().toString(), toSet);
		/*    */ }

	/*    */
	/*    */ public void setReward1(Player player) {
		/* 30 */ long toSet = System.currentTimeMillis() + 36000000L;
		/* 31 */ CoinAPI.setDaily1(player.getUniqueId().toString(), toSet);
		/*    */ }

	/*    */
	/*    */ public void setReward2(Player player) {
		/* 34 */ long toSet = System.currentTimeMillis() + 36000000L;
		/* 35 */ CoinAPI.setDaily2(player.getUniqueId().toString(), toSet);
		/*    */ }

	/*    */
	/*    */ @SuppressWarnings("rawtypes")
	public void getTime(Player player, Callback<HashMap> callback) {
		CoinAPI.getDaily(player.getUniqueId().toString(), new Callback<HashMap>() {
			@Override
			public void onSuccess(HashMap data) {
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(data);
					}
				});
			}
		});
		/*    */ }

	/*    */
	/*    */ @SuppressWarnings("rawtypes")
	public void getTime1(Player player, Callback<HashMap> callback) {
		CoinAPI.getDaily1(player.getUniqueId().toString(), new Callback<HashMap>() {
			@Override
			public void onSuccess(HashMap data) {
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(data);
					}
				});
			}
		});
		/*    */ }

	/*    */
	/*    */ @SuppressWarnings("rawtypes")
	public void getTime2(Player player, Callback<HashMap> callback) {
		CoinAPI.getDaily2(player.getUniqueId().toString(), new Callback<HashMap>() {
			@Override
			public void onSuccess(HashMap data) {
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(data);
					}
				});
			}
		});
		/*    */ }

	/*    */
	/*    */ public String getRemainingTime(long millis) {
		/* 49 */ long seconds = millis / 1000L;
		/* 50 */ long minutes = 0L;
		/* 51 */ while (seconds > 60L) {
			/* 52 */ seconds -= 60L;
			/* 53 */ minutes++;
			/*    */ }
		/* 55 */ long hours = 0L;
		/* 56 */ while (minutes > 60L) {
			/* 57 */ minutes -= 60L;
			/* 58 */ hours++;
			/*    */ }
		/* 60 */ return "§e" + hours + " Stunde(n) " + minutes + " Minute(n) " + seconds + " Sekunde(n)";
		/*    */ }
	public static Object getPrivateField(String fieldName, @SuppressWarnings("rawtypes") Class clazz, Object object) {
	    Object o = null;
	    try {
	      Field field = clazz.getDeclaredField(fieldName);
	      field.setAccessible(true);
	      o = field.get(object);
	    } catch (NoSuchFieldException e) {
	      e.printStackTrace();
	    } catch (IllegalAccessException e) {
	      e.printStackTrace();
	    } 
	    return o;
	  }
	/*    */ }


