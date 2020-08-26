package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.Arena;
import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSet extends SubCommand
{
    @Override
    public String getName()
    {
        return "set";
    }

    @Override
    public String getDescription()
    {
        return "Sets an arena's key from the \n   player's current location or the value parameter depending \n   on the key being set";
    }

    @Override
    public String getSyntax()
    {
        return "/sumo set <id> <key> [value]";
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
        try{
            num = Integer.parseInt(args[1]);
        }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            player.sendMessage("Invalid arena ID");
            return false;
        }
        if (args.length > 3)
        {
            ArenaManager.getManager().setArena(num, args[2], player.getLocation(), args[3]);
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "] " + ChatColor.AQUA + "Set " + args[2] + " of arena with id: " + num + " to " + args[3]);
        }
        else
        {
            ArenaManager.getManager().setArena(num, args[2], player.getLocation(), "");
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "] " + ChatColor.AQUA + "Set " + args[2] + " of arena with id: " + num + " to \nWorld: " + player.getLocation().getWorld().getName() + "\nX: " + player.getLocation().getBlockX() + "\nY: " + player.getLocation().getBlockY() + "\nZ: " + player.getLocation().getBlockZ() + "\nYaw: " + player.getLocation().getYaw() + "\nPitch: " + player.getLocation().getPitch());
        }
        return true;
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        if (args.length == 2)
        {
            List<String> id = new ArrayList<String>();
            for (int i : ArenaManager.plugin.getArenaConfig().getIntegerList("Arenas.ArenaList"))
            {
                id.add(Integer.toString(i));
            }
            return id;
        }
        if (args.length == 3)
        {
            List<String> key = new ArrayList<String>();
            key.add("Name");
            key.add("Spawn");
            key.add("Lobby");
            key.add("MainLobby");
            return key;
        }
        return null;
    }
}
