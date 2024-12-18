package me.ghostedvortex.lifenetscoreboard.Listeners;

import me.ghostedvortex.lifenetscoreboard.LifeNetScoreboard;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

public class onJoin implements Listener {

    private final LifeNetScoreboard plugin;

    public onJoin(LifeNetScoreboard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ScoreboardManager sbManager = Bukkit.getScoreboardManager();
        Scoreboard sb = sbManager.getNewScoreboard();

        String ls = this.plugin.getConfig().getString("LifeSteal");
        String box = this.plugin.getConfig().getString("Box");
        String gens = this.plugin.getConfig().getString("Gens");
        String bw = this.plugin.getConfig().getString("BedWars");
        String ip = this.plugin.getConfig().getString("ip");

        LuckPerms luckPerms = LuckPermsProvider.get();
        UserManager userManager = luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());

        Objective obj = sb.registerNewObjective("Title", "dummy", ChatColor.translateAlternateColorCodes('&', "&x&F&B&8&0&1&3&lʟ&x&F&C&6&B&1&0&lɪ&x&F&C&5&5&0&D&lꜰ&x&F&D&4&0&0&A&lᴇ&x&F&E&2&B&0&6&lɴ&x&F&E&1&5&0&3&lᴇ&x&F&F&0&0&0&0&lᴛ&x&F&F&0&0&0&0&lᴡ&x&F&F&0&0&0&0&lᴏ&x&F&F&0&0&0&0&lʀ&x&F&F&0&0&0&0&lᴋ"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score1 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&d "));
        Score score2 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&4| " + player.getDisplayName()));
        Score score3 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&4|   &6ʀᴀɴᴋ: " + user.getPrimaryGroup()));
        Score score4 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&a "));
        Score score5 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&4| ɢᴀᴍᴇᴍᴏᴅᴇꜱ"));
        Score score10 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&c "));

        if (ls != null) {
            Score score6 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&4|   &cʟɪꜰᴇꜱᴛᴇᴀʟ: " + ls));
            score6.setScore(6);
        } else {
            Score score6 = obj.getScore(ChatColor.DARK_RED + "&4|    &cʟɪꜰᴇꜱᴛᴇᴀʟ: " + ChatColor.RED + "&cError");
            player.sendMessage(ChatColor.RED + "Config Error: ls came back null. Please contact the developer");
            score6.setScore(6);
        }

        if (box != null) {
            Score score7 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&4|   &dʙᴏx: " + box));
            score7.setScore(5);
        } else {
            Score score7 = obj.getScore(ChatColor.DARK_RED + "&4|   " + ChatColor.LIGHT_PURPLE + "ʙᴏx: " + ChatColor.RED + "&cError");
            player.sendMessage(ChatColor.RED + "Config Error: box came back null. Please contact the developer");
            score7.setScore(5);
        }

        if (gens != null) {
            Score score8 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&4|   &aɢᴇɴꜱ: " + gens));
            score8.setScore(4);
        } else {
            Score score8 = obj.getScore(ChatColor.DARK_RED + "&4|   " + ChatColor.GREEN + "ɢᴇɴꜱ: " + ChatColor.RED + "&cError");
            player.sendMessage(ChatColor.RED + "Config Error: gens came back null. Please contact the developer");
            score8.setScore(4);
        }

        if (bw != null) {
            Score score9 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&4|   &bʙᴇᴅ ᴡᴀʀꜱ: " + bw));
            score9.setScore(3);
        } else {
            Score score9 = obj.getScore(ChatColor.DARK_RED + "&4|   " + ChatColor.AQUA + "ʙᴇᴅ ᴡᴀʀꜱ: " + ChatColor.RED + "&cError");
            player.sendMessage(ChatColor.RED + "Config Error: bw came back null. Please contact the developer");
            score9.setScore(3);
        }

        if (ip != null) {
            Score score11 = obj.getScore(ChatColor.translateAlternateColorCodes('&', "     " + ip));
            score11.setScore(1);
        } else {
            Score score11 = obj.getScore(ChatColor.DARK_RED + "&4|   " + ChatColor.DARK_RED + "IP: " + ChatColor.RED + "&cError");
            player.sendMessage(ChatColor.RED + "Config Error: ip came back null. Please contact the developer");
            score11.setScore(1);
        }

        score1.setScore(11);
        score2.setScore(10);
        score3.setScore(9);
        score4.setScore(8);
        score5.setScore(7);
        score10.setScore(2);

        player.setScoreboard(sb);
    }
}
