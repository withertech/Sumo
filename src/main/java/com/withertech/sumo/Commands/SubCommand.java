package com.withertech.sumo.Commands;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract boolean perform(Player player, String args[]);

    public abstract List<String> getSubcommandArguments(Player player, String args[]);

}

