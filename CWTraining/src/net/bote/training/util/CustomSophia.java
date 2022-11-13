package net.bote.training.util;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSheep;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.bote.training.Training;
import net.minecraft.server.v1_8_R3.EntitySheep;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.World;

public class CustomSophia implements Listener{

	static HashMap<UUID, Player> sheeps = new HashMap<>();
	
	public static void SpawnSophi‰(Location loc, Player p) {
		World w = (World)((CraftWorld)loc.getWorld()).getHandle();
		
		EntitySheep es = new EntitySheep(w);
		es.setCustomName("Sophi‰‰‰‰‰‰‰");
		Color c = Training.getInstance().getController().getCWTPlayer(p).getTeam().getColor();
		
		Player opp = Training.getInstance().getController().getOppenent(Training.getInstance().getController().getCWTPlayer(p).getTeam()).getMembers().get(0).getPlayer();
		if(c == Color.RED) {
			es.setColor(EnumColor.RED);	
		}else {
			es.setColor(EnumColor.BLUE);
		}
		EntityTNTPrimed tnt = new EntityTNTPrimed(w);
		tnt.fuseTicks = 127;
		tnt.setCustomNameVisible(false);
		es.passenger = tnt;
		
		p.sendMessage(es.passenger.getCustomName());
		//es.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		w.addEntity(tnt);
		//w.addEntity(es);
		es.setCustomNameVisible(false);
		float[] f = {0F, 0F};
		es.dropChances = f;
		sheeps.put(es.getUniqueID(), opp);
		es.passenger = tnt;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				sheeps.remove(es.getUniqueID());
				es.setHealth(0);
				w.removeEntity(es);
			}
		}.runTaskLater(Training.getInstance(), 125);
	}
	
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if(((CraftEntity)e.getEntity()).getHandle() instanceof EntitySheep) {
			EntitySheep en = ((CraftSheep)e.getEntity()).getHandle();
			if(sheeps.containsKey(en.getUniqueID())) {
				Player pl = sheeps.get(en.getUniqueID());
				PathfinderGoalWalkToLoc p = new PathfinderGoalWalkToLoc(en, pl.getLocation() , 10);
				p.c();
			}
		}
	}
	
	

}
