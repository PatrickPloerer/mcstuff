package de.padel.coinsys.utils;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityWitch;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class WitchN extends EntityWitch {
	public WitchN(Location loc) {
		super((World) ((CraftWorld) loc.getWorld()).getHandle());
		List<?> goalB = (List<?>)Utils.getPrivateField("b", PathfinderGoalSelector.class, this.goalSelector);
	    goalB.clear();
	    List<?> goalC = (List<?>)Utils.getPrivateField("c", PathfinderGoalSelector.class, this.goalSelector);
	    goalC.clear();
	    List<?> targetB = (List<?>)Utils.getPrivateField("b", PathfinderGoalSelector.class, this.targetSelector);
	    targetB.clear();
	    List<?> targetC = (List<?>)Utils.getPrivateField("c", PathfinderGoalSelector.class, this.targetSelector);
	    targetC.clear();
	    this.persistent = true;
		this.setCustomNameVisible(true);
		this.setCustomName("§cShop");
		this.setHealth(20);
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
	}
	@Override
	public void move(double d0, double d1, double d2) {
	}

	@Override
	public void collide(Entity entity) {
	}
	@Override
	public void makeSound(String s, float f, float f1) {
	}

	@Override
	public boolean damageEntity(DamageSource damagesource, float f) {
		return false;
	}
	@Override
	public void g(double d0, double d1, double d2) {
	}
}
