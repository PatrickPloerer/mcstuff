package de.padel.coinsys.commands;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import de.padel.coinsys.main.CoinAPI;
import de.padel.coinsys.main.CoinAPI.Callback;
import de.padel.coinsys.main.Main;

public class CoinsMain implements CommandExecutor {
  @SuppressWarnings("rawtypes")
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player)sender;
      if (args.length >= 3) {
        if (args[0].equalsIgnoreCase("set")) {
          if (p.hasPermission("coins.set")) {
            if (getUuid(args[1]) != null) {
              p.sendMessage(String.valueOf(Main.prefix) + "§aDu hast die Coins von " + args[1] + " auf §6"+ args[2] + " §agesetzt!" );
              int c = Integer.parseInt(args[2]);
              CoinAPI.setCoins(getUuid(args[1]).toString(), c);
            } else {
              p.sendMessage(String.valueOf(Main.prefix) + "§cDer Spieler war nie auf dem Server!");
            } 
          } else {
            p.sendMessage(String.valueOf(Main.prefix) + "§Du hast keine Rechte!");
          } 
        } else if (args[0].equalsIgnoreCase("add")) {
          if (p.hasPermission("coins.set")) {
            if (getUuid(args[1]) != null) {
              CoinAPI.addCoins(getUuid(args[1]).toString(), Integer.parseInt(args[2]));
              p.sendMessage(String.valueOf(Main.prefix) + "§aDu hast " + args[1] + " §6"+ args[2] + " §aCoins hinzugefügt");
            } else {
              p.sendMessage(String.valueOf(Main.prefix) + "§cDer Spieler war nie auf dem Server!");
            } 
          } else {
            p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast keine Rechte!");
          } 
        } else if (p.hasPermission("coins.set")) {
          p.sendMessage(String.valueOf(Main.prefix) + "§cBitte benutze §e/coins [add|set] [Spieler] [anzahl]");
        } else {
          p.sendMessage(String.valueOf(Main.prefix) + "§cBitte benutze §e/coins");
        } 
      } else if (args.length == 0) {
    	  CoinAPI.getCoins(p.getUniqueId().toString(), new Callback<HashMap>() {
			
			@Override
			public void onSuccess(HashMap data) {
				int coins = (int)data.get("Value");
				p.sendMessage(String.valueOf(Main.prefix) + "§7Deine Coins: §6"+ coins);
			}
		});
      } else if (args.length == 1) {
        if (p.hasPermission("coins.other")) {
          if (getUuid(args[0]) != null) {
        	  CoinAPI.getCoins(getUuid(args[0]).toString(), new Callback<HashMap>() {
        		  @Override
        		public void onSuccess(HashMap data) {
        			  int i = (int)data.get("Value");
        			  p.sendMessage(String.valueOf(Main.prefix) + "§7Die Coins von " + args[0] + ": §6"+ i);
        		  }
        	  });
          } else {
            p.sendMessage(String.valueOf(Main.prefix) + "§cDer Spieler war nie auf dem Server!");
          } 
        } else {
          p.sendMessage(String.valueOf(Main.prefix) + "§cDu hast keine Rechte!");
        } 
      } else if (p.hasPermission("coins.set")) {
        p.sendMessage(String.valueOf(Main.prefix) + "§cBitte benutze §e/coins [add|set] [Spieler] [anzahl] [Spieler]");
      } else {
        p.sendMessage(String.valueOf(Main.prefix) + "§cBitte benutze §e/coins");
      } 
    } 
    return true;
  }
  
  public String getUuid(String name) {
    String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
    try {
      String UUIDJson = IOUtils.toString(new URL(url));
      if (UUIDJson.isEmpty())
        return "invalid name"; 
      JSONObject UUIDObject = (JSONObject)JSONValue.parseWithException(UUIDJson);
      return UUIDObject.get("id").toString();
    } catch (IOException|org.json.simple.parser.ParseException e) {
      e.printStackTrace();
      return "error";
    } 
  }
}
