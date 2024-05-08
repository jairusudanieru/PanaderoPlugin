package dev.jairusu.panaderoplugin.Events;

import dev.jairusu.panaderoplugin.Configuration;
import dev.jairusu.panaderoplugin.Methods.WorldGroups;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class PlayerAsyncChat implements Listener {

   @EventHandler
   public void onPlayerChat(AsyncChatEvent event) {
      event.setCancelled(true);
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();

      String message = LegacyComponentSerializer.legacyAmpersand().serialize(event.message());
      Component fullMessage = Component.text("<" + player.getName() + "> " + message);

      List<String> worldGroup = WorldGroups.worldGroups(playerWorld);
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         onlinePlayer.sendMessage(fullMessage);
      }

      String consoleMessage = "[" + player.getName() + "]: " + message;
      Configuration.getPlugin.getLogger().info(consoleMessage);
   }

}
