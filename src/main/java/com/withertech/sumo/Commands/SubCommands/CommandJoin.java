package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            if(!ArenaManager.getManager().isInGame(player)){
                ArenaManager.getManager().addPlayer(player, num);
                Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "] " + ChatColor.YELLOW + player.getName() + " Has Joined The Arena");
                return true;
            } else{
                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "] " + ChatColor.RED + "You are already in a game");
                return false;
            }
        }
        else {
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
