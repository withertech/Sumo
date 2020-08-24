package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
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
        return null;
    }

    @Override
    public String getSyntax()
    {
        return "/sumo create";
    }

    @Override
    public void perform(Player player, String[] args)
    {
        ArenaManager.getManager().createArena(player.getLocation(), player.getLocation(), player.getLocation());
        player.sendMessage("Created arena at " + player.getLocation().toString());
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        return null;
    }
}
