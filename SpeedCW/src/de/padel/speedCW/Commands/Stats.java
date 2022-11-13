package de.padel.speedCW.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.Manager.MysqlManager;
import de.padel.speedCW.Utils.UUIDFetcher;

public class Stats implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs instanceof Player) {
			Player p = (Player)cs;
			if(args.length > 0) {
				if(p.hasPermission("speed.stats.other")) {
					String name = args[0];
					UUID other = UUIDFetcher.getUUID(name);
					if(Bukkit.getPlayer(other) != null) {
					p.sendMessage(SpeedCW.prefix+"§7--- §bStats §7---");
					p.sendMessage(SpeedCW.prefix+"§7Kills: §b"+MysqlManager.getKills(other));
					p.sendMessage(SpeedCW.prefix+"§7Tode: §b"+MysqlManager.getDeaths(other));
					p.sendMessage(SpeedCW.prefix+"§7Gewonnene Spiele: §b"+MysqlManager.getWins(other));
					p.sendMessage(SpeedCW.prefix+"§7Elo: §b"+MysqlManager.getElo(other));
					p.sendMessage(SpeedCW.prefix+"§7Rang: §b"+MysqlManager.getRank(other.toString()));
					p.sendMessage(SpeedCW.prefix+"§7--- §bStats §7---");
					}else {
						p.sendMessage(SpeedCW.prefix+"§7Der Spieler hat keine stats!");
					}
				}else {
					p.sendMessage(SpeedCW.prefix
							+"§7Keine Rechte!");
				}
			}else {
				UUID other = p.getUniqueId();
				p.sendMessage(SpeedCW.prefix+"§7--- §bStats §7---");
				p.sendMessage(SpeedCW.prefix+"§7Kills: §b"+MysqlManager.getKills(other));
				p.sendMessage(SpeedCW.prefix+"§7Tode: §b"+MysqlManager.getDeaths(other));
				p.sendMessage(SpeedCW.prefix+"§7Gewonnene Spiele: §b"+MysqlManager.getWins(other));
				p.sendMessage(SpeedCW.prefix+"§7Elo: §b"+MysqlManager.getElo(other));
				p.sendMessage(SpeedCW.prefix+"§7Rang: §b"+MysqlManager.getRank(other.toString()));
				p.sendMessage(SpeedCW.prefix+"§7--- §bStats §7---");
			}
		}else {
			cs.sendMessage("§7Du musst ein Spieler sein!");
		}
		return true;
	}
	
	

}
