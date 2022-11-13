package net.bote.training.backend;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.bote.training.Training;
import net.bote.training.backend.enums.*;
import net.bote.training.backend.game.MapSpawner;
import net.bote.training.backend.game.TrainingMap;
import net.bote.training.backend.game.TrainingPlayer;
import net.bote.training.backend.game.TrainingTeam;
import net.bote.training.frontend.commands.ConfigureMapCommand;
import net.bote.training.util.ItemBuilder;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;


public class TrainingController {

    private final Training main;
    private GameState currentState;
    private int lobbyCountdown;
    private int lobbyTask = 0;
    private List<TrainingMap> mapList = Lists.newArrayList();
    private TrainingMap currentMap;
    private int playedRounds = 0;
    private final int gamesAmount;

    private List<TrainingPlayer> playerList = Lists.newArrayList();
    private final Map<Player, TrainingPlayer> trainingPlayerHashMap = Maps.newHashMap();

    private final File locationFile = new File("plugins/CWBWTraining/locations.yml");
    private final YamlConfiguration locationConfiguration = YamlConfiguration.loadConfiguration(locationFile);

    public TrainingController(Training training) {
        this.main = training;
        this.currentState = GameState.LOBBY;
        this.lobbyCountdown = main.getMessageService().getYamlConfiguration().getInt("lobby.countdown");
        this.gamesAmount = Training.getInstance().getMessageService().getYamlConfiguration().getInt("general.roundAmount");

        if (this.gamesAmount % 2 == 0) {
            System.err.println("DIE RUNDENANZAHL MUSS UNGERADE SEIN! CWBW-Training wird deaktiviert. " + this.gamesAmount + " ist nicht erlaubt.");
            Training.getInstance().getPluginLoader().disablePlugin(Training.getInstance());
            return;
        }

    }

