package net.bote.training.backend.enums;

import org.bukkit.Material;

/**
 * @author Elias Arndt | bote100
 * Created on 29.01.2020
 */

public enum SpawnerType {

    GOLD(Material.GOLD_INGOT, "§6Gold"),
    @SuppressWarnings("deprecation") BRONZE(Material.getMaterial(336), "§cBronze"),
    SILVER(Material.IRON_INGOT, "§7Eisen");

    SpawnerType(Material goldIngot, String string) {
		this.material = goldIngot;
		this.name = string;
	}
	public Material getMaterial() {
		return material;
	}
	public String getName() {
		return name;
	}
	private final Material material;
	private final String name;

}
