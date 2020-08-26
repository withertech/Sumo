package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandLeave extends SubCommand
{
    @Override
    public String getName()
    {
        return "leave";
    }

    @Override
    public String getDescription()
    {
        return "Leaves the player's currently joined arena";
    }

    @Override
    public String getSyntax()
    {
        return "/sumo leave";
    }

    @Override
    public String getPermission()
    {
        return null;
    }

    @Override
    public boolean perform(Player player, String[] args)
    {
        ArenaManager.getManager().removePlayer(player);
        Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "] " + ChatColor.YELLOW + player.getName() + " Has Left The Arena");
        return true;
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        return null;
    }
}
