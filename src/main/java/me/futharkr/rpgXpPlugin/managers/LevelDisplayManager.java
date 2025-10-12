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

        // Reuse the player's existing scoreboard to avoid overwriting other plugins' scoreboards
        Scoreboard scoreboard = player.getScoreboard();

        if (scoreboard == null) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        Objective objective = scoreboard.getObjective("playerLevel");

        if (objective == null) {
            objective = scoreboard.registerNewObjective("playerLevel", Criteria.DUMMY, Component.text("lvl"));
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
        // ensure display slot is correct
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);


        // If the player's scoreboard was empty/new, assign it. If another plugin manages it, avoid forcing replacement.
        if (player.getScoreboard() != scoreboard) {
            player.setScoreboard(scoreboard);
        }

        updateLevelDisplay(player);

    }

    public void updateLevelDisplay(Player player) {
        updateLevelDisplay(player, player.getLevel());
    }

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
