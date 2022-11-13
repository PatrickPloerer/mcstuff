package net.bote.training.backend.game;

import net.bote.training.Training;
import net.bote.training.backend.enums.GameRole;
import net.bote.training.backend.enums.SpectatorState;
import org.bukkit.Color;
import org.bukkit.Location;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * @author Elias Arndt | bote100
 * Created on 24.01.2020
 */

public class TrainingTeam {

    private final List<TrainingPlayer> members;
    private final String name;
    private final String colorCode;
    private final int maxSize;
    @Nonnull
    private GameRole role;
    private int points = 0;
	private boolean respawnable;
    @Nonnull
    private final Color color;
    
    public TrainingTeam(List<TrainingPlayer> players, String name, String colorCode, int maxSize, GameRole role, Color color) {
    	this.members = players;
    	this.name = name;
    	this.colorCode = colorCode;
    	this.maxSize = maxSize;
    	this.role = role;
    	this.color = color;
    }

    public void switchRole() {
        if(role == GameRole.DEFENDER) {
        	role = GameRole.ATTACKER;
        }
        else if(role == GameRole.ATTACKER) { 
        	role = GameRole.DEFENDER;
        }
        setRespawnable(role.isRespawnable());
    }

    public void setRespawnable(boolean respawnable2) {
    	respawnable = respawnable2;
		
	}

	public void addPoint() {
        setPoints(getPoints() + 1);
    }

    public boolean isFull() {
        return members.size() >= maxSize;
    }

    public Location getRespawnLocation() {
        if(role.equals(GameRole.DEFENDER)) return Training.getInstance().getController().getCurrentMap().getDefenseSpawn();
        return Training.getInstance().getController().getCurrentMap().getAttackSpawn();
    }

    public int getPlayersAlive() {
        return (int)members.stream().filter(tp -> tp.getSpectatorState().equals(SpectatorState.NONE)).count();
    }

	public String getName() {
		return name;
	}

	public String getColorCode() {
		return colorCode;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public List<TrainingPlayer> getMembers() {
		return members;
	}

	public GameRole getRole() {
		return role;
	}

	public Color getColor() {
		return color;
	}

	public boolean isRespawnable() {
		return respawnable;
	}

	public int getMaxSize() {
		return maxSize;
	}

}
