package dev.jairusu.panaderoplugin.Events;

import dev.jairusu.panaderoplugin.Configuration;
import dev.jairusu.panaderoplugin.Methods.IdleManager;
import dev.jairusu.panaderoplugin.Methods.WorldGroups;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class PlayerMove implements Listener {

   @EventHandler
   public void onUnloggedPlayerMove(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      if (!player.hasMetadata("unlogged")) return;
      event.setCancelled(true);
   }

   @EventHandler
   public void onPlayerVoid(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      double voidLevel = Configuration.getDouble("config.voidYLevel");
      Location spawnLocation = player.getWorld().getSpawnLocation();
      if (!player.getWorld().equals(WorldGroups.lobbyWorld()) &&
              !player.getWorld().equals(WorldGroups.authWorld())) return;
      if (player.getLocation().getY() > voidLevel) return;
      player.setFallDistance(0);
      player.teleport(spawnLocation);
   }

   @EventHandler
   public void onPlayerMoveAFK(PlayerMoveEvent event) {
      if (event.hasChangedOrientation()) return;
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      List<String> worldGroups = WorldGroups.worldGroups(WorldGroups.survivalWorld());
      if (!worldGroups.contains(playerWorld.getName())) return;

      IdleManager.lastMovementHashMap.put(player, System.currentTimeMillis());
      if (!IdleManager.afkStatusHashMap.containsKey(player)) return;
      IdleManager.afkStatusHashMap.remove(player);
      IdleManager.removeToAFKTeam(player, IdleManager.afkTeam());
   }

}
