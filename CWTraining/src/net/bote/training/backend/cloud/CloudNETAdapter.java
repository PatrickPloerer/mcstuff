package net.bote.training.backend.cloud;


import java.lang.reflect.Field;

import org.bukkit.Bukkit;

/**
 * @author Elias Arndt | bote100
 * Created on 03.02.2020
 */

public class CloudNETAdapter {

    public boolean isEnabled() {
        return false;
    	//return TimoCloudAPI.isEnabled() && Training.getInstance().getMessageService().getYamlConfiguration().getBoolean("general.cloudnet");
    }

    public void ingame() {
        if(!isEnabled()) return;
        //TimoCloudAPI.getBukkitAPI().getThisServer().setState("INGAME");
    }

    public void slots(int arg0) {
        if(!isEnabled()) return;
			setMaxPlayers(arg0);
    }
    public void setMaxPlayers(int maxPlayers) {
        String bukkitversion = Bukkit.getServer().getClass().getPackage()
                .getName().substring(23);
		try {
			Object playerlist = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".CraftServer").getDeclaredMethod("getHandle").invoke(Bukkit.getServer());
			Field maxplayers = playerlist.getClass().getSuperclass().getDeclaredField("maxPlayers");
	        maxplayers.setAccessible(true);
	        maxplayers.set(playerlist, maxplayers);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
