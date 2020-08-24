package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
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
        return null;
    }

    @Override
    public String getSyntax()
    {
        return "/sumo leave";
    }

    @Override
    public void perform(Player player, String[] args)
    {
        ArenaManager.getManager().removePlayer(player);
        player.sendMessage("You have left the arena!");
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        return null;
    }
}
