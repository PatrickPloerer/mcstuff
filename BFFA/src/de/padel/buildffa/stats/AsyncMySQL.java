package de.padel.buildffa.stats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;

import de.padel.buildffa.main.Main;

public class AsyncMySQL {
	
	private static String dbName = "BFFAItems";
	
	public static void createPlayer(String uuid, final Callback<Boolean> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				boolean ret;
				if(!playerExists(uuid)) {
				Main.mysql.update("INSERT INTO "+dbName+" (UUID, sticks, bloecke, schwert, killsound, ruestungFarbe, aktivStick, aktivSword, aktivBlock, aktivSound, aktivColor) VALUES ('"+uuid+"', '0', '0', '0', '0', '0', 0,0,0,0,0);");
					ret = false;
				}else {
					ret = true;
				}
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(ret);
					}
				});
			}
		});
	}
	public static void setAktivStick(String uuid, int number) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (playerExists(uuid)) {
					Main.mysql.update("UPDATE "+dbName+" SET aktivStick = '" +number+ "' WHERE UUID= '" + uuid + "';");
				} else {
					createPlayer(uuid, new Callback<Boolean>() {

						@Override
						public void onSuccess(Boolean done) {
							if(done) {
								setAktivStick(uuid, number);
							}
						}
						@Override
						public void onFailure(Throwable cause) {
						}
					});
				}
			}
		});
	}
	public static void setAktivColor(String uuid, int number) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (playerExists(uuid)) {
					Main.mysql.update("UPDATE "+dbName+" SET aktivColor = '" +number+ "' WHERE UUID= '" + uuid + "';");
				} else {
					createPlayer(uuid, new Callback<Boolean>() {

						@Override
						public void onSuccess(Boolean done) {
							if(done) {
								setAktivColor(uuid, number);
							}
						}
						@Override
						public void onFailure(Throwable cause) {
							
						}
					});
				}
			}
		});
	}
	public static void setAktivSword(String uuid, int number) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (playerExists(uuid)) {
					Main.mysql.update("UPDATE "+dbName+" SET aktivSword = '" +number+ "' WHERE UUID= '" + uuid + "';");
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onSuccess(Boolean done) {
							if(done) {
								setAktivSword(uuid, number);
							}
						}
						@Override
						public void onFailure(Throwable cause) {
							
						}
					});
				}
			}
		});
	}
	public static void setAktivBlock(String uuid, int number) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (playerExists(uuid)) {
					Main.mysql.update("UPDATE "+dbName+" SET aktivBlock = '" +number+ "' WHERE UUID= '" + uuid + "';");
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onFailure(Throwable cause) {
							
						}
						@Override
						public void onSuccess(Boolean done) {
							if(done) {
								setAktivBlock(uuid, number);
							}
						}
					});
				}
			}
		});
	}
	public static void setAktivSound(String uuid, int number) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (playerExists(uuid)) {
					Main.mysql.update("UPDATE "+dbName+" SET aktivSound = '" +number+ "' WHERE UUID= '" + uuid + "';");
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onFailure(Throwable cause) {
							
						}
						@Override
						public void onSuccess(Boolean done) {
							if(done) {
								setAktivSound(uuid, number);
							}
						}
					});
				}
			}
		});
	}
	@SuppressWarnings("rawtypes")
	public static void getAktivStick(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				int number = 0;
				HashMap<String, Integer> hash = new HashMap<>();
				hash.put("Value", number);
				if (playerExists(uuid)) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
						if (rs.next())
							hash.replace("Value", rs.getInt("aktivStick"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onSuccess(Boolean done) {
							try {
								ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
								if (rs.next())
									hash.replace("Value", rs.getInt("aktivStick"));
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						@Override
						public void onFailure(Throwable cause) {
						}
					});
				}
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			}
		});
	}
	@SuppressWarnings("rawtypes")
	public static void getAktivColor(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				int number = 0;
				HashMap<String, Integer> hash = new HashMap<>();
				hash.put("Value", number);
				if (playerExists(uuid)) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
						if (rs.next())
							hash.replace("Value", rs.getInt("aktivColor"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onFailure(Throwable cause) {
						}
						public void onSuccess(Boolean done) {
							if(done) {
								try {
									ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
									if (rs.next())
										hash.replace("Value", rs.getInt("aktivColor"));
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						};
					});
				}
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			}
		});
	}
	@SuppressWarnings("rawtypes")
	public static void getAktivSword(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				int number = 0;
				HashMap<String , Integer> hash = new HashMap<>();
				hash.put("Value", number);
				if (playerExists(uuid)) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
						if (rs.next())
							hash.replace("Value", rs.getInt("aktivSword"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onFailure(Throwable cause) {
						}
						@Override
						public void onSuccess(Boolean done) {
							if(done) {
								try {
									ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
									if (rs.next())
										hash.replace("Value", rs.getInt("aktivSword"));
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
					});
				}
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			}
		});
	}
	@SuppressWarnings("rawtypes")
	public static void getAktivBlock(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				int number = 0;
				HashMap<String, Integer> hash = new HashMap<>();
				hash.put("Value", number);
				if (playerExists(uuid)) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
						if (rs.next())
							hash.replace("Value", rs.getInt("aktivBlock"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onFailure(Throwable cause) {
						}
						@Override
						public void onSuccess(Boolean done) {
							if(done) {
								try {
									ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
									if (rs.next())
										hash.replace("Value", rs.getInt("aktivBlock"));
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
					});
				}
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			}
		});
	}
	@SuppressWarnings("rawtypes")
	public static void getAktivSound(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				int number = 0;
				HashMap<String, Integer> hash = new HashMap<>();
				hash.put("Value", number);
				if (playerExists(uuid)) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
						if (rs.next())
							hash.replace("Value", rs.getInt("aktivSound"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onFailure(Throwable cause) {
						}
						@Override
						public void onSuccess(Boolean done) {
							if(done) {
								try {
									ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
									if (rs.next())
										hash.replace("Value", rs.getInt("aktivSound"));
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
					});
				}
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	public static void getSticks(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, ArrayList<Integer>> hash = new HashMap<>();
				ArrayList<Integer> sticks = new ArrayList<>();
				String s = "";
				hash.put("Value", sticks);
				if (playerExists(uuid)) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
						if (rs.next())
							s = rs.getString("sticks");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String[] ss = s.split(",");
					for(String f : ss) {
						sticks.add(Integer.parseInt(f));
					}
					hash.replace("Value", sticks);
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onSuccess(Boolean done) {
							String s2 = "";
							if(done) {
								try {
									ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
									if (rs.next())
										s2 = rs.getString("sticks");
								} catch (SQLException e) {
									e.printStackTrace();
								}
								String[] ss = s2.split(",");
								for(String f : ss) {
									sticks.add(Integer.parseInt(f));
								}
								hash.replace("Value", sticks);
							}
						}
						@Override
						public void onFailure(Throwable cause) {
							
						}
					});
				}
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			}
		});
	}
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Boolean> hasStick(String uuid, Integer stickNumber) {
		HashMap<String, Boolean> bol = new HashMap<>();
		bol.put("Value", false);
		getSticks(uuid, new Callback<HashMap>() {
			@Override
			public void onFailure(Throwable cause) {
				
			}
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(HashMap done) {
				if(((ArrayList<Integer>) done.get("Value")).contains(stickNumber)) {
					bol.replace("Value", true);
				}
			}
		});
		return bol;
	}
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Boolean> hasColor(String uuid, Integer stickNumber) {
		HashMap<String, Boolean> bol = new HashMap<>();
		bol.put("Value", false);
		getRüstungsFarbe(uuid, new Callback<HashMap>() {
			@Override
			public void onFailure(Throwable cause) {
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(HashMap done) {
				if(((ArrayList<Integer>) done.get("Value")).contains(stickNumber)) {
					bol.replace("Value", true);
				}
			}
			
		});
		return bol;
	}
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Boolean> hasSound(String uuid, Integer stickNumber) {
		HashMap<String, Boolean> bol = new HashMap<>();
		bol.put("Value", false);
		getSounds(uuid, new Callback<HashMap>() {
			@Override
			public void onFailure(Throwable cause) {
			}
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(HashMap done) {
				if(((ArrayList<Integer>) done.get("Value")).contains(stickNumber)) {
					bol.replace("Value", true);
				}
			}
		});
		return bol;
	}
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Boolean> hasSword(String uuid, Integer stickNumber) {
		HashMap<String, Boolean> bol = new HashMap<>();
		bol.put("Value", false);
		getSwords(uuid, new Callback<HashMap>() {
			@Override
			public void onFailure(Throwable cause) {
			}
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(HashMap done) {
				if(((ArrayList<Integer>) done.get("Value")).contains(stickNumber)) {
					bol.replace("Value", true);
				}
			}
		});
		return bol;
	}
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Boolean> hasBlock(String uuid, Integer stickNumber) {
		HashMap<String, Boolean> bol = new HashMap<>();
		bol.put("Value", false);
		getBlocks(uuid, new Callback<HashMap>() {
			@Override
			public void onFailure(Throwable cause) {
			}
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(HashMap done) {
				if(((ArrayList<Integer>) done.get("Value")).contains(stickNumber)) {
					bol.replace("Value", true);
				}
			}
		});
		return bol;
	}
	@SuppressWarnings("rawtypes")
	public static void getSwords(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, ArrayList<Integer>> hash = new HashMap<>();
				ArrayList<Integer> sticks = new ArrayList<>();
				String s = "";
				hash.put("Value", sticks);
				if (playerExists(uuid)) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
						if (rs.next())
							s = rs.getString("schwert");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String[] ss = s.split(",");
					for(String f : ss) {
						sticks.add(Integer.parseInt(f));
					}
					hash.replace("Value", sticks);
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onFailure(Throwable cause) {
						}
						@Override
						public void onSuccess(Boolean done) {
							if(done) {
								String s1 = "";
								try {
									ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
									if (rs.next())
										s1 = rs.getString("schwert");
								} catch (SQLException e) {
									e.printStackTrace();
								}
								String[] ss = s1.split(",");
								for(String f : ss) {
									sticks.add(Integer.parseInt(f));
								}
								hash.replace("Value", sticks);
							}
						}
					});
				}
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			}
		});
	}
	@SuppressWarnings("rawtypes")
	public static void getSounds(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
						ArrayList<Integer> sticks = new ArrayList<>();
						String s = "";
						HashMap<String, ArrayList<Integer>> hash = new HashMap<>();
						hash.put("Value", sticks);
						if (playerExists(uuid)) {
							try {
								ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
								if (rs.next())
									s = rs.getString("killsound");
							} catch (SQLException e) {
								e.printStackTrace();
							}
							String[] ss = s.split(",");
							for(String f : ss) {
								sticks.add(Integer.parseInt(f));
							}
							hash.replace("Value", sticks);
						} else {
							createPlayer(uuid, new Callback<Boolean>() {
								@Override
								public void onFailure(Throwable cause) {
								}
								@Override
								public void onSuccess(Boolean done) {
									if(done) {
										String s1 = "";
										try {
											ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
											if (rs.next())
												s1 = rs.getString("killsound");
										} catch (SQLException e) {
											e.printStackTrace();
										}
										String[] ss = s1.split(",");
										for(String f : ss) {
											sticks.add(Integer.parseInt(f));
										}
										hash.replace("Value", sticks);
									}
								}
							});
						}
					 Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
						callback.onSuccess(hash);
					}
				});
			}
		});
	}
	@SuppressWarnings("rawtypes")
	public static void getRüstungsFarbe(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, ArrayList<Integer>> hash = new HashMap<>();
				ArrayList<Integer> sticks = new ArrayList<>();
				String s = "";
				hash.put("Value", sticks);
				if (playerExists(uuid)) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
						if (rs.next())
							s = rs.getString("ruestungFarbe");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String[] ss = s.split(",");
					for(String f : ss) {
						sticks.add(Integer.parseInt(f));
					}
					hash.replace("Value", sticks);
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onFailure(Throwable cause) {
						}
						@Override
						public void onSuccess(Boolean done) {
							if(done) {
								String s1 = "";
								try {
									ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
									if (rs.next())
										s1 = rs.getString("ruestungFarbe");
								} catch (SQLException e) {
									e.printStackTrace();
								}
								String[] ss = s1.split(",");
								for(String f : ss) {
									sticks.add(Integer.parseInt(f));
								}
								hash.replace("Value", sticks);
							}
						}
					});
				}
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			}
		});
	}
	@SuppressWarnings("rawtypes")
	public static void getBlocks(String uuid, Callback<HashMap> callback) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, ArrayList<Integer>> hash = new HashMap<>();
				ArrayList<Integer> sticks = new ArrayList<>();
				String s = "";
				hash.put("Value", sticks);
				if (playerExists(uuid)) {
					try {
						ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
						if (rs.next())
							s = rs.getString("bloecke");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String[] ss = s.split(",");
					for(String f : ss) {
						sticks.add(Integer.parseInt(f));
					}
					hash.replace("Value", sticks);
				} else {
					createPlayer(uuid, new Callback<Boolean>() {
						@Override
						public void onFailure(Throwable cause) {
						}
						@Override
						public void onSuccess(Boolean done) {
							String s1 = "";
							try {
								ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
								if (rs.next())
									s1 = rs.getString("bloecke");
							} catch (SQLException e) {
								e.printStackTrace();
							}
							String[] ss = s1.split(",");
							for(String f : ss) {
								sticks.add(Integer.parseInt(f));
							}
							hash.replace("Value", sticks);
						}
					});
				}
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						callback.onSuccess(hash);
					}
				});
			}
		});
	}
	public static boolean playerExists(String uuid) {
		boolean exists = false;
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '"+uuid+"';");
			if(rs.next()) {
				exists = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return exists;
	}
	public interface Callback<T>{
		void onSuccess(T done);
		void onFailure(Throwable cause);
	}

}
