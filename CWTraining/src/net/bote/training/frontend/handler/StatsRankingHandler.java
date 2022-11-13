package net.bote.training.frontend.handler;

import net.bote.training.Training;
import net.bote.training.backend.sql.MySQLBridge;
import net.bote.training.backend.sql.NameFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elias Arndt | bote100
 * Created on 25.09.2019
 */

public class StatsRankingHandler {

    private boolean loaded = false;

    private HashMap<Integer, String> map = new HashMap<>();

    private final MySQLBridge mySQLBridge;
    private final NameFetcher nameFetcher;
    
    public StatsRankingHandler(MySQLBridge mysqlBridge, NameFetcher namefetcher) {
    	this.mySQLBridge = mysqlBridge;
    	this.nameFetcher = namefetcher;
    }

    public void loadRanking() {

        if(isLoaded()) return;
        loaded = true;
        try {
            ResultSet rs = mySQLBridge.query("SELECT uuid FROM cwbwtraining ORDER BY points DESC LIMIT 10");

            int zahl = 0;

            while (rs.next()) {
                zahl++;
                map.put(zahl, rs.getString("uuid"));

            }


            ArrayList<Location> locs = new ArrayList<>();

            for (int i = 0; i < 10; i++)
                locs.add(getStatsHeadLocation("Head." + (i+1)));

            AtomicInteger atomicInt = new AtomicInteger();
            AtomicInteger idInt = new AtomicInteger(0);

            int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Training.getInstance(), () -> {

                if (idInt.addAndGet(1) > locs.size()) {
                    Bukkit.getScheduler().cancelTask(atomicInt.get());
                    return;
                }

                int id = idInt.get();

                locs.get(idInt.get() - 1).getBlock().setType(Material.SKULL);
                Skull s = (Skull) locs.get(idInt.get() - 1).getBlock().getState();
                s.setSkullType(SkullType.PLAYER);

                boolean legit = true;

                String name = "???";
                try {
                    name = nameFetcher.getName(UUID.fromString(map.get(id)));
                    s.setOwner(name);
                    s.setRotation(BlockFace.valueOf(Training.getInstance().getMessageService().getYamlConfiguration().getString("lobby.statsRotation")));
                } catch (NullPointerException e) {
                    legit = false;
                    s.setOwner("MHF_Question");
                    s.setRotation(BlockFace.valueOf(Training.getInstance().getMessageService().getYamlConfiguration().getString("lobby.statsRotation")));
                }

                s.update();

                Location newloc = locs.get(idInt.get() - 1).subtract(0D, 1D, 0D);

                if (newloc.getBlock().getState() instanceof Sign) {

                    BlockState b = newloc.getBlock().getState();
                    Sign sign = (Sign) b;

                    ResultSet resultSet = mySQLBridge.query("SELECT points FROM cwbwtraining WHERE uuid='" + map.get(id) + "'");

                    AtomicInteger atomicInteger = new AtomicInteger(0);

                    try {
                        if(resultSet.next()) atomicInteger.set(resultSet.getInt("points"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    sign.setLine(0, "- Platz #" + id + " -");
                    if (legit) {
                        sign.setLine(1, name);
                        sign.setLine(2, " ");
                        sign.setLine(3, atomicInteger.get() + " Points");
                    } else {
                        sign.setLine(1, "???");
                        sign.setLine(2, " ");
                        sign.setLine(3, "??? Points");
                    }
                    sign.update();

                } else {
                    System.err.println(newloc.toString() + " is not sign to set for TOP10-Wall!!");
                }

            }, 0, 2 * 20);
            atomicInt.set(task);


        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§cCWBWTraining §7| §cDie MySQL Verbindung wurde nicht aufgebaut, weswegen leider die Statswand nicht genutzt werden kann!");
            Bukkit.getConsoleSender().sendMessage("§6>> " + e.getMessage());
        }

    }

    private boolean isLoaded() {
		return loaded;
	}

	public Location getStatsHeadLocation(String key) {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(new File("plugins/CWBWTraining/locations.yml"));
        return new Location(
                Bukkit.getWorld(cfg.getString(key + ".World")),
                cfg.getDouble(key + ".X"),
                cfg.getDouble(key + ".Y"),
                cfg.getDouble(key + ".Z"),
                (float) cfg.getDouble(key + ".Yaw"),
                (float) cfg.getDouble(key + ".Pitch")
        );
    }

}
