package dev.jairusu.panaderoplugin.Events;

import dev.jairusu.panaderoplugin.Configuration;
import dev.jairusu.panaderoplugin.Methods.WorldGroups;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class PlayerSleep implements Listener {

   @EventHandler
   public void onPlayerSleep(PlayerDeepSleepEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      player.wakeup(false);
      Bukkit.getScheduler().runTaskLater(Configuration.getPlugin, () -> {
         playerWorld.setStorm(false);
         playerWorld.setThundering(false);
         dayNightAnimation(playerWorld);
         sendSleepMessage(player);
      }, 1L);
   }

   private void dayNightAnimation(World playerWorld) {
      final BukkitTask[] task = new BukkitTask[1];
      task[0] = Bukkit.getScheduler().runTaskTimer(Configuration.getPlugin, () -> {
         if (playerWorld.getTime() < 12010) {
            task[0].cancel();
            return;
         }
         playerWorld.setTime(playerWorld.getTime() + 100);
      }, 1L, 1L);
   }

   private void sendSleepMessage(Player player) {
      List<String> worldGroups = WorldGroups.worldGroups(player.getWorld());
      String sleepMessage = Configuration.getString("message.sleepMessage");
      if (sleepMessage == null || sleepMessage.isEmpty()) return;
      sleepMessage = sleepMessage.replace("%player%",player.getName());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroups.contains(onlinePlayer.getWorld().getName())) continue;
         onlinePlayer.sendMessage(Configuration.text(sleepMessage));
      }
   }


}
