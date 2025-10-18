package me.futharkr.rpgXpPlugin.commands;

import me.futharkr.rpgXpPlugin.RpgXpPlugin;
import me.futharkr.rpgXpPlugin.managers.LevelDisplayManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
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
        try {

            if (args.length < 2) {
                commandSender.sendMessage("Correct usage: /rpgxp-changecolor changecolor <color>");
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
            // Salva a configuração
            FileConfiguration config = RpgXpPlugin.getInstance().getConfig();
            config.set("scoreboard-color", color.name());
            RpgXpPlugin.getInstance().saveConfig();
            int changed = 0;

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (LevelDisplayManager.isCitizensNPC(player)) continue;
                Scoreboard scoreboard = player.getScoreboard();
                if (scoreboard == null) continue;
                Objective objective = scoreboard.getObjective("playerLevel");
                if (objective != null) {
                    objective.setDisplayName(color + "lvl");
                    changed++;
                }
            }

            commandSender.sendMessage("Changed color to " + color + color.name() + ChatColor.RESET + " for " + changed + " players.");
            return true;

        } catch (Throwable t) {
            RpgXpPlugin.getInstance().getLogger().severe("[ChangeColorDisplayCommand] error: " + t.getMessage());
            t.printStackTrace();
            commandSender.sendMessage("Ocorreu um erro ao executar o comando. Veja o console para detalhes.");
            return true;
        }
    }
}