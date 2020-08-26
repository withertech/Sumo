package com.withertech.sumo;

import com.withertech.sumo.Commands.CommandManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class Sumo extends JavaPlugin
{
    private File arenaConfigFile = null;
    private FileConfiguration arenaConfig = null;

    private File statsConfigFile = null;
    private FileConfiguration statsConfig = null;

    @Override
    public void onEnable()
    {
        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        if(getConfig() == null)
            saveDefaultConfig();

        createArenaConfig();
        createStatsConfig();

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

    public FileConfiguration getArenaConfig(){
        return this.arenaConfig;
    }

    private void createArenaConfig() {
        arenaConfigFile = new File(getDataFolder(), "arenas.yml");
        if (!arenaConfigFile.exists()) {
            arenaConfigFile.getParentFile().mkdirs();
            saveResource("arenas.yml", false);
        }

        arenaConfig = new YamlConfiguration();
        try {
            arenaConfig.load(arenaConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveArenaConfig()
    {
        try {
            getArenaConfig().save(arenaConfigFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + arenaConfigFile, ex);
        }
    }

    public FileConfiguration getStatsConfig(){
        return this.statsConfig;
    }

    private void createStatsConfig() {
        statsConfigFile = new File(getDataFolder(), "stats.yml");
        if (!statsConfigFile.exists()) {
            statsConfigFile.getParentFile().mkdirs();
            saveResource("stats.yml", false);
        }

        statsConfig = new YamlConfiguration();
        try {
            statsConfig.load(statsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveStatsConfig()
    {
        try {
            getStatsConfig().save(statsConfigFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + statsConfigFile, ex);
        }
    }

}
