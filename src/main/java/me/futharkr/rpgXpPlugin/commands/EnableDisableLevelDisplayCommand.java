package me.futharkr.rpgXpPlugin.commands;

import me.futharkr.rpgXpPlugin.RpgXpPlugin;
import me.futharkr.rpgXpPlugin.managers.LevelDisplayManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;


public class EnableDisableLevelDisplayCommand extends Command {

    ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    Scoreboard mainScoreboard = scoreboardManager.getMainScoreboard();

    private final LevelDisplayManager levelDisplayManager = new LevelDisplayManager(scoreboardManager, mainScoreboard);

    public EnableDisableLevelDisplayCommand() {
        super("rpgxp-enabledisable");
        this.setDescription("enables or disables xp display for players");
        this.setUsage("/rpgxp-enabledisable <enable|disable>");
        this.setPermission("rpgxp.admin");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (args.length < 1){
            commandSender.sendMessage("Correct usage: /rpgxp-enabledisable <enable|disable>");
            return true;

        }

        String action = args[0].toLowerCase();


        if (action.equals("enable")){
            commandSender.sendMessage("Enabling level display");

            FileConfiguration config = RpgXpPlugin.getInstance().getConfig();
            config.set("enabled", true);
            RpgXpPlugin.getInstance().saveConfig();

            levelDisplayManager.enableDisableLevelDisplayForAllOnlinePlayers();

            return true;


        }

        if (action.equals("disable")){
            commandSender.sendMessage("Disabling level display for ");

            FileConfiguration config = RpgXpPlugin.getInstance().getConfig();
            config.set("enabled", false);
            RpgXpPlugin.getInstance().saveConfig();

            levelDisplayManager.enableDisableLevelDisplayForAllOnlinePlayers();

            return true;

        }

        commandSender.sendMessage("Unknown action: " + action + "! Use /rpgxp-enabledisable <enable|disable>");

        return true;

    }
}
