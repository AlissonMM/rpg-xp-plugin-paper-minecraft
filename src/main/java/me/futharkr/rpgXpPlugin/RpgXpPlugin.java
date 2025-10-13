package me.futharkr.rpgXpPlugin;

import me.futharkr.rpgXpPlugin.commands.ChangeColorDisplayCommand;
import me.futharkr.rpgXpPlugin.commands.EnableDisableSingleEntityCommand;
import me.futharkr.rpgXpPlugin.listeners.XpListener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public final class RpgXpPlugin extends JavaPlugin {

    private static RpgXpPlugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // this is the instance of the plugin
        instance = this;

        getLogger().info("RPG XP Plugin has been enabled!");

        // creates the permission to access the commands
        getServer().getPluginManager().addPermission(new Permission("rpgxp.admin"));

        // When the command `/rpgxp` is executed in the game it will be handled by the class
        // EnableDisableSingleEntityCommand
        this.getServer().getCommandMap().register("enabledisable", new EnableDisableSingleEntityCommand());

        this.getServer().getCommandMap().register("changecolor", new ChangeColorDisplayCommand());

        // register events is used to `tell bukkit` that there are classes
        // which want to listen to events in the game
        getServer().getPluginManager().registerEvents(new XpListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("RPG XP Plugin has been disabled!");
    }

    public static RpgXpPlugin getInstance() {
        return instance;
    }
}