    public void startLobbyPhase() {
        Training.getInstance().getCloudNETAdapter().slots(main.getMessageService().getYamlConfiguration().getInt("general.minPlayers")*2);
        if (currentState != GameState.LOBBY) return;
        if (Bukkit.getScheduler().isQueued(lobbyTask)) return;
        if (ConfigureMapCommand.enabled) return;
        if (Bukkit.getOnlinePlayers().size() < main.getMessageService().getYamlConfiguration().getInt("general.minPlayers"))
            return;

        lobbyTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {

            if (Bukkit.getOnlinePlayers().size() < main.getMessageService().getYamlConfiguration().getInt("general.minPlayers")) {
                Bukkit.getScheduler().cancelTask(lobbyTask);
                main.getMessageService().sendToAll("lobby.cancel");
                lobbyCountdown = main.getMessageService().getYamlConfiguration().getInt("lobby.countdown");
                Bukkit.getOnlinePlayers().forEach(all -> all.setLevel(lobbyCountdown));
                return;
            }

            switch (lobbyCountdown) {
                case 30:
                case 20:
                case 10:
                case 5:
                case 4:
                case 3:
                case 2:
                case 1:
                    main.getMessageService().sendToAll("lobby.alertBegin", lobbyCountdown);
                    Bukkit.getOnlinePlayers().forEach(all -> all.playSound(all.getLocation(), Sound.NOTE_PLING, 2, 3));
                    break;

                case 0:
                    startIngame();
                    break;
            }

            Bukkit.getOnlinePlayers().forEach(all -> all.setLevel(lobbyCountdown));

            lobbyCountdown--;

        }, 0, 20);

    }

    public void startIngame() {
        Training.getInstance().getCloudNETAdapter().ingame();
        Training.getInstance().getCloudNETAdapter().slots(100);
        insertToTeam();
        Bukkit.getOnlinePlayers().forEach(all -> Training.getInstance().getMySQLBridge().addToEntry("plays", 1, all.getUniqueId().toString()));
        main.getMessageService().sendToAll("lobby.alertStart");
        Bukkit.getScheduler().cancelTask(lobbyTask);
        setCurrentState(GameState.INGAME);
        moveToMap(selectRandomTrainingMap());
    }

    public GameState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}

	public TrainingMap getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(TrainingMap currentMap) {
		this.currentMap = currentMap;
	}

	private int restartCountdown = 10;

    public void startRestartPhase(TrainingTeam winner) {
    	Training.getInstance().getCloudNETAdapter().slots(0);
        setCurrentState(GameState.RESTART);
        System.out.println("winner = " + winner);
        winner.getMembers().forEach(trainingPlayer -> {
            Training.getInstance().getMySQLBridge().addToEntry("wins", 1, trainingPlayer.getPlayer().getUniqueId().toString());
            Training.getInstance().getMySQLBridge().addToEntry("points", 3, trainingPlayer.getPlayer().getUniqueId().toString());
        });

        if(Training.getInstance().getMessageService().getYamlConfiguration().getBoolean("restart.teleportToLobby"))
            Bukkit.getOnlinePlayers().forEach(all -> {
            	all.getInventory().clear();
            	all.getInventory().setBoots(new ItemStack(Material.AIR));
            	all.getInventory().setChestplate(new ItemStack(Material.AIR));
            	all.getInventory().setLeggings(new ItemStack(Material.AIR));
            	all.getInventory().setHelmet(new ItemStack(Material.AIR));
            	all.teleport(getLobbySpawn());
            });

        AtomicInteger atomicInteger = new AtomicInteger(0);
        int task;
        Bukkit.broadcastMessage(Training.getInstance().getMessageService().getMessage("restart.winmessage", winner.getColorCode(), winner.getName()));

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Training.getInstance(), () -> {
            if (restartCountdown == 0) {
                Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(Training.getInstance().getMessageService().getMessage("restart.restartNow")));
                if (Training.getInstance().getMessageService().getYamlConfiguration().getBoolean("restart.stopServer"))
                    Bukkit.getServer().shutdown();
                else Bukkit.getServer().reload();
                Bukkit.getScheduler().cancelTask(atomicInteger.get());
                return;
            }

            Bukkit.broadcastMessage(Training.getInstance().getMessageService().getMessage("restart.restart", restartCountdown));
            restartCountdown--;
        }, 0, 20);
        atomicInteger.set(task);
    }

    public TrainingTeam getTeamByRole(GameRole role) {
        if (Training.getInstance().getTeamRed().getRole().equals(role)) return Training.getInstance().getTeamRed();
        return Training.getInstance().getTeamBlue();
    }

    public TrainingTeam getOppenent(TrainingTeam team) {
        if (team.getRole().equals(GameRole.DEFENDER)) return getTeamByRole(GameRole.ATTACKER);
        return getTeamByRole(GameRole.DEFENDER);
    }

    public void moveToMap(TrainingMap map) {
        this.currentMap = map;

        if (Objects.isNull(currentMap)) {
            Bukkit.broadcastMessage("§4§lES IST KEINE TRAINING-MAP VORHANDEN!");
            return;
        }
        System.out.println("[Training] Moving to map " + map.getName());

        playedRounds = getPlayedRounds() + 1;
        Training.getInstance().getTeamStream().forEach(TrainingTeam::switchRole);
        Bukkit.getOnlinePlayers().forEach(all -> {
            Training.getInstance().getSideBarHandler().display(all);
            TrainingPlayer cwtPlayer = getCWTPlayer(all);
            if (cwtPlayer.getSpectatorState().equals(SpectatorState.NORMAL_SPECTATOR)) {
                all.teleport(currentMap.getSpectatorSpawn());
                Bukkit.getScheduler().runTaskLater(Training.getInstance(), () -> all.setGameMode(GameMode.SPECTATOR), 2);
                return;
            }

            all.getInventory().clear();
            all.setHealth(20D);
            all.setFoodLevel(20);
            all.getActivePotionEffects().forEach(potionEffect -> all.removePotionEffect(potionEffect.getType()));
            all.getInventory().setArmorContents(null);
            all.setGameMode(GameMode.SURVIVAL);
            cwtPlayer.setSpectatorState(SpectatorState.NONE);
            Training.getInstance().getTitleService().sendTitle(all,
                    Training.getInstance().getMessageService().getMessage("ingame.title." + cwtPlayer.getTeam().getRole().toString().toLowerCase() + ".caption"),
                    Training.getInstance().getMessageService().getMessage("ingame.title." + cwtPlayer.getTeam().getRole().toString().toLowerCase() + ".subtitle"),
                    0, 2, 0);

            all.getInventory().setHelmet(createColoredItem(cwtPlayer, Material.LEATHER_HELMET));
            all.getInventory().setLeggings(createColoredItem(cwtPlayer, Material.LEATHER_LEGGINGS));
            all.getInventory().setBoots(createColoredItem(cwtPlayer, Material.LEATHER_BOOTS));

            if (cwtPlayer.getTeam().getRole().equals(GameRole.ATTACKER)) {
                System.out.println("currentMap.getAttackSpawn() = " + currentMap.getAttackSpawn());
                currentMap.getAttackSpawn().getChunk().load();
                all.setFireTicks(0);
                all.setFallDistance(0);
                all.teleport(currentMap.getAttackSpawn());
                new BukkitRunnable() {
					int counter = 0;
					@Override
					public void run() {
						if(counter <= 4) {
						counter++;
						all.teleport(currentMap.getAttackSpawn());
						}else {
							this.cancel();	
						}
					}
				}.runTaskTimer(Training.getInstance(), 0, 3);
                for (int i = 0; i < 9; i++) all.getInventory().setItem(i, currentMap.getAttackInventory().getItem(i));
                all.getInventory().setChestplate(
                        new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                                .addEnchantment(Enchantment.DURABILITY, 3)
                                .build());
            } else {
            	all.setFireTicks(0);
                all.setFallDistance(0);
                all.teleport(currentMap.getDefenseSpawn());
                new BukkitRunnable() {
					int counter = 0;
					@Override
					public void run() {
						if(counter <= 4) {
							counter++;
						all.teleport(currentMap.getDefenseSpawn());
						}else {
							this.cancel();	
						}
					}
				}.runTaskLater(Training.getInstance(), 15);
                for (int i = 0; i < 9; i++) all.getInventory().setItem(i, currentMap.getDefenseInventory().getItem(i));
                all.getInventory().setChestplate(
                        new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                                .addEnchantment(Enchantment.DURABILITY, 3)
                                .build());
            }
        });
        currentMap.startCountdown();
        alreadyPlayedMaps.add(currentMap);
    }

    private List<TrainingMap> alreadyPlayedMaps = Lists.newArrayList();

    public TrainingMap selectRandomTrainingMap() {
        if (mapList.size() == 0 || mapList.size() == alreadyPlayedMaps.size()) return null;
        TrainingMap rndMap = mapList.get(ThreadLocalRandom.current().nextInt(mapList.size()));
        if (alreadyPlayedMaps.contains(rndMap)) return selectRandomTrainingMap();
        return rndMap;
    }

    private Location getLobbySpawn() {
        YamlConfiguration cfg = Training.getInstance().getController().getLocationConfiguration();

        if (Objects.isNull(cfg.getString("Lobby.World"))) return null;

        return new Location(
                Bukkit.getWorld(cfg.getString("Lobby.World")),
                cfg.getDouble("Lobby.X"),
                cfg.getDouble("Lobby.Y"),
                cfg.getDouble("Lobby.Z"),
                (float) cfg.getDouble("Lobby.Yaw"),
                (float) cfg.getDouble("Lobby.Pitch")
        );
    }

    public YamlConfiguration getLocationConfiguration() {
		return locationConfiguration;
	}

	public Location getTrainingLocation(String world, TrainingLocation trainingLocation) {
        return new Location(
                Bukkit.getWorld(world),
                locationConfiguration.getDouble(world + "." + trainingLocation.getLocationName() + ".X"),
                locationConfiguration.getDouble(world + "." + trainingLocation.getLocationName() + ".Y"),
                locationConfiguration.getDouble(world + "." + trainingLocation.getLocationName() + ".Z"),
                (float) locationConfiguration.getDouble(world + "." + trainingLocation.getLocationName() + ".Yaw"),
                (float) locationConfiguration.getDouble(world + "." + trainingLocation.getLocationName() + ".Pitch")
        );
    }

    public void setupMaps() {
        System.out.println("[Training] Setting up maps...!");
        locationConfiguration.getKeys(false).stream().filter(key -> !key.equals("Lobby") && !key.equals("Head")).forEach(key -> {

            if (Bukkit.getWorld(key) == null) {
                new WorldCreator(key).createWorld();
                System.out.println("[Training] [##########] 100% loaded world " + key);
            }

            TrainingMap map = new TrainingMap(
                    getTrainingLocation(key, TrainingLocation.DEFENDER_SPAWN),
                    getTrainingLocation(key, TrainingLocation.ATTACKER_SPAWN),
                    Training.getInstance().getChestTranslator().getInventory(GameRole.DEFENDER, key).getInventory(),
                    Training.getInstance().getChestTranslator().getInventory(GameRole.ATTACKER, key).getInventory(),
                    getTrainingLocation(key, TrainingLocation.SPEC_SPAWN),
                    getTrainingLocation(key, TrainingLocation.VILLAGER),
                    key,
                    new MapSpawner(getTrainingLocation(key, TrainingLocation.BRONZE_SPAWNER), SpawnerType.BRONZE, 10),
                    new MapSpawner(getTrainingLocation(key, TrainingLocation.SILVER_SPAWNER), SpawnerType.SILVER, 20 * 10),
                    new MapSpawner(getTrainingLocation(key, TrainingLocation.GOLD_SPAWNER), SpawnerType.GOLD, 20 * 20)
            );

            mapList.add(map);
            System.out.println("[Training] Set map '" + key + "' up...");
        });
    }

    private void insertToTeam() {
        Bukkit.getOnlinePlayers().forEach(all -> {
            TrainingPlayer trainingPlayer = getCWTPlayer(all);
            if (trainingPlayer.getTeam() != null) return;
            if (!Training.getInstance().getTeamBlue().isFull())
                trainingPlayer.addToTeam(Training.getInstance().getTeamBlue());
            else trainingPlayer.addToTeam(Training.getInstance().getTeamRed());
        });
    }

    public TrainingPlayer getCWTPlayer(Player player) {
        if (trainingPlayerHashMap.containsKey(player)) return trainingPlayerHashMap.get(player);
        return playerList.stream().filter(cp -> cp.getPlayer().getName().equals(player.getName())).findFirst().orElseGet(null);
    }

    public void forgetPlayer(TrainingPlayer player) {
        trainingPlayerHashMap.remove(player.getPlayer());
        playerList.remove(player);
        player.removeFromTeam();
    }

    private ItemStack createColoredItem(TrainingPlayer trainingPlayer, Material mat) {
        ItemStack h1 = new ItemStack(mat);
        LeatherArmorMeta im = (LeatherArmorMeta) h1.getItemMeta();
        im.setColor(trainingPlayer.getTeam().getColor());
        im.addEnchant(Enchantment.DURABILITY, 3, true);
        h1.setItemMeta(im);
        return h1;
    }

	public int getGamesAmount() {
		return gamesAmount;
	}

	public int getPlayedRounds() {
		return playedRounds;
	}

	public int getLobbyTask() {
		return lobbyTask;
	}

	public File getLocationFile() {
		return locationFile;
	}

	public int getLobbyCountdown() {
		return lobbyCountdown;
	}

	public void setLobbyCountdown(int int1) {
		lobbyCountdown = int1;
		
	}

	public List<TrainingPlayer> getPlayerList() {
		return playerList;
	}

}
