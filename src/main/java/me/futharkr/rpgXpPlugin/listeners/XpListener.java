package me.futharkr.rpgXpPlugin.listeners;

import me.futharkr.rpgXpPlugin.managers.LevelDisplayManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class XpListener implements Listener {

    private final LevelDisplayManager levelDisplayManager;

    public XpListener() {

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

        Scoreboard mainScoreboard = scoreboardManager.getMainScoreboard();

        levelDisplayManager = new LevelDisplayManager(scoreboardManager, mainScoreboard);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        levelDisplayManager.setupPlayerLevelDisplay(player);
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
