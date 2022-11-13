package net.bote.training.frontend.commands;

import net.bote.training.Training;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * @author Elias Arndt | bote100
 * Created on 02.02.2020
 */

public class AddHeadCommand implements CommandExecutor {

    private File file = new File("plugins/CWBWTraining/locations.yml");
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = ((Player) commandSender);

        if(!player.hasPermission("training.admin")) {
            player.sendMessage(Training.getInstance().getMessageService().getMessage("general.noRights"));
            return true;
        }

        if(strings.length != 1) {
            player.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ß7Nutze: ße/addhead <Platz>");
            return true;
        }

        try {
            int id = Integer.parseInt(strings[0]);

            if(id < 1 || id > 10) {
                player.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ßcDer Rang muss zwischen 1 und 10 liegen.");
                return true;
            }

            player.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ß7Du hast ße#" + strings[0] + " ß7hinzugef√ºgt!");
            saveHeadLocation(player.getTargetBlock((Set<Material>) null, 5).getLocation(), cfg, "Head." + id);
        } catch (NumberFormatException nfe) {
            player.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ßcDies ist keine Zahl!");
            return true;
        }
        return true;
    }

    private void saveHeadLocation(Location loc, YamlConfiguration c, String key) {
        c.set(key + ".World", loc.getWorld().getName());
        c.set(key + ".X", loc.getX());
        c.set(key + ".Y", loc.getY());
        c.set(key + ".Z", loc.getZ());
        c.set(key + ".Yaw", loc.getYaw());
        c.set(key + ".Pitch", loc.getPitch());
        try {
            c.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
