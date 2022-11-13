package net.bote.training.backend;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

/**
 * Made by GERVobis
 */
public class EntityModifier {

	static Entity entity;
	static CraftEntity craftentity;
	static net.minecraft.server.v1_8_R3.Entity entityS;
	static Plugin plugin;

	public EntityModifier(Entity entity,Plugin plugin) {
		EntityModifier.entity = entity;
		craftentity = ((CraftEntity) entity);
		entityS = craftentity.getHandle();
		EntityModifier.plugin = plugin;
	}

	public Builder modify() {
		return new Builder();
	}

	public static final class Builder {

		public Builder setDisplayName(String display) {
			entity.setCustomName(display);
			entity.setCustomNameVisible(true);
			return this;
		}
		public Builder setDisplayNameVisible(Boolean visible) {
			entity.setCustomNameVisible(visible);
			return this;
		}

		public Builder remove() {
			entity.remove();
			return this;
		}

		public Builder setPassenger(Entity passenger) {
			entity.setPassenger(passenger);
			return this;
		}

		public Builder teleport(Location location) {
			entity.teleport(location);
			return this;
		}

		public Builder die() {
			entityS.die();
			return this;
		}

		public Builder setInvisible(boolean invisible) {
			entityS.setInvisible(invisible);
			return this;
		}

		public Builder setInvulnerable(boolean invulnerable) {
			try {
				Field invulnerableField = net.minecraft.server.v1_8_R3.Entity.class
						.getDeclaredField("invulnerable");
				invulnerableField.setAccessible(true);
				invulnerableField.setBoolean(entityS, invulnerable);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return this;
		}
		
		public Builder setNoAI(boolean noAI) {
			NBTTagCompound tag = new NBTTagCompound();
			entityS.c(tag);
			tag.setBoolean("NoAI", noAI);
			EntityLiving el = (EntityLiving) entityS;
			el.a(tag);
			return this;
		}

		public Builder setCanDespawn(boolean candespawn) {
			candespawn = !candespawn;
			NBTTagCompound tag = new NBTTagCompound();
			entityS.c(tag);
			tag.setBoolean("PersistenceRequired", candespawn);
			EntityLiving el = (EntityLiving) entityS;
			el.a(tag);
			return this;
		}

		public Entity build() {
			return entity;
		}

	}

}
