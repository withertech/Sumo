package com.withertech.sumo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardManager
{
    static Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    public static void setScoreboard(Player player){
        Objective obj;
        if(board.getObjective("ServerName") == null){
            obj = board.registerNewObjective("ServerName", "dummy", ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Sumo" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE);
        }else {
            obj = board.getObjective("ServerName");
        }
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Team Wins;
        if(board.getTeam("Wins") == null){
            Wins = board.registerNewTeam("Wins");
        }else {
            Wins = board.getTeam("Wins");
        }
        Wins.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        Wins.setPrefix(ChatColor.GREEN + "Wins: " + ArenaManager.plugin.getStatsConfig().getInt("Data." + player.getUniqueId() + ".Wins"));
        obj.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(15);
        Team Losses;
        if(board.getTeam("Losses") == null){
            Losses = board.registerNewTeam("Losses");
        }else {
            Losses = board.getTeam("Losses");
        }
        Losses.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
        Losses.setPrefix(ChatColor.RED + "Losses: " + ArenaManager.plugin.getStatsConfig().getInt("Data." + player.getUniqueId() + ".Losses"));
        obj.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(14);
        player.setScoreboard(board);
    }

    public static void toggleScoreboard(Player player, boolean enabled){
        if(enabled){
            player.setScoreboard(board);
        }else {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    public static void updateScoreboard(Player player)
    {
        player.getScoreboard().getTeam("Wins").setPrefix(ChatColor.GREEN + "Wins: " + ArenaManager.plugin.getStatsConfig().getInt("Data." + player.getUniqueId() + ".Wins"));
        player.getScoreboard().getTeam("Losses").setPrefix(ChatColor.RED + "Losses: " + ArenaManager.plugin.getStatsConfig().getInt("Data." + player.getUniqueId() + ".Losses"));
    }
}
