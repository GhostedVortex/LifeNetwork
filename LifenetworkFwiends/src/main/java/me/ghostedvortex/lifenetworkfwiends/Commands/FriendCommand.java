package me.ghostedvortex.lifenetworkfwiends.Commands;

import me.ghostedvortex.lifenetworkfwiends.LifenetworkFwiends;
import me.ghostedvortex.lifenetworkfwiends.Managers.FriendManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FriendCommand implements CommandExecutor {

    private final LifenetworkFwiends plugin;
    private final FriendManager friendManager;

    public FriendCommand(LifenetworkFwiends plugin, FriendManager friendManager) {
        this.plugin = plugin;
        this.friendManager = friendManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();
            StringBuilder sb = new StringBuilder();
            String subCommand = args[0];
            if (args.length > 0) {
                switch (subCommand) {
                    case "add":
                        if (sender instanceof Player) {
                            String targetName = args[1];
                            Player target = Bukkit.getPlayer(targetName);

                            if (target != null) {
                                UUID targetUUID = target.getUniqueId();
                                friendManager.sendRequest(player, player.getUniqueId(), target, targetUUID);
                            } else {
                                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetName);
                                UUID targetUUID = offlineTarget.getUniqueId();

                                if (offlineTarget.hasPlayedBefore()) {
                                    friendManager.sendOfflineRequest(player, player.getUniqueId(), Bukkit.getPlayer(targetUUID).getDisplayName(), targetUUID);
                                } else {
                                    FriendManager.createFile(targetUUID);
                                    friendManager.sendOfflineRequest(player, player.getUniqueId(), args[1], targetUUID);
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "This command is only usable by players!");
                        }
                        break;
                    case "remove":
                        if (sender instanceof Player) {
                            String targetName = args[1];
                            Player target = Bukkit.getPlayer(targetName);

                            if (target != null) {
                                UUID targetUUID = target.getUniqueId();
                                friendManager.removeFriend(player, player.getUniqueId(), target.getDisplayName(), targetUUID);
                            } else {
                                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetName);
                                UUID targetUUID = offlineTarget.getUniqueId();

                                if (offlineTarget.hasPlayedBefore()) {
                                    friendManager.removeFriend(player, player.getUniqueId(), Bukkit.getPlayer(targetUUID).getDisplayName(), targetUUID);
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "This command is only usable by players!");
                        }
                        break;
                    case "block":
                        if (sender instanceof Player) {
                            String targetName = args[1];
                            Player target = Bukkit.getPlayer(targetName);

                            if (target != null) {
                                UUID targetUUID = target.getUniqueId();
                                friendManager.blockPlayer(player, player.getUniqueId(), target, targetUUID);
                            } else {
                                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetName);
                                UUID targetUUID = offlineTarget.getUniqueId();

                                if (offlineTarget.hasPlayedBefore()) {
                                    friendManager.blockOfflinePlayer(player, player.getUniqueId(), Bukkit.getPlayer(targetUUID).getDisplayName(), targetUUID);
                                } else {
                                    FriendManager.createFile(targetUUID);
                                    friendManager.blockOfflinePlayer(player, player.getUniqueId(), Bukkit.getPlayer(targetUUID).getDisplayName(), targetUUID);
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "This command is only usable by players!");
                        }
                        break;
                    case "unblock":
                        if (sender instanceof Player) {
                            String targetName = args[1];
                            Player target = Bukkit.getPlayer(targetName);

                            if (target != null) {
                                UUID targetUUID = target.getUniqueId();
                                friendManager.unblockPlayer(player, player.getUniqueId(), target, targetUUID);
                            } else {
                                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetName);
                                UUID targetUUID = offlineTarget.getUniqueId();

                                if (offlineTarget.hasPlayedBefore()) {
                                    friendManager.unblockOfflinePlayer(player, player.getUniqueId(), Bukkit.getPlayer(targetUUID).getDisplayName(), targetUUID);
                                } else {
                                    friendManager.unblockOfflinePlayer(player, player.getUniqueId(), args[1], targetUUID);
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "This command is only usable by players!");
                        }
                        break;
                    case "accept":
                        if (sender instanceof Player) {
                            String targetName = args[1];
                            Player target = Bukkit.getPlayer(targetName);

                            if (target != null) {
                                UUID targetUUID = target.getUniqueId();
                                friendManager.acceptRequest(player, player.getUniqueId(), target, targetUUID);
                            } else {
                                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetName);
                                UUID targetUUID = offlineTarget.getUniqueId();

                                if (offlineTarget.hasPlayedBefore()) {
                                    friendManager.acceptOfflineRequest(player, player.getUniqueId(), Bukkit.getPlayer(targetUUID).getDisplayName(), targetUUID);
                                } else {
                                    FriendManager.createFile(targetUUID);
                                    friendManager.acceptOfflineRequest(player, player.getUniqueId(), args[1], targetUUID);
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "This command is only usable by players!");
                        }
                        break;
                    case "deny":
                        if (sender instanceof Player) {
                            String targetName = args[1];
                            Player target = Bukkit.getPlayer(targetName);

                            if (target != null) {
                                UUID targetUUID = target.getUniqueId();
                                friendManager.denyRequest(player, player.getUniqueId(), target.getDisplayName(), targetUUID);
                            } else {
                                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetName);
                                UUID targetUUID = offlineTarget.getUniqueId();

                                if (offlineTarget.hasPlayedBefore()) {
                                    friendManager.denyRequest(player, player.getUniqueId(), Bukkit.getPlayer(targetUUID).getDisplayName(), targetUUID);
                                }
                            }
                        } else {
                            Bukkit.getLogger().severe(ChatColor.RED + "This command is only usable by players!");
                        }
                        break;
                    default:
                        player.sendMessage(ChatColor.RED + "Usage: /friend <add|remove|block|unblock|accept|deny> <player>");
                        break;
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /friend <add|remove|block|unblock|accept|deny> <player>");
            }
        }





        return true;
    }
}
