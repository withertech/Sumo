package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandStart extends SubCommand
{
    @Override
    public String getName()
    {
        return "start";
    }

    @Override
    public String getDescription()
    {
        return "Starts an arena's game";
    }

    @Override
    public String getSyntax()
    {
        return "/sumo start <id>";
    }

    @Override
    public boolean perform(Player player, String[] args)
    {
        int num = 0;
        try{
            num = Integer.parseInt(args[1]);
        }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            player.sendMessage("Invalid arena ID");
            return false;
        }
        ArenaManager.getManager().startGame(num);
        return true;
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        if (args.length == 2)
        {
            List<String> id = new ArrayList<String>();
            for (int i : ArenaManager.plugin.getConfig().getIntegerList("Arenas.ArenaList"))
            {
                id.add(Integer.toString(i));
            }
            return id;
        }
        return null;
    }
}
