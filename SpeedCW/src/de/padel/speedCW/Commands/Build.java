package de.padel.speedCW.Commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.Utils.Utils;

public class Build implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs instanceof Player) {
			Player p = (Player) cs;
			if(p.hasPermission("speed.build")) {
				if(Utils.build.contains(p.getUniqueId())) {
					Utils.build.remove(p.getUniqueId());
					p.setGameMode(GameMode.SURVIVAL);
					p.sendMessage(SpeedCW.prefix + "§7Du bist nicht mehr im §bBuild!");
				}else {
					Utils.build.add(p.getUniqueId());
					p.setGameMode(GameMode.CREATIVE);
					p.sendMessage(SpeedCW.prefix + "§7Du bist nun im §bBuild!");
				}
			}else {
				p.sendMessage("§7Keine Rechte!");
			}
		}
		return true;
	}
	
	

}
