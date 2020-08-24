package com.withertech.sumo;

import com.withertech.sumo.Commands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Sumo extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        if(getConfig() == null)
            saveDefaultConfig();

        new ArenaManager(this);
        ArenaManager.getManager().loadGames();

        getServer().getPluginManager().registerEvents(new GameListener(this), this);
        // Plugin startup logic
        this.getCommand("sumo").setExecutor(new CommandManager());
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }
}
