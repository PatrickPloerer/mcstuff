package net.bote.training.backend.game;

import net.bote.training.Training;
import net.bote.training.backend.EntityModifier;
import net.bote.training.backend.WorldResetter;
import net.bote.training.backend.enums.GameRole;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author Elias Arndt | bote100
 * Created on 23.01.2020
 */

public class TrainingMap {

    private Location defenseSpawn;
    private Location attackSpawn;
    private Inventory defenseInventory;
    private Inventory attackInventory;
    private Location spectatorSpawn;
    private Location villagerLocation;
    private String name;
    private boolean roundRunning;

    private final MapSpawner bronzeSpawner;
    private final MapSpawner silverSpawner;
    private final MapSpawner goldSpawner;

    private Villager villager;
    private ArmorStand cart;

    private int secondsRemain = Training.getInstance().getMessageService().getYamlConfiguration().getInt("ingame.lenght");

    private int cdTask = 0;

    public TrainingMap(Location trainingLocation, Location trainingLocation2, Inventory inventory, Inventory inventory2,
			Location trainingLocation3, Location trainingLocation4, String key, MapSpawner mapSpawner,
			MapSpawner mapSpawner2, MapSpawner mapSpawner3) {
		defenseSpawn = trainingLocation;
    	attackSpawn = trainingLocation2;
    	defenseInventory = inventory;
    	attackInventory = inventory2;
    	spectatorSpawn = trainingLocation3;
    	villagerLocation = trainingLocation4;
    	name = key;
    	bronzeSpawner = mapSpawner;
    	silverSpawner = mapSpawner2;
    	goldSpawner = mapSpawner3;
    	
	}

	public void startCountdown() {
        if(roundRunning) return;
        roundRunning = true;
        startSpawners();
        setVillager();
        System.out.println("[Training] Started countdown for map " + this.name);
        WorldResetter.saveWorld(Bukkit.getWorld(this.name));
        Bukkit.getWorld(this.name).setStorm(false);
        cdTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Training.getInstance(), () -> {
            Bukkit.getOnlinePlayers().forEach(all
                    -> Training.getInstance().getTitleService().sendActionbar(
                        all,
                        Training.getInstance().getMessageService().getMessage("ingame.timeRemaining",
                                Integer.valueOf(secondsRemain/60), (String.valueOf(secondsRemain%60).toCharArray().length == 1 ? "0" : "") + secondsRemain%60)));

            if(secondsRemain == 0) {
                stopCountdown();
                onCountdownEnds();
            }

            secondsRemain--;
        }, 0, 20);

        File worldFile = new File("plugins/CWBWTraining/dontdelete.yml");
        YamlConfiguration worldCFG = YamlConfiguration.loadConfiguration(worldFile);

        if(worldCFG.contains("worlds")) {
            worldCFG.set("worlds", worldCFG.getString("worlds") + ";" + this.name);
        } else worldCFG.set("worlds", this.name);

        try {
            worldCFG.save(worldFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setVillager() {

        this.villager = Bukkit.getWorld(this.name).spawn(villagerLocation, Villager.class);

        this.cart = Bukkit.getWorld(this.name).spawn(villagerLocation, ArmorStand.class);

        villager.setCustomName(Training.getInstance().getMessageService().getMessage("ingame.shopName"));
        villager.setAdult();

        villager.teleport(villagerLocation);
        villager.setProfession(Villager.Profession.FARMER);
        villager.setCanPickupItems(false);
        villager.setRemoveWhenFarAway(false);
        villager.setCustomNameVisible(true);
        villager.setFallDistance(0F);
        villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999191, 500));
        new EntityModifier(villager, Training.getInstance()).modify().setCanDespawn(false).setInvulnerable(true).setPassenger(cart).build();
        new EntityModifier(cart, Training.getInstance()).modify().setInvisible(true).setInvulnerable(true).setNoAI(true).build();
    }

    public void startSpawners() {
        Stream.of(bronzeSpawner, silverSpawner, goldSpawner).forEach(MapSpawner::startSpawning);
    }

    public void stopSpawners() {
        Stream.of(bronzeSpawner, silverSpawner, goldSpawner).forEach(MapSpawner::stopSpawning);
    }

    public void closeRound(TrainingTeam winner, boolean end) {
        if(!roundRunning) return;
        winner.addPoint();
        secondsRemain = Training.getInstance().getMessageService().getYamlConfiguration().getInt("ingame.lenght");
        Bukkit.getScheduler().cancelTask(cdTask);
        stopSpawners();

        Stream.of(villager, cart).forEach(vs -> {
            new EntityModifier(vs, Training.getInstance()).modify().setInvulnerable(false).build();
            new EntityModifier(vs, Training.getInstance()).modify().die().build();
        });

        if((Training.getInstance().getController().getGamesAmount()==Training.getInstance().getController().getPlayedRounds()) || end) {
            Training.getInstance().getController().startRestartPhase(winner);
            Bukkit.getOnlinePlayers().forEach(all->Training.getInstance().getSideBarHandler().display(all));
            roundRunning = false;
            return;
        }
        Bukkit.getOnlinePlayers().forEach(all->all.sendMessage(Training.getInstance().getMessageService().getMessage("ingame.winRound", winner.getColorCode(), winner.getName())));
        Training.getInstance().getController().moveToMap(Training.getInstance().getController().selectRandomTrainingMap());
    }

    public void stopCountdown() {
        Bukkit.getScheduler().cancelTask(cdTask);
    }

    private void onCountdownEnds() {
        String msg = "";
        if(Training.getInstance().getController().getTeamByRole(GameRole.DEFENDER).isRespawnable()) {
            msg = Training.getInstance().getMessageService().getMessage("ingame.noResultDef");
            closeRound(Training.getInstance().getController().getTeamByRole(GameRole.DEFENDER), false);
        } else {
            msg = Training.getInstance().getMessageService().getMessage("ingame.noResultAtt");
            closeRound(Training.getInstance().getController().getTeamByRole(GameRole.ATTACKER), false);
        }
        Bukkit.broadcastMessage(msg);
    }

    public void load() {
        new WorldCreator(this.name).createWorld();
        System.out.println("[Training] [##########] 100% loaded world " + this.name);
    }

	public String getName() {
		return name;
	}

	public Location getSpectatorSpawn() {
		return spectatorSpawn;
	}

	public Location getAttackSpawn() {
		return attackSpawn;
	}

	public Location getDefenseSpawn() {
		return defenseSpawn;
	}

	public Inventory getDefenseInventory() {
		return defenseInventory;
	}

	public Inventory getAttackInventory() {
		return attackInventory;
	}
}
