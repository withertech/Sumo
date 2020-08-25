package com.withertech.sumo.Commands;

import java.util.ArrayList;
import java.util.List;

import com.withertech.sumo.Commands.SubCommands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class CommandManager implements TabExecutor
{
    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(){
        //Get the subcommands so we can access them in the command manager class(here)
        subcommands.add(new CommandCreate());
        subcommands.add(new CommandJoin());
        subcommands.add(new CommandLeave());
        subcommands.add(new CommandList());
        subcommands.add(new CommandRemove());
        subcommands.add(new CommandSet());
        subcommands.add(new CommandStart());
        subcommands.add(new CommandHelp());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubCommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        if (getSubCommands().get(i).getPermission() == null || p.hasPermission(getSubCommands().get(i).getPermission()) )
                        {
                            if(getSubCommands().get(i).perform(p, args)){
                                return true;
                            }else{
                                new CommandHelp().perform(p, args);
                            }
                        }else {
                            p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                        }
                    }
                }
            }else if(args.length == 0){

                new CommandHelp().perform(p, args);

            }


        }


        return true;
    }

    public ArrayList<SubCommand> getSubCommands(){
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1){ //prank <subcommand> <args>
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++){
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }

            return subcommandsArguments;
        }else if(args.length >= 2){
            for (int i = 0; i < getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }
        }

        return null;
    }

}
