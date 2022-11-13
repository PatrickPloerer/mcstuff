package de.padel.itemsystem.utils;

import de.padel.itemsystem.main.Main;
import de.padel.itemsystem.mobs.RideAbleChicken;
import de.padel.itemsystem.mobs.RideAbleCow;
import de.padel.itemsystem.mobs.RideAbleEntityType;
import de.padel.itemsystem.mobs.RideAbleGuardian;
import de.padel.itemsystem.mobs.RideAblePig;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.entity.Player;

public class Pet extends PathfinderGoal {
  private final EntityInsentient a;
  
  private EntityLiving b;
  
  private final double f;
  
  private final float g;
  
  private double c;
  
  private double d;
  
  private double e;
  
  public Pet(EntityInsentient a, double speed, float distance) {
    this.a = a;
    this.f = speed;
    this.g = distance;
  }
  
  public static Entity getPet(Player p, String petnum) {
    Entity e;
    if (petnum == "8") {
      e = RideAbleEntityType.spawnEntity((Entity)new RideAblePig(p.getLocation(), p), p.getLocation());
    } else if (petnum == "9") {
      e = RideAbleEntityType.spawnEntity((Entity)new RideAblePig(p.getLocation(), p), p.getLocation());
    } else if (petnum == "10") {
      e = RideAbleEntityType.spawnEntity((Entity)new RideAbleCow(p.getLocation(), p), p.getLocation());
    } else if (petnum == "11") {
      e = RideAbleEntityType.spawnEntity((Entity)new RideAbleCow(p.getLocation(), p), p.getLocation());
    } else if (petnum == "12") {
      e = RideAbleEntityType.spawnEntity((Entity)new RideAbleChicken(p.getLocation(), p), p.getLocation());
    } else if (petnum == "13") {
      e = RideAbleEntityType.spawnEntity((Entity)new RideAbleChicken(p.getLocation(), p), p.getLocation());
    } else if (petnum == "14") {
      e = RideAbleEntityType.spawnEntity((Entity)new RideAbleGuardian(p.getLocation(), p), p.getLocation());
    } else {
      e = RideAbleEntityType.spawnEntity((Entity)new RideAbleChicken(p.getLocation(), p), p.getLocation());
      p.sendMessage(String.valueOf(Main.prefix) + "Pet nicht ermitteln.");
    } 
    return e;
  }
  
  public boolean a() {
    this.b = this.a.getGoalTarget();
    if (this.b == null)
      return false; 
    if (this.a.getCustomName() == null)
      return false; 
    if (this.b.h((Entity)this.a) > (this.g * this.g)) {
      this.a.setPosition(this.b.locX, this.b.locY, this.b.locZ);
      return false;
    } 
    this.c = this.b.locX;
    this.d = this.b.locY;
    this.e = this.b.locZ;
    return true;
  }
  
  public void c() {
    this.a.getNavigation().a(this.c, this.d, this.e, this.f);
  }
  
  public boolean b() {
    return (!this.a.getNavigation().m() && this.b.h((Entity)this.a) < (this.g * this.g));
  }
  
  public void d() {
    this.b = null;
  }
}
