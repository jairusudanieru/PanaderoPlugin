package dev.jairusu.panaderoplugin.Events;

import dev.jairusu.panaderoplugin.Configuration;
import dev.jairusu.panaderoplugin.Methods.ChatManager;
import dev.jairusu.panaderoplugin.Methods.IdleManager;
import dev.jairusu.panaderoplugin.Methods.WorldGroups;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeave implements Listener {

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      event.joinMessage(null);
      Player player = event.getPlayer();

      if (player.isDead()) player.spigot().respawn();
      Bukkit.getScheduler().runTaskLater(Configuration.getPlugin, () -> {
         player.teleport(WorldGroups.authLocation());
         for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(Configuration.getPlugin, onlinePlayer);
            onlinePlayer.hidePlayer(Configuration.getPlugin, player);
         }
      }, 10L);
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      event.quitMessage(null);
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();

      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         ChatManager.sendQuitMessage(player, playerWorld, onlinePlayer);
      }
   }

   @EventHandler
   public void onPlayerQuitAFK(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      IdleManager.lastMovementHashMap.remove(player);
      if (!IdleManager.afkStatusHashMap.containsKey(player)) return;
      IdleManager.afkStatusHashMap.remove(player);
      IdleManager.removeToAFKTeam(player, IdleManager.afkTeam());
   }
   
}
