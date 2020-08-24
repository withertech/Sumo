package com.withertech.sumo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaManager{

    //save where the player teleported
    public Map<String, Location> locs = new HashMap<String, Location>();
    //make a new instance of the class
    public static ArenaManager am = new ArenaManager();
    //a few other fields
    Map<String, ItemStack[]> inv = new HashMap<String, ItemStack[]>();
    Map<String, ItemStack[]> armor = new HashMap<String, ItemStack[]>();
    //list of arenas
    List<Arena> arenas = new ArrayList<Arena>();
    int arenaSize = 0;

    public static Sumo plugin;
    public ArenaManager(Sumo sumo) {
        plugin = sumo;
    }

    public ArenaManager(){

    }

    //we want to get an instance of the manager to work with it statically
    public static ArenaManager getManager(){
        return am;
    }

    //get an Arena object from the list
    public Arena getArena(int i){
        for(Arena a : arenas){
            if(a.getId() == i){
                return a;
            }
        }
        return null;
    }

    //add players to the arena, save their inventory
    public void addPlayer(Player p, int i){
        Arena a = getArena(i);//get the arena you want to join
        if(a == null){//make sure it is not null
            p.sendMessage("Invalid arena!");
            return;
        }

        a.getPlayers().add(p.getName());//add them to the arena list of players
        inv.put(p.getName(), p.getInventory().getContents());//save inventory
        armor.put(p.getName(), p.getInventory().getArmorContents());

        p.getInventory().setArmorContents(null);
        p.getInventory().clear();

        locs.put(p.getName(), p.getLocation());
        p.teleport(a.lobby);//teleport to the arena lobby
    }

    //remove players
    public void removePlayer(Player p){
        Arena a = null;//make an arena
        for(Arena arena : arenas){
            if(arena.getPlayers().contains(p.getName())){
                a = arena;//if the arena has the player, the arena field would be the arena containing the player
            }
            //if none is found, the arena will be null
        }
        if(a == null || !a.getPlayers().contains(p.getName())){//make sure it is not null
            p.sendMessage("Invalid operation!");
            return;
        }

        a.getPlayers().remove(p.getName());//remove from arena

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        p.getInventory().setContents(inv.get(p.getName()));//restore inventory
        p.getInventory().setArmorContents(armor.get(p.getName()));

        inv.remove(p.getName());//remove entries from hashmaps
        armor.remove(p.getName());
//        p.teleport(locs.get(p.getName()));
        p.teleport(a.mainlobby);
        locs.remove(p.getName());

        p.setFireTicks(0);
    }

    public void startGame(int i){
        Arena a = getArena(i);//get the arena you want to join
        if(a == null){//make sure it is not null
            return;
        }
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        for (String pname:a.getPlayers())
        {
            Bukkit.getPlayer(pname).teleport(a.spawn);
            Bukkit.dispatchCommand(console, "give " + pname + " minecraft:stick{Unbreakable:1,display:{Name:\"{\\\"text\\\":\\\"Baseball Bat\\\"}\"},Enchantments:[{id:\"minecraft:knockback\",lvl:3}],AttributeModifiers:[{AttributeName:\"generic.movement_speed\",Name:\"generic.movement_speed\",Slot:\"mainhand\",Amount:0.2,Operation:0,UUIDMost:58156,UUIDLeast:183145}]} 1");
        }
        a.started = true;
    }

    public Arena getArenaFromPlayer(Player p){
        Arena a = null;//make an arena
        for(Arena arena : arenas){
            if(arena.getPlayers().contains(p.getName())){
                a = arena;//if the arena has the player, the arena field would be the arena containing the player
            }
            //if none is found, the arena will be null
        }
        if(a == null || !a.getPlayers().contains(p.getName())){//make sure it is not null
            p.sendMessage("Invalid operation!");
            return null;
        }
        return a;
    }
    //create arena
    public Arena createArena(Location spawn, Location lobby, Location mainlobby){
        int num = arenaSize + 1;
        arenaSize++;

        Arena a = new Arena(spawn, lobby, mainlobby, num);
        arenas.add(a);

        plugin.getConfig().set("Arenas." + num + ".Spawn", serializeLoc(spawn));
        plugin.getConfig().set("Arenas." + num + ".Lobby", serializeLoc(lobby));
        plugin.getConfig().set("Arenas." + num + ".MainLobby", serializeLoc(mainlobby));
        List<Integer> list = plugin.getConfig().getIntegerList("Arenas.ArenaList");
        list.add(num);
        plugin.getConfig().set("Arenas.ArenaList", list);
        plugin.saveConfig();

        return a;
    }

    public Arena reloadArena(Location spawn, Location lobby, Location mainlobby) {
        int num = arenaSize + 1;
        arenaSize++;

        Arena a = new Arena(spawn, lobby, mainlobby, num);
        arenas.add(a);

        return a;
    }

    public void setArena(int i, String key, Location location){
        Arena a = getArena(i);
        if(a == null) {
            return;
        }
        arenas.remove(a);
        switch (key){
            case "Spawn":
                a.spawn = location;
                break;
            case "Lobby":
                a.lobby = location;
                break;
            case "MainLobby":
                a.mainlobby = location;
                break;
            default:
                throw new IllegalArgumentException();
        }
        arenas.add(i - 1, a);
        plugin.getConfig().set("Arenas." + i + "." + key, serializeLoc(location));
        plugin.saveConfig();
    }

    public void removeArena(int i) {
        Arena a = getArena(i);
        if(a == null) {
            return;
        }
        arenas.remove(a);

        plugin.getConfig().set("Arenas." + i + ".Spawn", null);
        plugin.getConfig().set("Arenas." + i + ".Lobby", null);
        plugin.getConfig().set("Arenas." + i + ".MainLobby", null);
        List<Integer> list = plugin.getConfig().getIntegerList("Arenas.ArenaList");
        list.remove(i - 1);
        plugin.getConfig().set("Arenas.ArenaList", list);
        plugin.saveConfig();
    }

    public boolean isInGame(Player p){
        for(Arena a : arenas){
            if(a.getPlayers().contains(p.getName()))
                return true;
        }
        return false;
    }

    public void loadGames(){
        arenaSize = 0;

        if(plugin.getConfig().getIntegerList("Arenas.ArenaList").isEmpty()){
            return;
        }

        for(int i : plugin.getConfig().getIntegerList("Arenas.ArenaList")){
            Arena a = reloadArena(deserializeLoc(plugin.getConfig().getString("Arenas." + i + ".Spawn")), deserializeLoc(plugin.getConfig().getString("Arenas." + i + ".Lobby")), deserializeLoc(plugin.getConfig().getString("Arenas." + i + ".MainLobby")));
            a.id = i;
        }
    }

    public String serializeLoc(Location l){
        return l.getWorld().getName()+","+l.getBlockX()+","+l.getBlockY()+","+l.getBlockZ();
    }
    public Location deserializeLoc(String s){
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]));
    }
}