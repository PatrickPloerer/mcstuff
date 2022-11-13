package de.padel.speedCW.Manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import de.padel.speedCW.SpeedCW;

public class MysqlManager {
	
	public static void addKill(UUID uuid) {
		if(existsPlayer(uuid)) {
			 Integer i = getKills(uuid)+1;
			 SpeedCW.mysql.update("UPDATE Stats SET Kills = '" + i + "' WHERE UUID= '" + uuid + "';");
		}else {
			addPlayer(uuid);
			addKill(uuid);
		}
	}
	public static void addDeath(UUID uuid) {
		if(existsPlayer(uuid)) {
			 Integer i = getDeaths(uuid)+1;
			 SpeedCW.mysql.update("UPDATE Stats SET Deaths = '" + i + "' WHERE UUID= '" + uuid + "';");
		}else {
			addPlayer(uuid);
			addDeath(uuid);
		}
	}
	public static int getKills(UUID uuid) {
		Integer i = Integer.valueOf(0);
	    if (existsPlayer(uuid)) {
	      try {
	        ResultSet rs = SpeedCW.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
	        if (rs.next())
	          Integer.valueOf(rs.getInt("Kills")); 
	        i = Integer.valueOf(rs.getInt("Kills"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	    	addPlayer(uuid);
	    	getKills(uuid);
	    } 
	    return i;
	}
	public static int getDeaths(UUID uuid) {
		Integer i = Integer.valueOf(0);
	    if (existsPlayer(uuid)) {
	      try {
	        ResultSet rs = SpeedCW.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
	        if (rs.next())
	          Integer.valueOf(rs.getInt("Deaths")); 
	        i = Integer.valueOf(rs.getInt("Deaths"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	    	addPlayer(uuid);
	    	getDeaths(uuid);
	    } 
	    return i;
	}
	public static int getElo(UUID uuid) {
		Integer i = Integer.valueOf(0);
	    if (existsPlayer(uuid)) {
	      try {
	        ResultSet rs = SpeedCW.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
	        if (rs.next())
	          Integer.valueOf(rs.getInt("elo")); 
	        i = Integer.valueOf(rs.getInt("elo"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	    	addPlayer(uuid);
	    	getElo(uuid);
	    } 
	    return i;
	}
	public static boolean existsPlayer(UUID uuid) {
		 try {
		      ResultSet rs = SpeedCW.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
		      if (rs.next())
		        return (rs.getString("UUID") != null); 
		      return false;
		    } catch (SQLException e) {
		      e.printStackTrace();
		      return false;
		    } 
	}
	public static void addWin(UUID uuid) {
		if(existsPlayer(uuid)) {
			 Integer i = (getWins(uuid)+1);
			 SpeedCW.mysql.update("UPDATE Stats SET Wins = '" + i + "' WHERE UUID= '" + uuid + "';");
		}else {
			addPlayer(uuid);
			addWin(uuid);
		}
	}
	public static void addElo(UUID uuid, int elo) {
		if(existsPlayer(uuid)) {
			 Integer i = (getElo(uuid)+elo);
			 SpeedCW.mysql.update("UPDATE Stats SET elo = '" + i + "' WHERE UUID= '" + uuid + "';");
		}else {
			addPlayer(uuid);
			addElo(uuid, elo);
		}
	}
	public static void setElo(UUID uuid, int elo) {
		if(existsPlayer(uuid)) {
			 Integer i = elo;
			 SpeedCW.mysql.update("UPDATE Stats SET elo = '" + i + "' WHERE UUID= '" + uuid + "';");
		}else {
			addPlayer(uuid);
			addElo(uuid, elo);
		}
	}
	public static int getWins(UUID uuid) {
		    Integer i = Integer.valueOf(0);
		    if (existsPlayer(uuid)) {
		      try {
		        ResultSet rs = SpeedCW.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
		        if (rs.next())
		          Integer.valueOf(rs.getInt("Wins")); 
		        i = Integer.valueOf(rs.getInt("Wins"));
		      } catch (SQLException e) {
		        e.printStackTrace();
		      } 
		    } else {
		    	addPlayer(uuid);
		    	getWins(uuid);
		    } 
		    return i;
	}
	public static void addPlayer(UUID uuid) {
		if(!existsPlayer(uuid)) {
			SpeedCW.mysql.update("INSERT INTO Stats (UUID, Kills, Deaths, Wins, elo) VALUES ('" + uuid + 
			          "', '0', '0', '0', 1000);");
		}
	}
	public static int getRank(String uuid) {
		ResultSet rs1 = SpeedCW.mysql.query("SELECT * FROM Stats ORDER BY elo DESC");
		int in = 0;
		try {
			while(rs1.next()) {
				in++;
				if(rs1.getString("UUID").equals(uuid)) {
					return in; 
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
