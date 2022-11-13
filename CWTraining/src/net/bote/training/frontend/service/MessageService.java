package net.bote.training.frontend.service;

import com.google.common.collect.Maps;
import net.bote.training.Training;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;
import java.util.Objects;

/**
 * @author Elias Arndt | bote100
 * Created on 23.01.2020
 */

public class MessageService {

    private final Training training;
    public File configFile = new File("plugins//CWBWTraining", "config.yml");
    public YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
    private Map<String, String> cache = Maps.newHashMap();
    private String prefix;

    public MessageService(Training main) {
        this.training = main;
        this.training.reloadConfig();
    }

    public void sendToAll(String key, Object... args) {
        Bukkit.getOnlinePlayers().forEach(all->all.sendMessage(getMessage(key, args)));
        Bukkit.getConsoleSender().sendMessage(getMessage(key, args));
    }

    public String getMessage(String key, Object... args) {
        if(args.length == 0 && cache.containsKey(key)) return cache.get(key);
        String message = yamlConfiguration.getString(key);
        message = message.replace("{prefix}", getPrefix()).replace("%>%", "»");
        int i = 0;
        for (Object arg : args) {
            message = message.replaceAll("\\{" + i + "}", arg.toString());
            i++;
        }
        message = this.validate(ChatColor.translateAlternateColorCodes('&', message));
        if(args.length == 0) cache.put(key, message);
        return message;
    }

    public String getPrefix() {
        if(Objects.nonNull(prefix)) return prefix;
        prefix = validate(ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("general.prefix"))).replace("%>%", "»");
        return prefix;
    }

    public void createConfig() {
        this.training.getConfig().options().copyDefaults(true);
        this.training.saveDefaultConfig();
    }

    // Zur Auswahl: ï¸³”ƒ –�

    public String validate(String arg0) {
        return arg0.replaceAll( "%n%", "\n" ).replace("%oe%", "Ã¶").replace("%ue%", "Ã¼").replace("%ae%", "Ã¤").replace("%|%", "”ƒ");
    }

	public YamlConfiguration getYamlConfiguration() {
		return yamlConfiguration;
	}


}