package de.padel.coinsys.commands;

import de.padel.coinsys.main.Main;
import de.padel.coinsys.utils.LocationM;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.InventoryEnderChest;
import net.minecraft.server.v1_8_R3.InventorySubcontainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setChest implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player)sender;
      if (p.hasPermission("coins.setLottery")) {
        if (args.length == 0) {
          try {
            InventoryEnderChest enderChest = (InventoryEnderChest)p.getEnderChest();
            Field chestNameField = InventorySubcontainer.class.getDeclaredField("a");
            chestNameField.setAccessible(true);
            chestNameField.set(enderChest, "§fLottery");
          } catch (Exception e) {
            e.printStackTrace();
          } 
          LocationM.setLocation("Lottery", p.getEyeLocation());
          p.sendMessage(String.valueOf(Main.prefix) + "§7Du hast die Lottery erfolgreich gesetzt!");
        } else {
          p.sendMessage(String.valueOf(Main.prefix) + "§7Nutze: §e/setLottery");
        } 
      } else {
        p.sendMessage(String.valueOf(Main.prefix) + "§cKeine Rechte!");
      } 
    } 
    return true;
  }
}

