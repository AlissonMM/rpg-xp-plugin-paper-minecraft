package me.futharkr.rpgXpPlugin.commands;

import me.futharkr.rpgXpPlugin.managers.LevelDisplayManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class EnableDisableSingleEntityCommand extends Command {

    private final LevelDisplayManager levelDisplayManager = new LevelDisplayManager();

    public EnableDisableSingleEntityCommand() {
        super("rpgxp-enabledisable");
        this.setDescription("enables or disables xp display for players");
        this.setUsage("/rpgxp-enabledisable <enable|disable> <nickname|id>");
        this.setPermission("rpgxp.admin");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (args.length < 2){
            commandSender.sendMessage("Correct usage: /rpgxp <enable|disable> <nickname|id>");
            return true;

        }

        String action = args[0].toLowerCase();
        String target = args[1];

        Player targetPlayer = Bukkit.getPlayer(target);

        if (targetPlayer == null) {
            commandSender.sendMessage("Player not found: " + target);
            return true;
        }

        if (action.equals("enable")){
            commandSender.sendMessage("Enabling level display for " + targetPlayer.getName());

            levelDisplayManager.setupLevelDisplay(targetPlayer);

            return true;


        }

        if (action.equals("disable")){
            commandSender.sendMessage("Disabling level display for " + targetPlayer.getName());

            levelDisplayManager.disableLevelDisplay(targetPlayer);

            return true;

        }

        commandSender.sendMessage("Unknown action: " + action + "! Use /rpgxp <enable|disable> <nick|id>");

        return true;

    }
}
