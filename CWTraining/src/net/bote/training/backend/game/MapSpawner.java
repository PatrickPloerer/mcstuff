package net.bote.training.backend.game;

import net.bote.training.Training;
import net.bote.training.backend.enums.SpawnerType;
import net.bote.training.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Collections;
import java.util.Random;


public class MapSpawner {

    public final Location location;
    public final SpawnerType type;
    public final int interval;

    private int taskId = 0;

    public MapSpawner(Location trainingLocation, SpawnerType type, int i) {
		location = trainingLocation;
		this.type = type;
		interval = i;
    }

	public void startSpawning() {
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Training.getInstance(), () -> {
            String[] z = new String[]{"§c", "§0", "§d", "§5", "§4", "§e", "§3", "§6", "§7"};
            //ItemStaItemAPI.createSpawnItem(336, "§cBronze", random));
            location.getWorld().dropItem(location, new ItemBuilder(type.getMaterial()).setName(this.type.getName()).setLore(Collections.singletonList(z[new Random().nextInt(z.length)])).build());
        }, 0, this.interval);
    }

    public void stopSpawning() {
        Bukkit.getScheduler().cancelTask(this.taskId);
    }

}
