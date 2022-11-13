package net.bote.training.backend.translation;

import com.google.gson.Gson;
import net.bote.training.backend.enums.GameRole;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author Elias Arndt | bote100
 * Created on 25.01.2020
 */

public class ChestContentTranslator {

    public void getInventory(Consumer<TranslateableInventory> consumer, GameRole role, String worldName) {
        File invFile = new File("plugins/CWBWTraining/inventorys/" , worldName + ".yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(invFile);
        String invString = cfg.getString(worldName + "." + role.toString().toLowerCase());
        Gson gson = new Gson();
        consumer.accept(gson.fromJson(invString, TranslateableInventory.class));
    }

    public TranslateableInventory getInventory(GameRole role, String worldName) {
        File invFile = new File("plugins/CWBWTraining/inventorys/" , worldName + ".yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(invFile);
        String invString = cfg.getString(worldName + "." + role.toString().toLowerCase());
        Gson gson = new Gson();
        return gson.fromJson(invString, TranslateableInventory.class);
    }

    public void save(TranslateableInventory inventory, String worldName, GameRole role) {
        Gson gson = new Gson();
        File invFile = new File("plugins/CWBWTraining/inventorys/" , worldName + ".yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(invFile);
        cfg.set(worldName + "." + role.toString().toLowerCase(), gson.toJson(inventory));
        try {
            cfg.save(invFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
