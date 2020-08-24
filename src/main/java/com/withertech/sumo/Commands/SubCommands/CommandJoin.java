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
        return null;
    }

    @Override
    public String getSyntax()
    {
        return "/sumo join <id>";
    }

    @Override
    public void perform(Player player, String[] args)
    {
        int num = 0;
        try{
            num = Integer.parseInt(args[1]);
        }catch(NumberFormatException e){
            player.sendMessage("Invalid arena ID");
        }catch(ArrayIndexOutOfBoundsException e) {
            player.sendMessage("Invalid arena ID");
        }
        if(!ArenaManager.getManager().isInGame(player)){
            ArenaManager.getManager().addPlayer(player, num);
        } else{
            player.sendMessage("You are already in a game");
        }
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
