package me.ghostedvortex.lifenetworkfwiends.Managers;

import me.ghostedvortex.lifenetworkfwiends.LifenetworkFwiends;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ConfigManager {

    private static File file;
    private static FileConfiguration config;
    private final LifenetworkFwiends plugin;
    private static File playerDataFolder;

    public ConfigManager(File dataFolder, LifenetworkFwiends plugin) {
        this.plugin = plugin;
        this.playerDataFolder = new File(dataFolder, "PlayerData");

        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    public static void initialize(LifenetworkFwiends plugin) {
        ConfigManager.setupMessageConfig();
        plugin.saveDefaultConfig();
    }

    public static void setupMessageConfig(){
        file = new File(Bukkit.getPluginManager().getPlugin("LifenetworkFwiends").getDataFolder(), "messages.yml");

        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                System.out.println(e);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getMessageConfig(){
        return config;
    }

    public static FileConfiguration getPlayerConfig(UUID playerUUID){
        File file = new File(playerDataFolder, playerUUID.toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public static void saveMessageConfig(){
        try{
            config.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save messages.yml");
        }
    }

    public static void reload(LifenetworkFwiends plugin){
        config = YamlConfiguration.loadConfiguration(file);
        plugin.saveDefaultConfig();
    }

}
