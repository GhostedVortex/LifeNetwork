package me.ghostedvortex.lifenetworkfwiends.Commands;

import me.ghostedvortex.lifenetworkfwiends.LifenetworkFwiends;
import me.ghostedvortex.lifenetworkfwiends.Managers.ColorUtils;
import me.ghostedvortex.lifenetworkfwiends.Managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LifenetworkCommand implements CommandExecutor {

    private final LifenetworkFwiends plugin;

    public LifenetworkCommand(LifenetworkFwiends plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        String subCommand = args[0];
        switch (subCommand){
            case "reload":
                ConfigManager.reload(plugin);
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    String reloaded = ConfigManager.getMessageConfig().getString("reload-message");
                    String prefix = this.plugin.getConfig().getString("prefix");
                    StringBuilder sb = new StringBuilder();
                    sb.append(prefix);
                    sb.append(reloaded);
                    player.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
                } else {
                    String reloaded = ConfigManager.getMessageConfig().getString("reload-message");
                    String prefix = this.plugin.getConfig().getString("prefix");
                    StringBuilder sb = new StringBuilder();
                    sb.append(prefix);
                    sb.append(reloaded);
                    Bukkit.getLogger().info(ColorUtils.translateColorCodes(sb.toString()));
                }
                break;
            case "version":
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    player.sendMessage(ChatColor.GREEN + "This server is running LifenetworkFwiends version 1.0");
                } else {
                    Bukkit.getLogger().info("This server is running LifenetworkFwiends version 1.0");
                }
            default:
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    player.sendMessage(ChatColor.RED + "Usage: /lifenetwork <reload|version>");
                } else {
                    Bukkit.getLogger().severe("Usage: /lifenetwork <reload|version>");
                }
                break;
        }



        return true;
    }
}
