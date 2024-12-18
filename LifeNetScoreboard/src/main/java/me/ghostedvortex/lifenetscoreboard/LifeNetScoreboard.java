package me.ghostedvortex.lifenetscoreboard;

import me.ghostedvortex.lifenetscoreboard.Listeners.onJoin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.UserManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LifeNetScoreboard extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("LifeNet Scoreboard Loaded.");

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new onJoin(this), this);
        LuckPerms luckPerms = LuckPermsProvider.get();
        UserManager userManager = luckPerms.getUserManager();
    }

}
