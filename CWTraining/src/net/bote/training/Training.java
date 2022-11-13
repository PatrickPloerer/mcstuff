package net.bote.training;

import com.google.common.collect.Lists;
import net.bote.training.backend.TrainingController;
import net.bote.training.backend.WorldResetter;
import net.bote.training.backend.cloud.CloudNETAdapter;
import net.bote.training.backend.enums.GameRole;
import net.bote.training.backend.game.TrainingTeam;
import net.bote.training.backend.sql.MySQLBridge;
import net.bote.training.backend.sql.NameFetcher;
import net.bote.training.backend.translation.ChestContentTranslator;
import net.bote.training.frontend.commands.*;
import net.bote.training.frontend.service.MessageService;
import net.bote.training.frontend.handler.SideBarHandler;
import net.bote.training.frontend.service.TitleService;
import net.bote.training.util.CustomSophia;
import net.bote.training.frontend.handler.StatsRankingHandler;
import net.bote.training.frontend.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Elias Arndt | bote100
 * Created on 23.01.2020
 */

public class Training extends JavaPlugin {

    private static Training instance;

    public static Training getInstance() {
		return instance;
	}

	public static void setInstance(Training instance) {
		Training.instance = instance;
	}

	private TrainingController controller;
    private MessageService messageService;
    private SideBarHandler sideBarHandler;
    private ChestContentTranslator chestTranslator;
    private TitleService titleService;
    private MySQLBridge mySQLBridge;
    private NameFetcher nameFetcher;
    private StatsRankingHandler statsRankingHandler;
    private CloudNETAdapter cloudNETAdapter;

    private TrainingTeam teamBlue;
    private TrainingTeam teamRed;

    @Override
    public void onEnable() {
        instance = this;
        this.messageService = new MessageService(this);
        this.messageService.createConfig();
        this.controller = new TrainingController(this);
        this.sideBarHandler = new SideBarHandler();
        this.chestTranslator = new ChestContentTranslator();
        this.titleService = new TitleService();
        this.cloudNETAdapter = new CloudNETAdapter();
        this.mySQLBridge = new MySQLBridge(
                messageService.getYamlConfiguration().getString("general.mysql.host"),
                messageService.getYamlConfiguration().getString("general.mysql.user"),
                messageService.getYamlConfiguration().getString("general.mysql.database"),
                messageService.getYamlConfiguration().getString("general.mysql.password")
        );
        this.mySQLBridge.connect();
        this.nameFetcher = new NameFetcher(this.mySQLBridge);
        this.statsRankingHandler = new StatsRankingHandler(this.mySQLBridge, this.nameFetcher);

        init();
        initTeams();
        this.controller.setupMaps();
    }

    private void initTeams() {
        this.teamBlue = new TrainingTeam(
                Lists.newArrayList(),
                this.messageService.getMessage("lobby.teams.blue"),
                this.messageService.getMessage("lobby.teams.blueColor"),
                this.messageService.getYamlConfiguration().getInt("general.playersPerTeam"),
                GameRole.DEFENDER,
                Color.BLUE);

        this.teamRed = new TrainingTeam(
                Lists.newArrayList(),
                this.messageService.getMessage("lobby.teams.red"),
                this.messageService.getMessage("lobby.teams.redColor"),
                this.messageService.getYamlConfiguration().getInt("general.playersPerTeam"),
                GameRole.ATTACKER,
                Color.RED);
    }

    private void init() {
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new PingListener(), this);
        Bukkit.getPluginManager().registerEvents(new EnvironmentListener(), this);
        Bukkit.getPluginManager().registerEvents(new CWChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PickUpListener(), this);
        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new CustomSophia(), this);
        getCommand("setlobby").setExecutor(new SetLobbyCommand());
        getCommand("configuremap").setExecutor(new ConfigureMapCommand());
        getCommand("forcestart").setExecutor(new ForceStartCommand());
        getCommand("start").setExecutor(new ForceStartCommand());
        getCommand("addhead").setExecutor(new AddHeadCommand());
        getCommand("stats").setExecutor(new TrainingStatsCommand(this.mySQLBridge, this.messageService));
    }

    public Stream<TrainingTeam> getTeamStream() {
        return Stream.of(teamBlue, teamRed);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(all->all.kickPlayer(""));

        try {
            File worldFile = new File("plugins/CWBWTraining/dontdelete.yml");
            YamlConfiguration worldCFG = YamlConfiguration.loadConfiguration(worldFile);

            Arrays.stream(worldCFG.getString("worlds").split(";")).forEach(key -> {
                System.out.println("[Training] Resetting " + key);
                WorldResetter.resetWorld(Bukkit.getWorld(key));
            });

            System.out.println("worldFile.delete(); = " + worldFile.delete());

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

	public MessageService getMessageService() {
		return messageService;
	}

	public SideBarHandler getSideBarHandler() {
		return sideBarHandler;
	}

	public ChestContentTranslator getChestTranslator() {
		return chestTranslator;
	}

	public TitleService getTitleService() {
		return titleService;
	}

	public StatsRankingHandler getStatsRankingHandler() {
		return statsRankingHandler;
	}

	public CloudNETAdapter getCloudNETAdapter() {
		return cloudNETAdapter;
	}

	public MySQLBridge getMySQLBridge() {
		return mySQLBridge;
	}

	public TrainingTeam getTeamRed() {
		return teamRed;
	}
	public TrainingTeam getTeamBlue() {
		return teamBlue;
	}

	public TrainingController getController() {
		return controller;
	}

	public NameFetcher getNameFetcher() {
		return nameFetcher;
	}
}
