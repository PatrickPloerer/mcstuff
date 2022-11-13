package net.bote.training.frontend.commands;

import net.bote.training.Training;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * @author Elias Arndt | bote100
 * Created on 24.01.2020
 */

public class SetLobbyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player p = ((Player) commandSender);

        if(!p.hasPermission("training.admin")) {
            p.sendMessage(Training.getInstance().getMessageService().getMessage("general.noRights"));
            return true;
        }

        YamlConfiguration configuration = Training.getInstance().getController().getLocationConfiguration();
        Location loc = p.getLocation();

        configuration.set("Lobby.World", loc.getWorld().getName());
        configuration.set("Lobby.X", loc.getX());
        configuration.set("Lobby.Y", loc.getY());
        configuration.set("Lobby.Z", loc.getZ());
        configuration.set("Lobby.Yaw", loc.getYaw());
        configuration.set("Lobby.Pitch", loc.getPitch());

        try {
            configuration.save(Training.getInstance().getController().getLocationFile());
            p.sendMessage("§aLobby wurde gesetzt!");
        } catch (IOException e) {
            p.sendMessage("§4" + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }
}
