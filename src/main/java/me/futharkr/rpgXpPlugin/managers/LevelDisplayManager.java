package me.futharkr.rpgXpPlugin.managers;

import me.futharkr.rpgXpPlugin.RpgXpPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import static org.bukkit.Bukkit.getLogger;

public class LevelDisplayManager {

    private final ScoreboardManager scoreboardManager;
    private final Scoreboard mainScoreBoard;

    public LevelDisplayManager(ScoreboardManager scoreboardManager, Scoreboard mainScoreBoard) {
        this.scoreboardManager = scoreboardManager;
        this.mainScoreBoard = mainScoreBoard;



    }

    public void setupPlayerLevelDisplay(Player player) {




        // First, check if level display is enabled in the config
        if (!isLevelDisplayEnabled()) {
            removeLevelDisplay(player);
            return;
        }



        if (isCitizensNPC(player)) {
            removeLevelDisplay(player);
            return;
        }

        // If the player's level is less than 1, remove the objective and return
        if (checkAndRemoveLevelDisplayIfLevelBelowOne(player)) {
            return;
        }


        player.setScoreboard(mainScoreBoard);

        Objective objective = mainScoreBoard.getObjective("playerLevel");

        if (objective == null) {
            objective = mainScoreBoard.registerNewObjective("playerLevel", Criteria.DUMMY, ChatColor.WHITE + "lvl");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }

        updateLevelDisplay(player);

    }

    public void updateLevelDisplay(Player player) {


        if (isCitizensNPC(player)) {
            removeLevelDisplay(player);
            return;
        }

        updateLevelDisplay(player, player.getLevel());
    }

    //Don't know if this will continue, maybe this should be deleted
    // and only the updateLevelDisplay(Player) should be used
    public void updateLevelDisplay(Player player, int level) {



        if (isCitizensNPC(player)) {
            removeLevelDisplay(player);
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
            removeLevelDisplay(player);
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
            setupPlayerLevelDisplay(player);
        }
    }

    private boolean checkIfLevelIsBelowOne(Player player) {

        getLogger().info("[RpgXpPlugin] Checking if level is below one for player " + player.getName() + " with level " + player.getLevel());

        return player.getLevel() < 1 || player.getLevel() == 0;

    }


    private boolean removeLevelDisplay(Player player) {

            getLogger().info("Removing level display for player " + player.getName());

            try {
                Scoreboard scoreboard = player.getScoreboard();

                if (scoreboard != mainScoreBoard) {
                    getLogger().info("[RpgXpPlugin] scoreboard is different from mainsScoreboard for player " + player.getName());
                    return true;
                }

                Scoreboard emptyScoreboard = scoreboardManager.getNewScoreboard();
                emptyScoreboard.getObjectives().clear();

                player.setScoreboard(emptyScoreboard);

                emptyScoreboard.getObjectives().clear();

                getLogger().info("[RpgXpPlugin] Main Scoreboard has been removed for Player " + player.getName());
                return true;
            }
            catch (Exception exception) {
                getLogger().warning("[RpgXpPlugin] An error has occurred when removing Main Scoreboard from player "
                        + player.getName() + " Error:" + exception.getMessage());
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