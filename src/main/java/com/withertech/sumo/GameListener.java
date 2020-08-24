package com.withertech.sumo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class GameListener implements Listener{

    static List<String> players = new ArrayList<String>();
    static Sumo plugin;

    public GameListener(Sumo plugin){
        GameListener.plugin = plugin;
    }

    @EventHandler
    public void onDamange(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && players.contains(((Player) e.getEntity()).getName())){
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e){
        Location loc = e.getPlayer().getLocation();
        Block locBlock = loc.getBlock();
        Material block = locBlock.getType();
        if(block == Material.LAVA && ArenaManager.getManager().isInGame(e.getPlayer()) && ArenaManager.getManager().getArenaFromPlayer(e.getPlayer()).started)
        {
            Arena a = ArenaManager.getManager().getArenaFromPlayer(e.getPlayer());
            e.getPlayer().sendMessage("You Died!");
            ArenaManager.getManager().removePlayer(e.getPlayer());
            if((a.getPlayers().size() == 1) ){
                for (String pname:a.getPlayers())
                {
                    Bukkit.getPlayer(pname).sendMessage("You Won!");
                    ArenaManager.getManager().removePlayer(Bukkit.getPlayer(pname));
                }
            }
        }
    }

    public static void add(Player p){
        final String name = p.getName();
        players.add(name);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
            @Override
            public void run(){
                players.remove(name);
            }
        }, 100L);
    }
}
