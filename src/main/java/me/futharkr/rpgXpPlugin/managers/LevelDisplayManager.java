package me.futharkr.rpgXpPlugin.managers;

import me.futharkr.rpgXpPlugin.RpgXpPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import static org.bukkit.Bukkit.getLogger;

public class LevelDisplayManager {

    public void setupLevelDisplay(Player player) {

        // First, check if level display is enabled in the config
        if (!isLevelDisplayEnabled()) {
            Scoreboard scoreboard = player.getScoreboard();
            if (scoreboard != null) {
                Objective objective = scoreboard.getObjective("playerLevel");
                if (objective != null) {
                    objective.unregister();
                }
            }
            return;
        }



        if (isCitizensNPC(player)) {
            return;
        }

        // If the player's level is less than 1, remove the objective and return
        if (checkAndRemoveLevelDisplayIfLevelBelowOne(player)) {
            return;
        }

        // Try to get the player's existing scoreboard, or create a new one if they don't have one
        Scoreboard scoreboard = player.getScoreboard();

        if (scoreboard == null) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        // Try to get the existing objective, or create it if it doesn't exist
        Objective objective = scoreboard.getObjective("playerLevel");

        // Get the color from the config, default to WHITE if not found or invalid
        FileConfiguration config = RpgXpPlugin.getInstance().getConfig();
        String colorName = config.getString("scoreboard-color", "WHITE");

        ChatColor color = ChatColor.WHITE;
        try {
            color = ChatColor.valueOf(colorName);
        } catch (Exception ignored) {}

        if (objective == null) {
            objective = scoreboard.registerNewObjective("playerLevel", Criteria.DUMMY, color + "lvl");
        } else {
            objective.setDisplayName(color + "lvl");
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


        if (isCitizensNPC(player)) {
            return;
        }

        updateLevelDisplay(player, player.getLevel());
    }

    //Don't know if this will continue, maybe this should be deleted
    // and only the updateLevelDisplay(Player) should be used
    public void updateLevelDisplay(Player player, int level) {



        if (isCitizensNPC(player)) {
            return;
        }

        Scoreboard scoreboard = player.getScoreboard();

        Objective objective = scoreboard.getObjective("playerLevel");

        if (objective == null) {
            RpgXpPlugin.getInstance().getLogger().info("[LevelDisplay] objective is null for player " + player.getName());
            return;
        }

        // If the player's level is less than 1, remove the objective and return
        if (checkAndRemoveLevelDisplayIfLevelBelowOne(player)) {
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


    private boolean isLevelDisplayEnabled() {
        FileConfiguration config = RpgXpPlugin.getInstance().getConfig();
        return config.getBoolean("enabled", true);
    }

    public void enableDisableLevelDisplayForAllOnlinePlayers() {
        getLogger().info("Enabling/disabling level display for all online players");
        for (Player player : Bukkit.getOnlinePlayers()) {
            setupLevelDisplay(player);
        }
    }

    private boolean checkIfLevelIsBelowOne(Player player) {

        getLogger().info("[RpgXpPlugin] Checking if level is below one for player " + player.getName() + " with level " + player.getLevel());

        return player.getLevel() < 1 || player.getLevel() == 0;

    }


    private boolean removeLevelDisplay(Player player) {
        try {
            getLogger().info("Removing level display for player " + player.getName());
            Scoreboard scoreboard = player.getScoreboard();
            if (scoreboard == Bukkit.getScoreboardManager().getMainScoreboard()) {
                getLogger().warning("Trying to remove objective from main scoreboard for " + player.getName());
            }

                Objective objective = scoreboard.getObjective("playerLevel");
                if (objective != null) {
                    getLogger().info("Removing objective playerLevel for " + player.getName());
                    objective.unregister();

            } else {
                getLogger().info("Scoreboard is null for " + player.getName());
            }
            return true;
        } catch (Exception ex) {
            getLogger().severe("Failed to remove level display for player " + player.getName() + ": " + ex.getMessage());
            return false;
        }
    }

    private boolean checkAndRemoveLevelDisplayIfLevelBelowOne(Player player) {
        if (checkIfLevelIsBelowOne(player)) {

            getLogger().info("[RpgXpPlugin] Player " + player.getName() + " level is below one, its level is " + player.getLevel() +  " removing level display.");
            return removeLevelDisplay(player);
        }

        return false;
    }

    private boolean isCitizensNPC(Player player) {
        return player.hasMetadata("NPC") || player.hasMetadata("CITIZENS_NPC");
    }
}