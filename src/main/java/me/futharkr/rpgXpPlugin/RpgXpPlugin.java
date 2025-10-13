package me.futharkr.rpgXpPlugin;

import me.futharkr.rpgXpPlugin.listeners.XpListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RpgXpPlugin extends JavaPlugin {

    private static RpgXpPlugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // this is the instance of the plugin
        instance = this;

        getLogger().info("RPG XP Plugin has been enabled!");

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
