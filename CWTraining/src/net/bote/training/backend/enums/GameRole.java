package net.bote.training.backend.enums;

/**
 * @author Elias Arndt | bote100
 * Created on 24.01.2020
 */

public enum GameRole {

    ATTACKER(false),
    DEFENDER(true);

    GameRole(boolean b) {
    	respawnable = b;
	}
	public boolean isRespawnable() {
		return respawnable;
    }
	final boolean respawnable;
}
