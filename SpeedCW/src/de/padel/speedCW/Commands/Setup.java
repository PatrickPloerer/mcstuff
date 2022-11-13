package de.padel.speedCW.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.padel.speedCW.SpeedCW;
import de.padel.speedCW.Manager.MapManager;

public class Setup implements CommandExecutor{
	
	/*
	 * Locations:
	 * - Player 1/2 spawn
	 * - Player 1/2 bronze
	 * - Player 1/2 gold
	 * - Player 1/2 silver
	 * - Player 1/2 bed
	 * - Lobbyspawn
	 * - Villagerspawns / NPCS
	 * - SelectTeam1
	 * - SelectTeam2
	 */
	int villagers = 0;

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs instanceof Player) {
			Player p = (Player)cs;
			if(p.hasPermission("speed.setup")) {
				 if(args.length >= 2) {
					 if(args[0].contains("location")) {
						 if(args[1].contains("team1Spawn")) {
							 MapManager.addLocation(p.getLocation(), "Team1Spawn");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team1Bronze")) {
							 MapManager.addLocation(p.getLocation(), "Team1Bronze");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team1Silver")) {
							 MapManager.addLocation(p.getLocation(), "Team1Silver");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team1Gold")) {
							 MapManager.addLocation(p.getLocation(), "Team1Gold");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team1Bed")) {
							 MapManager.addLocation(p.getLocation(), "Team1Bed");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team2Spawn")) {
							 MapManager.addLocation(p.getLocation(), "Team2Spawn");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team2Bronze")) {
							 MapManager.addLocation(p.getLocation(), "Team2Bronze");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team2Silver")) {
							 MapManager.addLocation(p.getLocation(), "Team2Silver");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team2Gold")) {
							 MapManager.addLocation(p.getLocation(), "Team2Gold");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team2Bed")) {
							 MapManager.addLocation(p.getLocation(), "Team2Bed");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("lobbySpawn")) {
							 MapManager.addLocation(p.getLocation(), "LobbySpawn");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("villagerSpawn")) {
							 villagers++;
							 MapManager.addLocation(p.getLocation(), "VillagerSpawn"+villagers);
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team1SelectTeam")) {
							 MapManager.addLocation(p.getLocation(), "SelectTeam1");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("team2SelectTeam")) {
							 MapManager.addLocation(p.getLocation(), "SelectTeam2");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("specSpawn")) {
							 MapManager.addLocation(p.getLocation(), "SpecSpawn");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("Top10")) {
							 MapManager.addLocation(p.getLocation(), "Top10");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else if(args[1].contains("DeathHeight")) {
							 MapManager.addLocation(p.getLocation(), "DeathHeight");
							 p.sendMessage(SpeedCW.prefix+"§7Location gesetzt!");
						 }else {
							 p.sendMessage(SpeedCW.prefix+"§7Locations verfügbar: ");
							 p.sendMessage(SpeedCW.prefix+"§7- team1Spawn");
							 p.sendMessage(SpeedCW.prefix+"§7- team1Bronze");
							 p.sendMessage(SpeedCW.prefix+"§7- team1Silver");
							 p.sendMessage(SpeedCW.prefix+"§7- team1Gold");
							 p.sendMessage(SpeedCW.prefix+"§7- team1Bed");
							 p.sendMessage(SpeedCW.prefix+"§7- team2Spawn");
							 p.sendMessage(SpeedCW.prefix+"§7- team2Bronze");
							 p.sendMessage(SpeedCW.prefix+"§7- team2Silver");
							 p.sendMessage(SpeedCW.prefix+"§7- team2Gold");
							 p.sendMessage(SpeedCW.prefix+"§7- team2Bed");
							 p.sendMessage(SpeedCW.prefix+"§7- lobbySpawn");
							 p.sendMessage(SpeedCW.prefix+"§7- villagerSpawn (4x)");
							 p.sendMessage(SpeedCW.prefix+"§7- team1SelectTeam");
							 p.sendMessage(SpeedCW.prefix+"§7- team2SelectTeam");
							 p.sendMessage(SpeedCW.prefix+"§7- specSpawn");
							 p.sendMessage(SpeedCW.prefix+"§7- Top10");
							 p.sendMessage(SpeedCW.prefix+"§7- DeathHeight");
						 }
					 }else {
						 p.sendMessage(SpeedCW.prefix+"§7Bitte nutze §b/setup location <Location>");
					 }
				 }else {
					 p.sendMessage(SpeedCW.prefix+"§7Bitte nutze §b/setup location <Location>");
					 p.sendMessage(SpeedCW.prefix+"§7Bitte nutze §b/setup ");
				 }
			}else {
				p.sendMessage(SpeedCW.prefix + "§7Keine Rechte!");
			}
		}else {
			cs.sendMessage(SpeedCW.prefix+ "§7Du musst ein Spieler sein!");
		}
		return true;
	}
	
	

}
