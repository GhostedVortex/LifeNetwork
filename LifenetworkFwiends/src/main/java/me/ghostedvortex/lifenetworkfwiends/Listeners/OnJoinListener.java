package me.ghostedvortex.lifenetworkfwiends.Listeners;

import me.ghostedvortex.lifenetworkfwiends.Managers.FriendManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class OnJoinListener implements Listener {

    private final FriendManager friendManager;

    public OnJoinListener(FriendManager friendManager) {
        this.friendManager = friendManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        FriendManager.createFile(playerUUID);

    }
}
