package com.withertech.sumo;


import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Arena{

    //you want some info about the arena stored here
    public Integer id;//the arena id
    public boolean started = false;
    public String name;
    public Location spawn;
    public Location lobby;
    public Location mainlobby;//spawn location for the arena
    List<String> players = new ArrayList<String>();//list of players

    //now let's make a few getters/setters, and a constructor
    public Arena(Location spawn, Location lobby, Location mainlobby, String name, Integer id){
        this.spawn = spawn;
        this.lobby = lobby;
        this.mainlobby = mainlobby;
        this.name = name;
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

    public List<String> getPlayers(){
        return this.players;
    }

    public boolean getStarted(){
        return this.started;
    }
    public String getName(){
        return this.name;
    }
}
