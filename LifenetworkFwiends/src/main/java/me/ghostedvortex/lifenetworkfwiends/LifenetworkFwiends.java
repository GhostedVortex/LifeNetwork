package me.ghostedvortex.lifenetworkfwiends;

import me.ghostedvortex.lifenetworkfwiends.Commands.FriendCommand;
import me.ghostedvortex.lifenetworkfwiends.Commands.LifenetworkCommand;
import me.ghostedvortex.lifenetworkfwiends.Commands.SettingsCommand;
import me.ghostedvortex.lifenetworkfwiends.Listeners.OnJoinListener;
import me.ghostedvortex.lifenetworkfwiends.Managers.*;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class LifenetworkFwiends extends JavaPlugin {

    private static FriendManager friendManager;
    private static SettingsManager settingsManager;
    private static ConfigManager configManager;

    @Override
    public void onEnable() {

        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        FriendManager friendManager = new FriendManager(dataFolder, this);
        getServer().getPluginManager().registerEvents(new OnJoinListener(friendManager), this);
        getCommand("friend").setExecutor(new FriendCommand(this, friendManager));
        getCommand("lifenetwork").setExecutor(new LifenetworkCommand(this));
        getCommand("settings").setExecutor(new SettingsCommand(settingsManager, configManager));

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        ConfigManager.setupMessageConfig();
        ConfigManager.getMessageConfig().addDefault("error-message", "&cAn error has occurred while running this command");
        ConfigManager.getMessageConfig().addDefault("reload-message", "&bSuccessfully reloaded the plugin");
        ConfigManager.getMessageConfig().options().copyDefaults(true);
        ConfigManager.saveMessageConfig();


    }
}
