package net.bote.training.frontend.commands;

import net.bote.training.Training;
import net.bote.training.backend.enums.GameRole;
import net.bote.training.backend.translation.TranslateableInventory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

/**
 * @author Elias Arndt | bote100
 * Created on 24.01.2020
 */

public class ConfigureMapCommand implements CommandExecutor {

    public static boolean enabled = false;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player p = ((Player) commandSender);

        if(!p.hasPermission("training.admin")) {
            p.sendMessage(Training.getInstance().getMessageService().getMessage("general.noRights"));
            return true;
        }

        if(p.getWorld().getName().equals("Lobby") || p.getWorld().getName().equals("Head")) {
            p.sendMessage("ß4ßlDie Welt darf nicht 'Lobby' oder 'Head' hei√üen!");
            return true;
        }

        if(Bukkit.getScheduler().isQueued(Training.getInstance().getController().getLobbyTask())) {
            Bukkit.getScheduler().cancelTask(Training.getInstance().getController().getLobbyTask());
            p.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ßaDer Countdown wurde abgebrochen. Bitte starte nach dem Aufsetzen den Server neu!");
        }

        enabled = true;

        switch (args.length) {

            case 1:

                switch (args[0]) {

                    case "attack": case "defense": case "spec": case "villager":
                        saveLocation(args[0], p.getLocation());
                        p.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ß7Variable ße" + args[0] + " ß7f√ºr die Welt ße"+p.getWorld().getName()+" ß7gesetzt!");
                        break;
                    case "defenseInv":
                        saveInventory(p, GameRole.DEFENDER);
                        p.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ß7Variable ße" + args[0] + " ß7f√ºr die Welt ße"+p.getWorld().getName()+" ß7gesetzt!");
                        p.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ßaDein derzeitiges Inventar wurde gespeichert.");
                        break;
                    case "attackInv":
                        saveInventory(p, GameRole.ATTACKER);
                        p.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ß7Variable ße" + args[0] + " ß7f√ºr die Welt ße"+p.getWorld().getName()+" ß7gesetzt!");
                        p.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ßaDeie derzeitiges Inventar wurde gespeichert.");
                        break;
                    case "silver": case "gold": case "bronze":
                        saveLocation(args[0], p.getLocation().subtract(0D, 1D, 0D));
                        p.sendMessage(Training.getInstance().getMessageService().getPrefix() + " ß7Variable ße" + args[0] + " ß7f√ºr die Welt ße"+p.getWorld().getName()+" ß7gesetzt!");
                        break;
                    default:
                        sendHelp(p);
                        return true;
                }
                break;

            case 0: default:
                sendHelp(p);
                return true;

        }

        return true;
    }

    private void sendHelp(Player p) {
        p.sendMessage("ß7- ße/configuremap spec ß8| ß7Setze den Spectator-Spawn");
        p.sendMessage("ß7- ße/configuremap defense ß8| ß7Setze den Spawn der Verteidiger");
        p.sendMessage("ß7- ße/configuremap attack ß8| ß7Setze den Spawn der Angreifer");
        p.sendMessage("ß7- ße/configuremap defenseInv ß8| ß7Speicher das Inventar der Verteidiger");
        p.sendMessage("ß7- ße/configuremap attackInv ß8| ß7Speicher das Inventar der Angreifer");
        p.sendMessage("ß7- ße/configuremap <bronze | silver | gold> ß8| ß7Setze eine Spawnerlocation");
        p.sendMessage("ß7- ße/configuremap villager ß8| ß7Setze den Villager");
        p.sendMessage("ß6ßlPro Welt ist nur eine CWBW-Training Map setzbar!");
    }

    private void saveLocation(String locName, Location loc) {

        String world = loc.getWorld().getName();
        YamlConfiguration cfg = Training.getInstance().getController().getLocationConfiguration();

        cfg.set(world + "." + locName + ".X", loc.getX());
        cfg.set(world + "." + locName + ".Y", loc.getY());
        cfg.set(world + "." + locName + ".Z", loc.getZ());
        cfg.set(world + "." + locName + ".Yaw", loc.getYaw());
        cfg.set(world + "." + locName + ".Pitch", loc.getPitch());
        cfg.set(world + "." + locName + ".World", world);

        try {
            cfg.save(Training.getInstance().getController().getLocationFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveInventory(Player player, GameRole role) {
        Inventory tempInv = Bukkit.createInventory(null, 9, role.toString()  + "-INV");
        for(int i = 0; i < 9; i++)
            tempInv.setItem(i, player.getInventory().getItem(i));
        Training.getInstance().getChestTranslator().save(new TranslateableInventory(tempInv, tempInv.getName()), player.getWorld().getName(), role);
    }

}
