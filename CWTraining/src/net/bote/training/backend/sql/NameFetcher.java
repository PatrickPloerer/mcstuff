package net.bote.training.backend.sql;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author Elias Arndt | bote100
 * Created on 02.02.2020
 */

public class NameFetcher {

    private final MySQLBridge bridge;
    
    public NameFetcher(MySQLBridge bridge) {
    	this.bridge = bridge;
    }

    public void saveData(Player player) {
       bridge.update("INSERT INTO namefetcher (uuid, name, saved) VALUES ('" + player.getUniqueId().toString() + "', '" + player.getName() + "', '" + System.currentTimeMillis() + "')" +
                " ON DUPLICATE KEY UPDATE name='"+player.getName()+"'");
    }

    public String getName(UUID uuid) {
        try {
            ResultSet rs = bridge.query("SELECT name FROM namefetcher WHERE uuid= '" + uuid + "'");
            if (rs.next()) {
                return rs.getString("name");
            }
            return "???";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "???";
    }

}
