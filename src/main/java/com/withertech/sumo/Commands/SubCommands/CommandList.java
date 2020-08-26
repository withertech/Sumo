package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandList extends SubCommand
{
    @Override
    public String getName()
    {
        return "list";
    }

    @Override
    public String getDescription()
    {
        return "Lists all sumo arenas with their ids and names";
    }

    @Override
    public String getSyntax()
    {
        return "/sumo list";
    }

    @Override
    public String getPermission()
    {
        return null;
    }

    @Override
    public boolean perform(Player player, String[] args)
    {
        player.sendMessage(" ");
        player.sendMessage(ChatColor.GREEN + "======= " + ChatColor.GRAY + "[" + ChatColor.AQUA + ChatColor.BOLD + "Sumo" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Arenas " + ChatColor.GREEN + "=======");
        player.sendMessage(" ");
        if (!ArenaManager.plugin.getArenaConfig().getIntegerList("Arenas.ArenaList").isEmpty())
        {

            for (Integer i : ArenaManager.plugin.getArenaConfig().getIntegerList("Arenas.ArenaList"))
            {
                player.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.AQUA + i + ": " + ArenaManager.plugin.getArenaConfig().getString("Arenas." + i + ".Name"));
            }
            player.sendMessage(" ");
            player.sendMessage(ChatColor.GREEN + "============================");
            player.sendMessage(" ");
            return true;
        }
        else
        {
            player.sendMessage("You Have No Arenas");
            player.sendMessage(" ");
            player.sendMessage(ChatColor.GREEN + "=====================================");
            player.sendMessage(" ");
            return false;
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        return null;
    }
}
