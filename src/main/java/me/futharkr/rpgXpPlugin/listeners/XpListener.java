package me.futharkr.rpgXpPlugin.listeners;

import me.futharkr.rpgXpPlugin.RpgXpPlugin;
import me.futharkr.rpgXpPlugin.managers.LevelDisplayManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.Bukkit;

public class XpListener implements Listener {

    private final LevelDisplayManager levelDisplayManager = new LevelDisplayManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        levelDisplayManager.setupLevelDisplay(player);
    }

    @EventHandler
    public void onPlayerGainXp(PlayerExpChangeEvent event) {

        Player player = event.getPlayer();

        levelDisplayManager.updateLevelDisplay(player);

    }

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {

        Player player = event.getPlayer();

        levelDisplayManager.updateLevelDisplay(player);

    }
}
