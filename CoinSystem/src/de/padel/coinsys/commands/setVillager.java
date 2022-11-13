package de.padel.coinsys.commands;

import de.padel.coinsys.main.Main;
import de.padel.coinsys.utils.LocationM;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setVillager implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player)sender;
      if (p.hasPermission("coins.setVillager")) {
        if (args.length == 0) {
          LocationM.setLocation("Villager", p.getLocation());
          p.sendMessage(String.valueOf(Main.prefix) + "§7Du hast den Villager erfolgreich gesetzt!");
        } else {
          p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/setVillager");
        } 
      } else {
        p.sendMessage(String.valueOf(Main.prefix) + "§cKeine Rechte!");
      } 
    } 
    return true;
  }
}
