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

public class LevelDisplayManager {

    public void setupLevelDisplay(Player player) {

        // Check if the player is an NPC (Citizens plugin). If it is, return immediately.
        if (isCitizensNPC(player)) return;

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

        if (isCitizensNPC(player)) return;

        updateLevelDisplay(player, player.getLevel());
    }

    //Don't know if this will continuo, maybe this should be deleted
    // and only the updateLevelDisplay(Player) should be used
    public void updateLevelDisplay(Player player, int level) {

        boolean isCitizensNPC = player.hasMetadata("NPC");

        if (isCitizensNPC) {
            return;
        }

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

    public void disableLevelDisplay(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        Objective objective = scoreboard.getObjective("playerLevel");

        if (objective != null) {
            objective.unregister();


        }

    }

    // Helper to detect if a Player is a Citizens NPC without statically depending on Citizens API
    private boolean isCitizensNPC(Player player) {
        // Metadata check first (Citizens usually sets this)
        if (player.hasMetadata("NPC")) return true;

        if (!Bukkit.getPluginManager().isPluginEnabled("Citizens")) return false;

        try {
            // Use reflection to avoid NoClassDefFoundError when Citizens API is absent
            Class<?> citizensApi = Class.forName("net.citizensnpcs.api.CitizensAPI");
            java.lang.reflect.Method getNPCRegistry = citizensApi.getMethod("getNPCRegistry");
            Object registry = getNPCRegistry.invoke(null);
            java.lang.reflect.Method isNPC = registry.getClass().getMethod("isNPC", org.bukkit.entity.Entity.class);
            return (boolean) isNPC.invoke(registry, player);
        } catch (ClassNotFoundException e) {
            return false;
        } catch (Throwable t) {
            RpgXpPlugin.getInstance().getLogger().warning("[LevelDisplay] failed to check Citizens API: " + t.getMessage());
            return false;
        }
    }

}
