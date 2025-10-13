package me.futharkr.rpgXpPlugin.commands;

import me.futharkr.rpgXpPlugin.RpgXpPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class ChangeColorDisplayCommand extends Command {

    public ChangeColorDisplayCommand() {
        super("rpgxp-changecolor");
        this.setDescription("Changes the color of the xp display");
        this.setUsage("/rpgxp-changecolor changecolor <color>");
        this.setPermission("rpgxp.admin");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender,
                             @NotNull String label,
                             @NotNull String[] args) {


        if (args.length < 2){
            commandSender.sendMessage("Correct usage: /rpgxp changecolor <color>");
            return true;
        }
        String colorName = args[1].toUpperCase();
        ChatColor color;
        try {
            color = ChatColor.valueOf(colorName);
        } catch (IllegalArgumentException e) {
            commandSender.sendMessage("Invalid color: " + colorName + " Use valid minecraft color names.");
            return true;
        }
        // Saves the configuration
        FileConfiguration config = RpgXpPlugin.getInstance().getConfig();
        config.set("scoreboard-color", color.name());
        RpgXpPlugin.getInstance().saveConfig();
        int changed = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = player.getScoreboard();
            Objective objective = scoreboard.getObjective("playerLevel");
            if (objective != null) {
                objective.setDisplayName(color + "lvl");
                changed++;
            }
        }
        commandSender.sendMessage("Changed color to " + color + color.name() + ChatColor.RESET + " for " + changed + " players.");
        return true;
    }




}
