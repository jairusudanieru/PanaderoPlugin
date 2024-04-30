package dev.jairusu.panaderoplugin.Events;

import dev.jairusu.panaderoplugin.Configuration;
import dev.jairusu.panaderoplugin.Methods.ChatManager;
import dev.jairusu.panaderoplugin.Methods.IdleManager;
import dev.jairusu.panaderoplugin.Methods.LobbyManager;
import dev.jairusu.panaderoplugin.Methods.WorldGroups;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class ChangeWorld implements Listener {

   @EventHandler
   public void onChangeWorld(PlayerChangedWorldEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      World previousWorld = event.getFrom();

      String playerWorldName = playerWorld.getName();
      String previousWorldName = previousWorld.getName();
      List<String> worldGroup = WorldGroups.worldGroups(playerWorld);
      List<String> previousGroup = WorldGroups.worldGroups(previousWorld);

      if (worldGroup.contains(playerWorldName) && !worldGroup.contains(previousWorldName)) {
         if (playerWorld.equals(WorldGroups.lobbyWorld())) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            LobbyManager.give(player);
         }

         for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
            player.showPlayer(Configuration.getPlugin, onlinePlayer);
            onlinePlayer.showPlayer(Configuration.getPlugin, player);
            ChatManager.sendJoinMessage(player, playerWorld, onlinePlayer);
         }
      }

      if (previousGroup.contains(previousWorldName) && !previousGroup.contains(playerWorldName)) {
         for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!previousGroup.contains(onlinePlayer.getWorld().getName())) continue;
            player.hidePlayer(Configuration.getPlugin, onlinePlayer);
            onlinePlayer.hidePlayer(Configuration.getPlugin, player);
            ChatManager.sendQuitMessage(player, previousWorld, onlinePlayer);
         }
      }
   }

   @EventHandler
   public void changeWorldAFK(PlayerChangedWorldEvent event) {
      Player player = event.getPlayer();
      IdleManager.lastMovementHashMap.remove(player);
      if (!IdleManager.afkStatusHashMap.containsKey(player)) return;
      IdleManager.afkStatusHashMap.remove(player);
      IdleManager.removeToAFKTeam(player, IdleManager.afkTeam());
   }



}
