package de.padel.coinsys.commands;

import de.padel.coinsys.main.Main;
import de.padel.coinsys.utils.LocationM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setShop implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player)sender;
      if (p.hasPermission("coins.setShop")) {
        if (args.length == 0) {
          LocationM.setLocation("Shop", p.getLocation());
          p.sendMessage(String.valueOf(Main.prefix) + "§7Du hast den Shop erfolgreich gesetzt!");
        } else {
          p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/setShop");
        } 
      } else {
        p.sendMessage(String.valueOf(Main.prefix) + "§cKeine Rechte!");
      } 
    } 
    return true;
  }
}
