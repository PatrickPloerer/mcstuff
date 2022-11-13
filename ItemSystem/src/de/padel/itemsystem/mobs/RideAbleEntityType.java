package de.padel.itemsystem.mobs;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public enum RideAbleEntityType {
  RideAblePig("Pig", 90, RideAblePig.class),
  RideAbleCow("Cow", 92, RideAbleCow.class),
  RideAbleChicken("Chicken", 93, RideAbleChicken.class),
  RideAbleGuardian("Guardian", 68, RideAbleGuardian.class);
  
  RideAbleEntityType(String name, int id, Class<? extends Entity> custom) {
    addToMaps(custom, name, id);
  }
  
  public static Entity spawnEntity(Entity entity, Location loc1) {
    entity.setLocation(loc1.getX(), loc1.getY(), loc1.getZ(), loc1.getYaw(), loc1.getPitch());
    ((CraftWorld)loc1.getWorld()).getHandle().addEntity(entity);
    return entity;
  }
  
  @SuppressWarnings("unchecked")
private static void addToMaps(Class<?> clazz, String name, int id) {
    ((Map<String, Class<?>>)getPrivateField("c", EntityTypes.class, null)).put(name, clazz);
    ((Map<Class<?>, String>)getPrivateField("d", EntityTypes.class, null)).put(clazz, name);
    ((Map<Integer, Class<?>>)getPrivateField("e", EntityTypes.class, null)).put(Integer.valueOf(id), clazz);
    ((Map<Class<?>, Integer>)getPrivateField("f", EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
    ((Map<String, Integer>)getPrivateField("g", EntityTypes.class, null)).put(name, Integer.valueOf(id));
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
