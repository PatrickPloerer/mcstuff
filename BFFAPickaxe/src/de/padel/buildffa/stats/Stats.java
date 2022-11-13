package de.padel.buildffa.stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.padel.buildffa.main.Main;

public class Stats {
	  public static Double getKD(String uuid) {
	    if (getDeaths(uuid).intValue() == 0)
	      return Double.valueOf(getDeaths(uuid).intValue()); 
	    double kd = getKills(uuid).intValue() / getDeaths(uuid).intValue();
	    double kd1 = (int)(kd * 100.0D);
	    double finalkd = kd1 / 100.0D;
	    return Double.valueOf(finalkd);
	  }
	  
	  public static Integer getKills(String uuid) {
	    Integer i = Integer.valueOf(0);
	    if (MySQL.playerExists(uuid)) {
	      try {
	        ResultSet rs = Main.mysql.query("SELECT * FROM BFFAStats WHERE UUID= '" + uuid + "'");
	        if (rs.next())
	          Integer.valueOf(rs.getInt("KILLS")); 
	        i = Integer.valueOf(rs.getInt("KILLS"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	      MySQL.createPlayer(uuid);
	      getKills(uuid);
	    } 
	    return i;
	  }
	  
	  public static Integer getDeaths(String uuid) {
	    Integer i = Integer.valueOf(0);
	    if (MySQL.playerExists(uuid)) {
	      try {
	        ResultSet rs = Main.mysql.query("SELECT * FROM BFFAStats WHERE UUID= '" + uuid + "'");
	        if (rs.next())
	          Integer.valueOf(rs.getInt("DEATHS")); 
	        i = Integer.valueOf(rs.getInt("DEATHS"));
	      } catch (SQLException e) {
	        e.printStackTrace();
	      } 
	    } else {
	      MySQL.createPlayer(uuid);
	      getKills(uuid);
	    } 
	    return i;
	  }
	  
	  public static void setKills(String uuid, Integer kills) {
	    if (MySQL.playerExists(uuid)) {
	      Main.mysql.update("UPDATE BFFAStats SET KILLS= '" + kills + "' WHERE UUID= '" + uuid + "';");
	    } else {
	      MySQL.createPlayer(uuid);
	      setKills(uuid, kills);
	    } 
	  }
	  
	  public static void setDeaths(String uuid, Integer deaths) {
	    if (MySQL.playerExists(uuid)) {
	      Main.mysql.update("UPDATE BFFAStats SET DEATHS= '" + deaths + "' WHERE UUID= '" + uuid + "';");
	    } else {
	      MySQL.createPlayer(uuid);
	      setDeaths(uuid, deaths);
	    } 
	  }
	  
	  public static void addKill(String uuid) {
	    Integer i = Integer.valueOf(1);
	    if (MySQL.playerExists(uuid)) {
	      setKills(uuid, Integer.valueOf(getKills(uuid).intValue() + i.intValue()));
	    } else {
	      MySQL.createPlayer(uuid);
	      addKill(uuid);
	    } 
	  }
	  
	  public static void addDeath(String uuid) {
	    Integer i = Integer.valueOf(1);
	    if (MySQL.playerExists(uuid)) {
	      setDeaths(uuid, Integer.valueOf(getDeaths(uuid).intValue() + i.intValue()));
	    } else {
	      MySQL.createPlayer(uuid);
	      addDeath(uuid);
	    } 
	  }
	  public static int getRank(String uuid) {
			ResultSet rs1 = Main.mysql.query("SELECT * FROM BFFAStats ORDER BY KILLS DESC");
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

