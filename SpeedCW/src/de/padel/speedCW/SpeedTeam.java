package de.padel.speedCW;

import org.bukkit.Color;
import org.bukkit.Location;

public class SpeedTeam {
	
	public String name;
	public Color color;
	public boolean respawnable;
	public Location spawn;
	public Location spawner1;
	public Location spawner2;
	public Location spawner3;
	
	public SpeedTeam(String name, Color color, boolean respawn, Location spawn, Location bronze, Location silver, Location gold) {
		this.name = name;
		this.color = color;
		this.respawnable = respawn;
		this.spawn = spawn;
		this.spawner1 = bronze;
		this.spawner2 = silver;
		this.spawner3 = gold;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isRespawnable() {
		return respawnable;
	}

	public void setRespawnable(boolean respawnable) {
		this.respawnable = respawnable;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getSpawner1() {
		return spawner1;
	}

	public void setSpawner1(Location bronze) {
		this.spawner1 = bronze;
	}

	public Location getSpawner2() {
		return spawner2;
	}

	public void setSpawner2(Location silver) {
		this.spawner2 = silver;
	}

	public Location getSpawner3() {
		return spawner3;
	}

	public void setSpawner3(Location gold) {
		this.spawner3 = gold;
	}
	

}
