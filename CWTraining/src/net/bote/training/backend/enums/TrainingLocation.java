package net.bote.training.backend.enums;

/**
 * @author Elias Arndt | bote100
 * Created on 25.01.2020
 */

public enum TrainingLocation {

    ATTACKER_SPAWN("attack"),
    DEFENDER_SPAWN("defense"),
    SPEC_SPAWN("spec"),
    GOLD_SPAWNER("gold"),
    SILVER_SPAWNER("silver"),
    BRONZE_SPAWNER("bronze"),
    VILLAGER("villager");

    TrainingLocation(String string) {
		setLocationName(string);
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	private String locationName;

}
