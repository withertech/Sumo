package com.withertech.sumo;

import com.withertech.sumo.Commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
    public void onSignChange(SignChangeEvent e) {
        String line1 = e.getLine(0);
        String line2 = e.getLine(1);
        if (line1 != null && line1.equals("[Sumo]")) {
            e.setLine(0, ChatColor.GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.GRAY + "]");
        }
        if(line2 != null && !line2.equals("")){
            e.setLine(2, ChatColor.AQUA + line2);
            e.setLine(1, "");
        }
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission("sign.use")) {

            Block b = e.getClickedBlock();
            if (b.getType() == Material.OAK_SIGN || b.getType() == Material.OAK_WALL_SIGN || b.getType() == Material.SPRUCE_SIGN || b.getType() == Material.SPRUCE_WALL_SIGN || b.getType() == Material.BIRCH_SIGN || b.getType() == Material.BIRCH_WALL_SIGN || b.getType() == Material.JUNGLE_SIGN || b.getType() == Material.JUNGLE_WALL_SIGN || b.getType() == Material.DARK_OAK_SIGN || b.getType() == Material.DARK_OAK_WALL_SIGN || b.getType() == Material.ACACIA_SIGN || b.getType() == Material.ACACIA_WALL_SIGN || b.getType() == Material.CRIMSON_SIGN || b.getType() == Material.CRIMSON_WALL_SIGN || b.getType() == Material.WARPED_SIGN || b.getType() == Material.WARPED_WALL_SIGN) {

                Sign sign = (Sign) b.getState();
                if (ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase("[Sumo]")) {
                    if(sign.getLine(2) != null && !sign.getLine(2).equals("")) {
                        for (int i : ArenaManager.plugin.getConfig().getIntegerList("Arenas.ArenaList"))
                        {
                            if(ArenaManager.getManager().getArena(i).name.equalsIgnoreCase(ChatColor.stripColor(sign.getLine(2)))){
                                Bukkit.dispatchCommand(p, "sumo join " + i);
                            }
                        }


                    }
                }
            }

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
