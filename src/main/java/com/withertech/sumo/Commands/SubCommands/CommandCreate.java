package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandCreate extends SubCommand
{
    @Override
    public String getName()
    {
        return "create";
    }

    @Override
    public String getDescription()
    {
        return "Creates a new arena with all \n   locations defaulting to the player's current location";
    }

    @Override
    public String getSyntax()
    {
        return "/sumo create <name>";
    }

    @Override
    public String getPermission()
    {
        return "sumo.admin";
    }

    @Override
    public boolean perform(Player player, String[] args)
    {
        String name = null;
        try
        {
            name = args[1];
        } catch (ArrayIndexOutOfBoundsException e)
        {
            return false;
        }
        ArenaManager.getManager().createArena(player.getLocation(), player.getLocation(), player.getLocation(), name);
        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "] " + ChatColor.AQUA + "Created arena at: \nWorld: " + player.getLocation().getWorld().getName() + "\nX: " + player.getLocation().getBlockX() + "\nY: " + player.getLocation().getBlockY() + "\nZ: " + player.getLocation().getBlockZ() + "\nYaw: " + player.getLocation().getYaw() + "\nPitch: " + player.getLocation().getPitch());
        return true;
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        return null;
    }
}
