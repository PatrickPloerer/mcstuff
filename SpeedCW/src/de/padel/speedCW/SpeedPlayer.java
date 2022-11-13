package de.padel.speedCW;

import org.bukkit.entity.Player;

public class SpeedPlayer {
	
	public SpeedTeam team;
	public int points;
	public String name;
	public Player player;
	public GameRole gameRole;
	
	public SpeedPlayer(SpeedTeam team, int points, Player p, GameRole role) {
		this.team = team;
		this.points = points;
		name = p.getName();
		player = p;
		gameRole = role;
	}
	
	public void setTeam(SpeedTeam team) {
		this.team = team;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public void addPoint() {
		this.points = (points+1);
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPlayer(Player p) {
		player = p;
	}
	public int getPoints() {
		return points;
	}
	public SpeedTeam getTeam() {
		return team;
	}
	public Player getPlayer() {
		return player;
	}
	public String getName() {
		return name;
	}
}
