package dev.jairusu.panaderoplugin.Events;

import com.github.games647.fastlogin.core.PremiumStatus;
import dev.jairusu.panaderoplugin.Configuration;
import dev.jairusu.panaderoplugin.Methods.Authentication;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

public class PlayerAuth implements Listener {

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      event.joinMessage(null);
      Player player = event.getPlayer();
      final BukkitTask[] task = new BukkitTask[1];
      Bukkit.getScheduler().runTaskLater(Configuration.getPlugin, ()-> task[0] = Bukkit.getScheduler().runTaskTimer(Configuration.getPlugin, () -> {
         if (!Authentication.getFastLoginBukkit.getStatus(player.getUniqueId()).equals(PremiumStatus.UNKNOWN)) {
            Authentication.authenticatePlayer(player);
            task[0].cancel();
         }
      }, 1L, 1L), 1L);
   }



}
