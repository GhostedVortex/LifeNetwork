package me.ghostedvortex.lifenetworkfwiends.Managers;

import me.ghostedvortex.lifenetworkfwiends.LifenetworkFwiends;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendManager {

    private static File playerDataFolder;
    private final LifenetworkFwiends plugin;

    public FriendManager(File dataFolder, LifenetworkFwiends plugin) {
        this.playerDataFolder = new File(dataFolder, "PlayerData");
        this.plugin = plugin;

        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    public static void createFile(UUID playerUUID){
        File file = new File(playerDataFolder, playerUUID.toString() + ".yml");
        if (!file.exists()){
            try{
                file.createNewFile();
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                config.set("Friends", new ArrayList<>());
                config.set("PendingRequests", new ArrayList<>());
                config.set("SentRequests", new ArrayList<>());
                config.set("Blocked", new ArrayList<>());
                config.set("Options", new ArrayList<>());
                config.set("Options.FriendRequests", true);

                config.save(file);

            }catch (IOException e){
                Bukkit.getLogger().severe("Couldn't create player data file for " + playerUUID);
            }
        }
    }


    public void sendRequest(Player sender, UUID senderUUID, Player target, UUID targetUUID){
        File senderFile = new File(playerDataFolder, senderUUID.toString() + ".yml");
        File targetFile = new File(playerDataFolder, targetUUID.toString() + ".yml");

        FileConfiguration senderConfig = YamlConfiguration.loadConfiguration(senderFile);
        FileConfiguration targetConfig = YamlConfiguration.loadConfiguration(targetFile);

        List<String> pendingRequests = targetConfig.getStringList("PendingRequests");
        if (!pendingRequests.contains(sender.getDisplayName())) {
            StringBuilder sb2 = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null){
                sb2.append(prefix);
                sb2.append("&bYou have been sent a friend request by &e" + sender.getDisplayName() + "&b! Use &e/friend accept " + sender.getDisplayName() + "&b to accept it!");
                target.sendMessage(ColorUtils.translateColorCodes(sb2.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }

        } else {
            sender.sendMessage(ChatColor.RED + "You already have a pending friend request to that player!");
            return;
        }
        List<String> sentRequests = senderConfig.getStringList("SentRequests");
        if (!sentRequests.contains(target.getDisplayName())) {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null){
                sb.append(prefix);
                sb.append("&bYou sent a friend request to &e" + target.getDisplayName() + "&b!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You already have a pending friend request to that player!");
            return;
        }
        try {
            pendingRequests.add(sender.getDisplayName());
            targetConfig.set("PendingRequests", pendingRequests);
            sentRequests.add(target.getDisplayName());
            senderConfig.set("SentRequests", sentRequests);
            targetConfig.save(targetFile);
            senderConfig.save(senderFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save player data files: " + e.getMessage());
        }
    }
    public void sendOfflineRequest(Player sender, UUID senderUUID, String target, UUID targetUUID){
        File senderFile = new File(playerDataFolder, senderUUID.toString() + ".yml");
        File targetFile = new File(playerDataFolder, targetUUID.toString() + ".yml");

        FileConfiguration senderConfig = YamlConfiguration.loadConfiguration(senderFile);
        FileConfiguration targetConfig = YamlConfiguration.loadConfiguration(targetFile);

        List<String> pendingRequests = targetConfig.getStringList("PendingRequests");
        List<String> blockedPlayers = targetConfig.getStringList("Blocked");
        if (!pendingRequests.contains(sender.getDisplayName())) {
            if (!blockedPlayers.contains(sender.getDisplayName())) {
            } else {
                StringBuilder sb = new StringBuilder();
                String prefix = this.plugin.getConfig().getString("prefix");
                if (prefix != null){
                    sb.append(prefix);
                    sb.append("&e" + target + "&b has you blocked!");
                    sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
                } else {
                    Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You are already friends with that player!");
            return;
        }
        List<String> sentRequests = senderConfig.getStringList("SentRequests");
        if (!sentRequests.contains(target)) {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null){
                sb.append(prefix);
                sb.append("&bYou sent a friend request to &e" + target);
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You already have a pending friend request sent to that player!");
            return;
        }
        try {
            sentRequests.add(target);
            senderConfig.set("SentRequests", sentRequests);
            pendingRequests.add(sender.getDisplayName());
            targetConfig.set("PendingRequests", pendingRequests);
            senderConfig.save(senderFile);
            targetConfig.save(targetFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save player data files: " + e.getMessage());
        }
    }
    public void removeFriend(Player sender, UUID senderUUID, String target, UUID targetUUID){
        File senderFile = new File(playerDataFolder, senderUUID.toString() + ".yml");
        File targetFile = new File(playerDataFolder, targetUUID.toString() + ".yml");

        FileConfiguration senderConfig = YamlConfiguration.loadConfiguration(senderFile);
        FileConfiguration targetConfig = YamlConfiguration.loadConfiguration(targetFile);

        List<String> senderFriends = senderConfig.getStringList("Friends");
        List<String> targetFriends = targetConfig.getStringList("Friends");
        if (senderFriends.contains(target)){
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null){
                sb.append(prefix);
                sb.append("&bYou have successfully removed &e" + target + "&b as a friend!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null) {
                sb.append(prefix);
                sb.append("&bYou are not friends with &e" + target + "&b!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            }
        }
        try {
            senderFriends.remove(target);
            targetFriends.remove(sender.getDisplayName());
            targetConfig.set("Friends", targetFriends);
            senderConfig.set("Friends", senderFriends);
            senderConfig.save(senderFile);
            targetConfig.save(targetFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save player data files: " + e.getMessage());
        }
    }
    public void acceptRequest(Player sender, UUID senderUUID, Player target, UUID targetUUID){
        File senderFile = new File(playerDataFolder, senderUUID.toString() + ".yml");
        File targetFile = new File(playerDataFolder, targetUUID.toString() + ".yml");

        FileConfiguration senderConfig = YamlConfiguration.loadConfiguration(senderFile);
        FileConfiguration targetConfig = YamlConfiguration.loadConfiguration(targetFile);

        List<String> sentRequests = targetConfig.getStringList("SentRequests");
        List<String> pendingRequests = senderConfig.getStringList("PendingRequests");
        List<String> senderFriends = senderConfig.getStringList("Friends");
        List<String> targetFriends = targetConfig.getStringList("Friends");
        if (pendingRequests.contains(target.getDisplayName())){
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null){
                sb.append(prefix);
                sb.append("&bYou have accepted &e" + target.getDisplayName() + "&b's friend request!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
                sb.append(prefix);
                sb.append("&e" + sender.getDisplayName() + " &bhas accepted your friend request!");
                target.sendMessage(ColorUtils.translateColorCodes(sb2.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null){
                sb.append(prefix);
                sb.append("&cYou do not have a friend request from " + target.getDisplayName() + "!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
                return;
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        }
        try {
            sentRequests.remove(sender.getDisplayName());
            pendingRequests.remove(target.getDisplayName());
            senderFriends.add(target.getDisplayName());
            targetFriends.add(sender.getDisplayName());
            targetConfig.set("Friends", targetFriends);
            senderConfig.set("Friends", senderFriends);
            targetConfig.set("SentRequests", sentRequests);
            senderConfig.set("PendingRequests", pendingRequests);
            senderConfig.save(senderFile);
            targetConfig.save(targetFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save player data files: " + e.getMessage());
        }
    }
    public void acceptOfflineRequest(Player sender, UUID senderUUID, String target, UUID targetUUID){
        File senderFile = new File(playerDataFolder, senderUUID.toString() + ".yml");
        File targetFile = new File(playerDataFolder, targetUUID.toString() + ".yml");

        FileConfiguration senderConfig = YamlConfiguration.loadConfiguration(senderFile);
        FileConfiguration targetConfig = YamlConfiguration.loadConfiguration(targetFile);

        List<String> sentRequests = targetConfig.getStringList("SentRequests");
        List<String> pendingRequests = senderConfig.getStringList("PendingRequests");
        List<String> senderFriends = senderConfig.getStringList("Friends");
        List<String> targetFriends = targetConfig.getStringList("Friends");
        if (pendingRequests.contains(target)){
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null){
                sb.append(prefix);
                sb.append("&bYou have accepted &e" + target + "&b's friend request!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null){
                sb.append(prefix);
                sb.append("&cYou do not have a friend request from " + target + "!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
                return;
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        }
        try {
            sentRequests.remove(sender.getDisplayName());
            pendingRequests.remove(target);
            senderFriends.add(target);
            targetFriends.add(sender.getDisplayName());
            targetConfig.set("Friends", targetFriends);
            senderConfig.set("Friends", senderFriends);
            targetConfig.set("SentRequests", sentRequests);
            senderConfig.set("PendingRequests", pendingRequests);
            senderConfig.save(senderFile);
            targetConfig.save(targetFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save player data files: " + e.getMessage());
        }
    }
    public void denyRequest(Player sender, UUID senderUUID, String target, UUID targetUUID){
        File senderFile = new File(playerDataFolder, senderUUID.toString() + ".yml");
        File targetFile = new File(playerDataFolder, targetUUID.toString() + ".yml");

        FileConfiguration senderConfig = YamlConfiguration.loadConfiguration(senderFile);
        FileConfiguration targetConfig = YamlConfiguration.loadConfiguration(targetFile);

        List<String> sentRequests = targetConfig.getStringList("SentRequests");
        List<String> pendingRequests = senderConfig.getStringList("PendingRequests");
        if (pendingRequests.contains(target)){
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null){
                sb.append(prefix);
                sb.append("&bYou have denied &e" + target + "&b's request!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null){
                sb.append(prefix);
                sb.append("&cYou do not have a friend request from " + target + "!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
                return;
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        }
        try {
            sentRequests.remove(sender.getDisplayName());
            pendingRequests.remove(target);
            targetConfig.set("SentRequests", sentRequests);
            senderConfig.set("PendingRequests", pendingRequests);
            senderConfig.save(senderFile);
            targetConfig.save(targetFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save player data files: " + e.getMessage());
        }
    }
    public void blockPlayer(Player sender, UUID senderUUID, Player target, UUID targetUUID){
        File senderFile = new File(playerDataFolder, senderUUID.toString() + ".yml");
        File targetFile = new File(playerDataFolder, targetUUID.toString() + ".yml");

        FileConfiguration senderConfig = YamlConfiguration.loadConfiguration(senderFile);
        FileConfiguration targetConfig = YamlConfiguration.loadConfiguration(targetFile);
        List<String> blockedUsers = senderConfig.getStringList("Blocked");
        List<String> senderFriends = senderConfig.getStringList("Friends");
        List<String> targetFriends = targetConfig.getStringList("Friends");
        if (!blockedUsers.contains(target.getDisplayName())){
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null) {
                sb.append(prefix);
                sb.append("&bYou have successfully blocked " + target.getDisplayName() + "!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null) {
                sb.append(prefix);
                sb.append("&e" + target.getDisplayName() + " &bis already blocked!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        }
        try {
            if (senderFriends.contains(target.getDisplayName())){
                senderFriends.remove(target.getDisplayName());
                targetFriends.remove(sender.getDisplayName());
                targetConfig.set("Friends", targetFriends);
                senderConfig.set("Friends", senderFriends);
            }
            blockedUsers.add(target.getDisplayName());
            senderConfig.set("Blocked", blockedUsers);
            senderConfig.save(senderFile);
            targetConfig.save(targetFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save player data files: " + e.getMessage());
        }
    }
    public void blockOfflinePlayer(Player sender, UUID senderUUID, String target, UUID targetUUID){
        File senderFile = new File(playerDataFolder, senderUUID.toString() + ".yml");
        File targetFile = new File(playerDataFolder, targetUUID.toString() + ".yml");

        FileConfiguration senderConfig = YamlConfiguration.loadConfiguration(senderFile);
        FileConfiguration targetConfig = YamlConfiguration.loadConfiguration(targetFile);
        List<String> blockedUsers = senderConfig.getStringList("Blocked");
        List<String> senderFriends = senderConfig.getStringList("Friends");
        List<String> targetFriends = targetConfig.getStringList("Friends");
        if (!blockedUsers.contains(target)){
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null) {
                sb.append(prefix);
                sb.append("&bYou have successfully blocked " + target + "!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null) {
                sb.append(prefix);
                sb.append("&e" + target + " &bis already blocked!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        }
        try {
            if (senderFriends.contains(target)){
                senderFriends.remove(target);
                targetFriends.remove(sender.getDisplayName());
                targetConfig.set("Friends", targetFriends);
                senderConfig.set("Friends", senderFriends);
            }
            blockedUsers.add(target);
            senderConfig.set("Blocked", blockedUsers);
            senderConfig.save(senderFile);
            targetConfig.save(targetFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save player data files: " + e.getMessage());
        }
    }
    public void unblockPlayer(Player sender, UUID senderUUID, Player target, UUID targetUUID){
        File senderFile = new File(playerDataFolder, senderUUID.toString() + ".yml");

        FileConfiguration senderConfig = YamlConfiguration.loadConfiguration(senderFile);
        List<String> blockedUsers = senderConfig.getStringList("Blocked");
        if (blockedUsers.contains(target.getDisplayName())){
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null) {
                sb.append(prefix);
                sb.append("&bYou have successfully unblocked &e" + target.getDisplayName() + "&b!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null) {
                sb.append(prefix);
                sb.append("&e" + target.getDisplayName() + " &bis not blocked!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        }
        try {
            blockedUsers.remove(target.getDisplayName());
            senderConfig.set("Blocked", blockedUsers);
            senderConfig.save(senderFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save player data files: " + e.getMessage());
        }
    }
    public void unblockOfflinePlayer(Player sender, UUID senderUUID, String target, UUID targetUUID) {
        File senderFile = new File(playerDataFolder, senderUUID.toString() + ".yml");

        FileConfiguration senderConfig = YamlConfiguration.loadConfiguration(senderFile);
        List<String> blockedUsers = senderConfig.getStringList("Blocked");
        if (blockedUsers.contains(target)) {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null) {
                sb.append(prefix);
                sb.append("&bYou have successfully unblocked &e" + target + "&b!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            String prefix = this.plugin.getConfig().getString("prefix");
            if (prefix != null) {
                sb.append(prefix);
                sb.append("&e" + target + " &bis not blocked!");
                sender.sendMessage(ColorUtils.translateColorCodes(sb.toString()));
            } else {
                Bukkit.getLogger().severe("[ERROR] prefix came back null! This is most likely the developers fault, if you changed the path name in the config.yml please change it back to prefix: and reload the plugin.");
            }
        }
        try {
            blockedUsers.remove(target);
            senderConfig.set("Blocked", blockedUsers);
            senderConfig.save(senderFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save player data files: " + e.getMessage());
        }
    }
}
