package me.futharkr.rpgXpPlugin.managers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class LevelDisplayManager {

    public void setupLevelDisplay(Player player) {

        // Use a new scoreboard per player so the objective is present on the player's scoreboard
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.getObjective("playerLevel");

        if (objective == null) {
            // use Component-based overload to avoid deprecated method
            objective = scoreboard.registerNewObjective("playerLevel", Criteria.DUMMY, Component.text("lvl"));
        }

        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);

        // assign the created scoreboard to the player
        player.setScoreboard(scoreboard);

        updateLevelDisplay(player);

    }

    public void updateLevelDisplay(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        Objective objective = scoreboard.getObjective("playerLevel");

        if (objective == null) {
            return;
        }

        objective.getScore(player.getName()).setScore(player.getLevel());
    }
}
