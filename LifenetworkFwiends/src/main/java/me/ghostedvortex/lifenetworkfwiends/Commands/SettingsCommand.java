package me.ghostedvortex.lifenetworkfwiends.Commands;

import me.ghostedvortex.lifenetworkfwiends.Managers.ConfigManager;
import me.ghostedvortex.lifenetworkfwiends.Managers.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class SettingsCommand implements CommandExecutor {

    private final SettingsManager settingsManager;
    private final ConfigManager configManager;

    public SettingsCommand(SettingsManager settingsManager, ConfigManager configManager) {
        this.settingsManager = settingsManager;
        this.configManager = configManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player) {
            Player player = (Player) sender;
            boolean frSetting = ConfigManager.getPlayerConfig(player.getUniqueId()).getBoolean("Options.FriendRequests");
            int size = 9;
            Inventory friendsInventory = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', "&b&lSettings"));
            ItemStack friendRequests = new ItemStack(Material.NAME_TAG, 1);
            ItemMeta frMeta = friendRequests.getItemMeta();
            if (frSetting){
                frMeta.setDisplayName(ChatColor.AQUA + "Friend Requests");
            } else if (!frSetting){
                frMeta.setDisplayName(ChatColor.RED + "Friend Requests");
            } else {
                frMeta.setDisplayName(ChatColor.YELLOW + "Friend Requests");
            }
            friendRequests.setItemMeta(frMeta);
            friendsInventory.setItem(0, friendRequests);

            player.openInventory(friendsInventory);

        }
        return true;
    }

}
