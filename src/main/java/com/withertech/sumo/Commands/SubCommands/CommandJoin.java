package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandJoin extends SubCommand
{
    @Override
    public String getName()
    {
        return "join";
    }

    @Override
    public String getDescription()
    {
        return "Joins an arena's lobby";
    }

    @Override
    public String getSyntax()
    {
        return "/sumo join <id>";
    }

    @Override
    public String getPermission()
    {
        return null;
    }

    @Override
    public boolean perform(Player player, String[] args)
    {
        Integer num = 0;
        try{
            num = Integer.parseInt(args[1]);
        }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            player.sendMessage("Invalid arena ID");
            return false;
        }
        if(!ArenaManager.getManager().isInGame(player)){
            ArenaManager.getManager().addPlayer(player, num);
            return true;
        } else{
            player.sendMessage("You are already in a game");
            return false;
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        if (args.length == 2)
        {
            List<String> id = new ArrayList<String>();
            for (Integer i : ArenaManager.plugin.getConfig().getIntegerList("Arenas.ArenaList"))
            {
                id.add(Integer.toString(i));
            }
            return id;
        }
        return null;
    }
}
