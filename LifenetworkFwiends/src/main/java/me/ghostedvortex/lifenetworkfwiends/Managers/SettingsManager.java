package me.ghostedvortex.lifenetworkfwiends.Managers;

import me.ghostedvortex.lifenetworkfwiends.LifenetworkFwiends;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class SettingsManager {

    private static File playerDataFolder;
    private final LifenetworkFwiends plugin;

    public SettingsManager(File dataFolder, LifenetworkFwiends plugin) {
        this.playerDataFolder = new File(dataFolder, "PlayerData");
        this.plugin = plugin;

        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    public void friendRequestsOn(Player player, UUID playerUUID){
        File playerFile = new File(playerDataFolder, playerUUID.toString() + ".yml");

        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        playerConfig.set("Options.FriendRequests", true);
        StringBuilder sb = new StringBuilder();
        String prefix = this.plugin.getConfig().getString("prefix");
        if (prefix != null){
            sb.append(prefix);
            sb.append("&bSuccessfully turned FriendRequests On");
            player.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
        } else {
            Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
        }
        try{
            playerConfig.save(playerFile);
        }catch (IOException e){
            System.out.println(e);
        }
    }
    public void friendRequestsOff(Player player, UUID playerUUID){
        File playerFile = new File(playerDataFolder, playerUUID.toString() + ".yml");

        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        playerConfig.set("Options.FriendRequests", false);
        StringBuilder sb = new StringBuilder();
        String prefix = this.plugin.getConfig().getString("prefix");
        if (prefix != null){
            sb.append(prefix);
            sb.append("&bSuccessfully turned FriendRequests Off");
            player.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
        } else {
            Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
        }
        try{
            playerConfig.save(playerFile);
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
