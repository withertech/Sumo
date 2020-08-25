package com.withertech.sumo.Commands.SubCommands;

import com.withertech.sumo.ArenaManager;
import com.withertech.sumo.Commands.CommandManager;
import com.withertech.sumo.Commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandHelp extends SubCommand
{
    @Override
    public String getName()
    {
        return "help";
    }

    @Override
    public String getDescription()
    {
        return "Shows information about all of sumo's \n   commands";
    }

    @Override
    public String getSyntax()
    {
        return "/sumo help";
    }

    @Override
    public String getPermission()
    {
        return null;
    }


    @Override
    public boolean perform(Player p, String[] args) {

        CommandManager commandManager = new CommandManager();

        p.sendMessage(" ");
        p.sendMessage(ChatColor.GREEN + "========== " + ChatColor.GRAY + "[" + ChatColor.AQUA + ChatColor.BOLD + "Sumo" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Commands " + ChatColor.GREEN + "==========");
        p.sendMessage(" ");
        if(args.length >= 1 && !args[0].equalsIgnoreCase("help")){
            for (int i = 0; i < commandManager.getSubCommands().size(); i++){
                if(commandManager.getSubCommands().get(i).getName().equalsIgnoreCase(args[0])){
                    p.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.YELLOW + commandManager.getSubCommands().get(i).getSyntax() + " - " + ChatColor.GRAY + commandManager.getSubCommands().get(i).getDescription());
                }
            }
        }else{
            for (int i = 0; i < commandManager.getSubCommands().size(); i++){
                p.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.YELLOW + commandManager.getSubCommands().get(i).getSyntax() + " - " + ChatColor.GRAY + commandManager.getSubCommands().get(i).getDescription());
            }
        }

        p.sendMessage(" ");
        p.sendMessage(ChatColor.GREEN + "====================================");
        p.sendMessage(" ");
        return true;
    }


    @Override
    public List<String> getSubcommandArguments(Player player, String[] args)
    {
        return null;
    }
}
