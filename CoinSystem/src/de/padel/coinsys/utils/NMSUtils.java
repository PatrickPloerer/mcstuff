package de.padel.coinsys.utils;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.Entity;

public enum NMSUtils {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	VillagerN("Villager", 120, (Class) VillagerN.class),
	@SuppressWarnings({ "unchecked", "rawtypes" })
	WitchN("Witch", 66, (Class) WitchN.class);

	NMSUtils(String name, int id, Class<? extends Entity> custom) {
		addToMaps(custom, name, id);
	}

	public static void spawnEntity(Entity entity, Location loc1) {
		entity.setLocation(loc1.getX(), loc1.getY(), loc1.getZ(), loc1.getYaw(), loc1.getPitch());
		((CraftWorld) loc1.getWorld()).getHandle().addEntity(entity);
	}

	@SuppressWarnings("unchecked")
	private static void addToMaps(Class<?> clazz, String name, int id) {
		((Map<String, Class<?>>) Utils.getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null))
				.put(name, clazz);
		((Map<Class<?>, String>) Utils.getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null))
				.put(clazz, name);
		((Map<Integer, Class<?>>) Utils.getPrivateField("e", net.minecraft.server.v1_8_R3.EntityTypes.class, null))
				.put(Integer.valueOf(id), clazz);
		((Map<Class<?>, Integer>) Utils.getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null))
				.put(clazz, Integer.valueOf(id));
		((Map<String, Integer>) Utils.getPrivateField("g", net.minecraft.server.v1_8_R3.EntityTypes.class, null))
				.put(name, Integer.valueOf(id));
	}

}