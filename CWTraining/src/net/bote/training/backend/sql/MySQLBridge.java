package net.bote.training.backend.sql;

import net.bote.training.Training;
import org.bukkit.Bukkit;

import java.sql.*;

/**
 * @author Elias Arndt | bote100
 * Created on 25.09.2019
 */


public class MySQLBridge {

    private final String host;
    private final String user;
    private final String database;

    private final String password;

    private Connection connection;
    
    public MySQLBridge(String host, String user, String database, String password) {
    	this.host = host;
    	this.user = user;
    	this.database = database;
    	this.password = password;
    }

    public void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":3306/" + this.database + "?autoReconnect=true",
                    this.user, this.password);
            System.out.println("[Training] Connected successfully to mysql-server");

            update("CREATE TABLE IF NOT EXISTS cwbwtraining(uuid VARCHAR(36), points INT DEFAULT '0', beds INT DEFAULT '0', plays INT DEFAULT '0', wins INT DEFAULT '0', UNIQUE (`uuid`))");
            update("CREATE TABLE IF NOT EXISTS namefetcher(uuid VARCHAR(36), name VARCHAR(16), saved VARCHAR(60), UNIQUE (`uuid`));");

            stable();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void stable() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Training.getInstance(), () -> {
            if(!isConnected()) return;
            query("SELECT uuid FROM cwbwtraining");
            System.out.println("[CWBWTraining] Reloaded connection to mysql-server!");
        }, 0, 20*60*30);
    }

    public ResultSet query(String qry) {
        ResultSet rs = null;
        try {
            Statement st = this.connection.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            e.printStackTrace();
        }
        return rs;
    }

    public void addToEntry(String entry, int add, String uuid) {
        update("UPDATE cwbwtraining SET " + entry + "=" + entry + "+" + add + " WHERE uuid='" + uuid + "'");
    }

    public void update(String qry) {
        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            connect();
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public int getRank(String uuid) {
        ResultSet set = query("SELECT (SELECT COUNT(*)+1 FROM cwbwtraining WHERE points >x.points) AS rank FROM cwbwtraining x WHERE uuid='" + uuid + "'");
        try {
            if(!set.next()) return -1; else return set.getInt("rank");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
