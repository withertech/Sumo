package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
        return null;
    }

    @Override
    public String getSyntax()
    {
        return "/sumo list";
    }

    @Override
    public void perform(Player player, String[] args)
    {
        for (int i : ArenaManager.plugin.getConfig().getIntegerList("Arenas.ArenaList"))
        {
            player.chat(Integer.toString(i));
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        return null;
    }
}
