package net.bote.training.frontend.commands;

import net.bote.training.backend.sql.MySQLBridge;
import net.bote.training.frontend.service.MessageService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Elias Arndt | bote100
 * Created on 07.10.2019
 */

public class TrainingStatsCommand implements CommandExecutor {

    private final MySQLBridge mySQLBridge;
    private final MessageService messageService;
    
    public TrainingStatsCommand(MySQLBridge mysqlbride, MessageService msService) {
    	this.mySQLBridge = mysqlbride;
    	this.messageService = msService;
    }
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player p = ((Player) commandSender);

        if(args.length == 0) {

            ResultSet resultSet = mySQLBridge.query("SELECT * FROM cwbwtraining WHERE uuid='" + p.getUniqueId().toString() + "'");

            try {
                if(!resultSet.next()) return true;
                p.sendMessage(messageService.getMessage("command.stats.header", p.getName()));
                p.sendMessage(messageService.getMessage("command.stats.entry", "Platzierung", mySQLBridge.getRank(p.getUniqueId().toString())));
                p.sendMessage(messageService.getMessage("command.stats.entry", "Punkte", resultSet.getInt("points")));
                p.sendMessage(messageService.getMessage("command.stats.entry", "Gespielte Spiele", resultSet.getInt("plays")));
                p.sendMessage(messageService.getMessage("command.stats.entry", "Gewonnene Spiele", resultSet.getInt("wins")));
                p.sendMessage(messageService.getMessage("command.stats.entry", "Betten zerstÃ¶rt", resultSet.getInt("beds")));
                p.sendMessage(messageService.getMessage("command.stats.header", p.getName()));
            } catch (SQLException e) {
                e.printStackTrace();
                p.sendMessage("§4An error occured! > " + e.getMessage());
            }

            return true;
        }

        UUID target = getUUIDByName(args[0]);

        if(Objects.isNull(target)) {
            p.sendMessage(messageService.getMessage("command.stats.notInDatabase"));
            return true;
        }

        ResultSet resultSet = mySQLBridge.query("SELECT * FROM cwbwtraining WHERE uuid='" + target.toString() + "'");

        try {
            if(!resultSet.next()) return true;
            p.sendMessage(messageService.getMessage("command.stats.header", args[0]));
            p.sendMessage(messageService.getMessage("command.stats.entry", "Platzierung", mySQLBridge.getRank(target.toString())));
            p.sendMessage(messageService.getMessage("command.stats.entry", "Punkte", resultSet.getInt("points")));
            p.sendMessage(messageService.getMessage("command.stats.entry", "Gespielte Spiele", resultSet.getInt("plays")));
            p.sendMessage(messageService.getMessage("command.stats.entry", "Gewonnene Spiele", resultSet.getInt("wins")));
            p.sendMessage(messageService.getMessage("command.stats.entry", "Betten zerstÃ¶rt", resultSet.getInt("beds")));
            p.sendMessage(messageService.getMessage("command.stats.header", args[0]));
        } catch (SQLException e) {
            e.printStackTrace();
            p.sendMessage("§4An error occured! > " + e.getMessage());
        }

        return true;
    }

    private UUID getUUIDByName(String name) {
        try {
            ResultSet rs = mySQLBridge.query("SELECT uuid FROM namefetcher WHERE name= '" + name + "'");
            if (rs.next()) {
                return UUID.fromString(rs.getString("uuid"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
