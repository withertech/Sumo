package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandRemove extends SubCommand
{
    @Override
    public String getName()
    {
        return "remove";
    }

    @Override
    public String getDescription()
    {
        return "Removes an arena from the game";
    }

    @Override
    public String getSyntax()
    {
        return "/sumo remove <id>";
    }

    @Override
    public String getPermission()
    {
        return "sumo.admin";
    }

    @Override
    public boolean perform(Player player, String[] args)
    {
        Integer num = 0;
        if (args.length == 2)
        {
            try{
                num = Integer.parseInt(args[1]);
            }catch(NumberFormatException e){
                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "] " + ChatColor.RED + "Invalid arena ID");
                return false;
            }
            if(ArenaManager.getManager().getArena(num) == null){
                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "] " + ChatColor.RED + "Invalid arena ID");
                return false;
            }
            ArenaManager.getManager().removeArena(num);
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "] " + ChatColor.AQUA + "Removed arena with id: " + num);
            return true;
        }
        else
        {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "] " + ChatColor.RED + "Invalid arena ID");
            return false;
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        if (args.length == 2)
        {
            List<String> id = new ArrayList<String>();
            for (Integer i : ArenaManager.plugin.getArenaConfig().getIntegerList("Arenas.ArenaList"))
            {
                id.add(Integer.toString(i));
            }
            return id;
        }
        return null;
    }
}
