package de.padel.itemsystem.mobs;

import de.padel.itemsystem.utils.Pet;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;

public class RideAblePig extends EntityPig {
  public RideAblePig(Location loc, Player p) {
    super((World)((CraftWorld)loc.getWorld()).getHandle());
    setPosition(loc.getX(), loc.getY(), loc.getZ());
    setAge(1);
    setCustomNameVisible(true);
    setCustomName("entity");
    List<?> goalB = (List<?>)getPrivateField("b", PathfinderGoalSelector.class, this.goalSelector);
    goalB.clear();
    List<?> goalC = (List<?>)getPrivateField("c", PathfinderGoalSelector.class, this.goalSelector);
    goalC.clear();
    setGoalTarget((EntityLiving)((CraftPlayer)p).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, 
        true);
    this.goalSelector.a(0, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)this));
    this.goalSelector.a(1, (PathfinderGoal)new Pet((EntityInsentient)this, 1.4D, 15.0F));
    this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, EntityHuman.class, 8.0F));
  }
  
  public RideAblePig(World world) {
    super(world);
  }
  
  public void g(float f, float f1) {
    if (this.passenger != null && this.passenger instanceof EntityHuman) {
      this.lastYaw = this.yaw = this.passenger.yaw;
      this.pitch = this.passenger.pitch * 0.5F;
      setYawPitch(this.yaw, this.pitch);
      this.aI = this.aG = this.yaw;
      f = ((EntityLiving)this.passenger).aZ * 0.5F;
      f1 = ((EntityLiving)this.passenger).ba;
      if (f1 <= 0.0F)
        f1 *= 0.25F; 
      Field jump = null;
      try {
        jump = EntityLiving.class.getDeclaredField("aY");
      } catch (NoSuchFieldException e1) {
        e1.printStackTrace();
      } catch (SecurityException e1) {
        e1.printStackTrace();
      } 
      jump.setAccessible(true);
      if (jump != null && this.onGround)
        try {
          if (jump.getBoolean(this.passenger)) {
            double jumpHeight = 1.0D;
            this.motY = jumpHeight;
          } 
        } catch (IllegalArgumentException|IllegalAccessException e) {
          e.printStackTrace();
        }  
      this.S = 1.0F;
      this.aM = bI() * 0.1F;
      if (!this.world.isClientSide) {
        k(0.35F);
        super.g(f, f1);
      } 
      this.aA = this.aB;
      double d0 = this.locX - this.lastX;
      double d1 = this.locZ - this.lastZ;
      float f4 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
      if (f4 > 1.0F)
        f4 = 1.0F; 
      this.aB += (f4 - this.aB) * 0.4F;
      this.aC += this.aB;
    } else {
      this.S = 0.5F;
      this.aM = 0.02F;
      super.g(f, f1);
    } 
  }
  
  public static Object getPrivateField(String fieldName, Class<?> clazz, Object object) {
    Object o = null;
    try {
      Field field = clazz.getDeclaredField(fieldName);
      field.setAccessible(true);
      o = field.get(object);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } 
    return o;
  }
}
