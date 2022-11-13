package de.padel.speedCW.Utils;

			import java.lang.reflect.Field;
/*     */ import net.minecraft.server.v1_8_R3.Entity;
/*     */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class EntityModifier {
/*     */   static org.bukkit.entity.Entity entity;
/*     */   
/*     */   static CraftEntity craftentity;
/*     */   
/*     */   static Entity entityS;
/*     */   
/*     */   static Plugin plugin;
/*     */   
/*     */   public EntityModifier(org.bukkit.entity.Entity entity, Plugin plugin) {
/*  18 */     EntityModifier.entity = entity;
/*  19 */     craftentity = (CraftEntity)entity;
/*  20 */     entityS = craftentity.getHandle();
/*  21 */     EntityModifier.plugin = plugin;
/*     */   }
/*     */   
/*     */   public Builder modify() {
/*  25 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static final class Builder {
/*     */     public Builder setDisplayName(String display) {
/*  33 */       EntityModifier.entity.setCustomName(display);
/*  34 */       EntityModifier.entity.setCustomNameVisible(false);
/*  35 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setDisplayNameVisible(Boolean visible) {
/*  39 */       EntityModifier.entity.setCustomNameVisible(visible.booleanValue());
/*  40 */       return this;
/*     */     }
/*     */     
/*     */     public Builder remove() {
/*  44 */       EntityModifier.entity.remove();
/*  45 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setPassenger(org.bukkit.entity.Entity passenger) {
/*  49 */       EntityModifier.entity.setPassenger(passenger);
/*  50 */       return this;
/*     */     }
/*     */     
/*     */     public Builder teleport(Location location) {
/*  54 */       EntityModifier.entity.teleport(location);
/*  55 */       return this;
/*     */     }
/*     */     
/*     */     public Builder die() {
/*  59 */       EntityModifier.entityS.die();
/*  60 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setInvisible(boolean invisible) {
/*  64 */       EntityModifier.entityS.setInvisible(invisible);
/*  65 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setInvulnerable(boolean invulnerable) {
/*     */       try {
/*  70 */         Field invulnerableField = Entity.class.getDeclaredField("invulnerable");
/*  71 */         invulnerableField.setAccessible(true);
/*  72 */         invulnerableField.setBoolean(EntityModifier.entityS, invulnerable);
/*  73 */       } catch (Exception var3) {
/*  74 */         var3.printStackTrace();
/*     */       } 
/*  77 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setNoAI(boolean noAI) {
/*  81 */       NBTTagCompound tag = new NBTTagCompound();
/*  82 */       EntityModifier.entityS.c(tag);
/*  83 */       tag.setBoolean("NoAI", noAI);
/*  84 */       EntityLiving el = (EntityLiving)EntityModifier.entityS;
/*  85 */       el.a(tag);
/*  86 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setCanDespawn(boolean candespawn) {
/*  90 */       candespawn = !candespawn;
/*  91 */       NBTTagCompound tag = new NBTTagCompound();
/*  92 */       EntityModifier.entityS.c(tag);
/*  93 */       tag.setBoolean("PersistenceRequired", candespawn);
/*  94 */       EntityLiving el = (EntityLiving)EntityModifier.entityS;
/*  95 */       el.a(tag);
/*  96 */       return this;
/*     */     }
/*     */     
/*     */     public org.bukkit.entity.Entity build() {
/* 100 */       return EntityModifier.entity;
/*     */     }
/*     */   }
/*     */ }
