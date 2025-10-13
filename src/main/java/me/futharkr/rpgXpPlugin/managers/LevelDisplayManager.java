package me.futharkr.rpgXpPlugin.managers;

import me.futharkr.rpgXpPlugin.RpgXpPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class LevelDisplayManager {

    public void setupLevelDisplay(Player player) {

        // Try to get the player's existing scoreboard, or create a new one if they don't have one
        Scoreboard scoreboard = player.getScoreboard();

        if (scoreboard == null) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        // Try to get the existing objective, or create it if it doesn't exist
        Objective objective = scoreboard.getObjective("playerLevel");

        if (objective == null) {
            objective = scoreboard.registerNewObjective("playerLevel", Criteria.DUMMY, Component.text("lvl"));

        }
        // Set the display slot to below the player's name
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);


        // Assign the scoreboard to the player if they don't already have it
        if (player.getScoreboard() != scoreboard) {
            player.setScoreboard(scoreboard);
        }

        updateLevelDisplay(player);

    }

    public void updateLevelDisplay(Player player) {
        updateLevelDisplay(player, player.getLevel());
    }

    //Don't know if this will continuo, maybe this should be deleted
    // and only the updateLevelDisplay(Player) should be used
    public void updateLevelDisplay(Player player, int level) {
        Scoreboard scoreboard = player.getScoreboard();

        Objective objective = scoreboard.getObjective("playerLevel");

        if (objective == null) {
            RpgXpPlugin.getInstance().getLogger().info("[LevelDisplay] objective is null for player " + player.getName());
            return;
        }

        try {
            Score score = objective.getScore(player.getName());

//            RpgXpPlugin.getInstance().getLogger().info("[LevelDisplay] before set: player=" + player.getName() + " scoreboardHash=" + scoreboard.hashCode() + " objectiveSlot=" + objective.getDisplaySlot() + " currentScore=" + score.getScore());

            score.setScore(level);

//            RpgXpPlugin.getInstance().getLogger().info("[LevelDisplay] after set: player=" + player.getName() + " newScore=" + objective.getScore(player.getName()).getScore());
        } catch (Exception e) {
            RpgXpPlugin.getInstance().getLogger().severe("[LevelDisplay] failed to set score for " + player.getName() + ": " + e.getMessage());
        }
    }
}
