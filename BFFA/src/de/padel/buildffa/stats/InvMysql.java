package de.padel.buildffa.stats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.padel.buildffa.main.Main;

public class InvMysql {

	private static String dbName = "BFFAItems";

	@SuppressWarnings("unused")
	private String all = "CREATE TABLE IF NOT EXISTS BFFAItems(UUID varchar(64), sticks varchar(64), bloecke varchar(64), schwert varchar(60), killsound varchar(60), ruestungFarbe varchar(64), "
      		+ "aktivStick int(1), aktivSword int(1), aktivBlock int(1), aktivSound int(1), aktivColor int(1));";
	
	public static void createPlayer(String uuid) {
		if(!playerExists(uuid)) {
			Main.mysql.update("INSERT INTO "+dbName+" (UUID, sticks, bloecke, schwert, killsound, ruestungFarbe, aktivStick, aktivSword, aktivBlock, aktivSound, aktivColor) VALUES ('"+uuid+"', '0', '0', '0', '0', '0', 0,0,0,0,0);");
		}
	}
	public static void setAktivStick(String uuid, int number) {
		if (playerExists(uuid)) {
			Main.mysql.update("UPDATE "+dbName+" SET aktivStick = '" +number+ "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setAktivStick(uuid, number);
		}
	}
	public static void setAktivColor(String uuid, int number) {
		if (playerExists(uuid)) {
			Main.mysql.update("UPDATE "+dbName+" SET aktivColor = '" +number+ "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setAktivColor(uuid, number);
		}
	}
	public static void setAktivSword(String uuid, int number) {
		if (playerExists(uuid)) {
			Main.mysql.update("UPDATE "+dbName+" SET aktivSword = '" +number+ "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setAktivSword(uuid, number);
		}
	}
	public static void setAktivBlock(String uuid, int number) {
		if (playerExists(uuid)) {
			Main.mysql.update("UPDATE "+dbName+" SET aktivBlock = '" +number+ "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setAktivBlock(uuid, number);
		}
	}
	public static void setAktivSound(String uuid, int number) {
		if (playerExists(uuid)) {
			Main.mysql.update("UPDATE "+dbName+" SET aktivSound = '" +number+ "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setAktivSound(uuid, number);
		}
	}
	public static int getAktivStick(String uuid) {
		int number = 0;
		if (playerExists(uuid)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
				if (rs.next())
					number = rs.getInt("aktivStick");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getAktivStick(uuid);
		}
		return number;
	}
	public static int getAktivColor(String uuid) {
		int number = 0;
		if (playerExists(uuid)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
				if (rs.next())
					number = rs.getInt("aktivColor");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getAktivColor(uuid);
		}
		return number;
	}
	public static int getAktivSword(String uuid) {
		int number = 0;
		if (playerExists(uuid)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
				if (rs.next())
					number = rs.getInt("aktivSword");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getAktivSword(uuid);
		}
		return number;
	}
	public static int getAktivBlock(String uuid) {
		int number = 0;
		if (playerExists(uuid)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
				if (rs.next())
					number = rs.getInt("aktivBlock");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			 getAktivBlock(uuid);
		}
		return number;
	}
	public static int getAktivSound(String uuid) {
		int number = 0;
		if (playerExists(uuid)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM "+dbName+" WHERE UUID = '" + uuid + "'");
				if (rs.next())
					number = rs.getInt("aktivSound");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getAktivSound(uuid);
		}
		return number;
	}
	
	
	public static ArrayList<Integer> getSticks(String uuid) {
		ArrayList<Integer> sticks = new ArrayList<>();
		String s = "";
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
		} else {
			createPlayer(uuid);
			getSticks(uuid);
		}
		return sticks;
	}
	public static boolean hasStick(String uuid, Integer stickNumber) {
		return getSticks(uuid).contains(stickNumber);
	}
	public static boolean hasColor(String uuid, Integer stickNumber) {
		return getRüstungsFarbe(uuid).contains(stickNumber);
	}
	public static boolean hasSound(String uuid, Integer stickNumber) {
		return getSounds(uuid).contains(stickNumber);
	}
	public static boolean hasSword(String uuid, Integer stickNumber) {
		return getSwords(uuid).contains(stickNumber);
	}
	public static boolean hasBlock(String uuid, Integer stickNumber) {
		return getBlocks(uuid).contains(stickNumber);
	}
	public static void addStick(String uuid, Integer stickNumber) {
		if (playerExists(uuid)) {
			ArrayList<Integer> sticks = getSticks(uuid);
			String all = "";
			for(int i = 0; i<sticks.size(); i++) {
				all = all + sticks.get(i) + ",";
			}
			all = all + stickNumber.toString() + ",";
			Main.mysql.update("UPDATE "+dbName+" SET sticks = '" +all+"' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			addStick(uuid, stickNumber);
		}
	}
	public static void removeStick(String uuid, Integer stickNumber) {
		if (playerExists(uuid)) {
			if(getSticks(uuid).contains(stickNumber)) {
				ArrayList<Integer> sticks = getSticks(uuid);
				String sticc = "";
				for(int i : sticks) {
					if(i != stickNumber) {
						sticc = sticc+i+",";
					}
				}
				Main.mysql.update("UPDATE "+dbName+" SET sticks = '" + sticc + "' WHERE UUID= '" + uuid + "';");
			}
		} else {
			createPlayer(uuid);
			removeStick(uuid, stickNumber);
		}
	}
	public static ArrayList<Integer> getSwords(String uuid) {
		ArrayList<Integer> sticks = new ArrayList<>();
		String s = "";
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
		} else {
			createPlayer(uuid);
			getSwords(uuid);
		}
		return sticks;
	}

	public static void addSwords(String uuid, Integer stickNumber) {
		if (playerExists(uuid)) {
			ArrayList<Integer> sticks = getSwords(uuid);
			String all = "";
			for(int i = 0; i<sticks.size(); i++) {
				all = all + sticks.get(i) + ",";
			}
			all = all + stickNumber.toString() + ",";
			Main.mysql.update("UPDATE "+dbName+" SET schwert = '" + all+ "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			addSwords(uuid, stickNumber);
		}
	}
	public static void removeSwords(String uuid, Integer stickNumber) {
		if (playerExists(uuid)) {
			if(getSwords(uuid).contains(stickNumber)) {
				ArrayList<Integer> sticks = getSwords(uuid);
				String sticc = "";
				for(int i : sticks) {
					if(i != stickNumber) {
						sticc = sticc+i+",";
					}
				}
				Main.mysql.update("UPDATE "+dbName+" SET schwert = '" + sticc + "' WHERE UUID= '" + uuid + "';");
			}
		} else {
			createPlayer(uuid);
			removeSwords(uuid, stickNumber);
		}
	}
	public static ArrayList<Integer> getSounds(String uuid) {
		ArrayList<Integer> sticks = new ArrayList<>();
		String s = "";
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
		} else {
			createPlayer(uuid);
			getSounds(uuid);
		}
		return sticks;
	}

	public static void addSounds(String uuid, Integer stickNumber) {
		if (playerExists(uuid)) {
			ArrayList<Integer> sticks = getSounds(uuid);
			String all = "";
			for(int i = 0; i<sticks.size(); i++) {
				all = all + sticks.get(i) + ",";
			}
			all = all + stickNumber.toString() + ",";
			Main.mysql.update("UPDATE "+dbName+" SET killsound = '" + all+ "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			addSounds(uuid, stickNumber);
		}
	}
	public static void removeSounds(String uuid, Integer stickNumber) {
		if (playerExists(uuid)) {
			if(getSounds(uuid).contains(stickNumber)) {
				ArrayList<Integer> sticks = getSounds(uuid);
				String sticc = "";
				for(int i : sticks) {
					if(i != stickNumber) {
						sticc = sticc+i+",";
					}
				}
				Main.mysql.update("UPDATE "+dbName+" SET killsound = '" + sticc + "' WHERE UUID= '" + uuid + "';");
			}
		} else {
			createPlayer(uuid);
			removeSounds(uuid, stickNumber);
		}
	}
	public static ArrayList<Integer> getRüstungsFarbe(String uuid) {
		ArrayList<Integer> sticks = new ArrayList<>();
		String s = "";
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
		} else {
			createPlayer(uuid);
			getRüstungsFarbe(uuid);
		}
		return sticks;
	}

	public static void addRüstungsFarbe(String uuid, Integer stickNumber) {
		if (playerExists(uuid)) {
			ArrayList<Integer> sticks = getRüstungsFarbe(uuid);
			String all = "";
			for(int i = 0; i<sticks.size(); i++) {
				all = all + sticks.get(i) + ",";
			}
			all = all + stickNumber.toString() + ",";
			Main.mysql.update("UPDATE "+dbName+" SET ruestungFarbe = '" + all+ "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			addRüstungsFarbe(uuid, stickNumber);
		}
	}
	public static void removeRüstungsFarbe(String uuid, Integer stickNumber) {
		if (playerExists(uuid)) {
			if(getRüstungsFarbe(uuid).contains(stickNumber)) {
				ArrayList<Integer> sticks = getRüstungsFarbe(uuid);
				String sticc = "";
				for(int i : sticks) {
					if(i != stickNumber) {
						sticc = sticc+i+",";
					}
				}
				Main.mysql.update("UPDATE "+dbName+" SET ruestungFarbe = '" + sticc + "' WHERE UUID= '" + uuid + "';");
			}
		} else {
			createPlayer(uuid);
			removeRüstungsFarbe(uuid, stickNumber);
		}
	}
	public static ArrayList<Integer> getBlocks(String uuid) {
		ArrayList<Integer> sticks = new ArrayList<>();
		String s = "";
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
		} else {
			createPlayer(uuid);
			getBlocks(uuid);
		}
		return sticks;
	}

	public static void addBlock(String uuid, Integer stickNumber) {
		if (playerExists(uuid)) {
			ArrayList<Integer> sticks = getBlocks(uuid);
			String all = "";
			for(int i = 0; i<sticks.size(); i++) {
				all = all + sticks.get(i) + ",";
			}
			all = all + stickNumber.toString() + ",";
			Main.mysql.update("UPDATE "+dbName+" SET bloecke = '" + all+ "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			addBlock(uuid, stickNumber);
		}
	}
	public static void removeBlock(String uuid, Integer stickNumber) {
		if (playerExists(uuid)) {
			if(getBlocks(uuid).contains(stickNumber)) {
				ArrayList<Integer> sticks = getBlocks(uuid);
				String sticc = "";
				for(int i : sticks) {
					if(i != stickNumber) {
						sticc = sticc+i+",";
					}
				}
				Main.mysql.update("UPDATE "+dbName+" SET bloecke = '" + sticc + "' WHERE UUID= '" + uuid + "';");
			}
		} else {
			createPlayer(uuid);
			removeBlock(uuid, stickNumber);
		}
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

}
