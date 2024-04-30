package dev.jairusu.panaderoplugin.Events;

import dev.jairusu.panaderoplugin.Configuration;
import dev.jairusu.panaderoplugin.Methods.LobbyManager;
import dev.jairusu.panaderoplugin.Methods.WorldGroups;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PlayerInteract implements Listener {

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack item = event.getItem();

      if (!player.getWorld().equals(WorldGroups.lobbyWorld())) return;
      Action action = event.getAction();
      if (!action.isRightClick() || item == null) return;

      if (item.equals(LobbyManager.COMPASS())) {
         Inventory inventory = LobbyManager.getInventory();
         player.openInventory(inventory);
      } else if (item.equals(LobbyManager.LIME_DYE())) {
         hideOthers(player);
         player.getInventory().setItem(8, LobbyManager.GRAY_DYE());
      } else if (item.equals(LobbyManager.GRAY_DYE())) {
         showOthers(player);
         player.getInventory().setItem(8, LobbyManager.LIME_DYE());
      } else if (item.equals(LobbyManager.INFO_BOOK())) {
         player.sendMessage(Configuration.text("<reset>Welcome To Pandesal!"));
      }
   }

   @EventHandler
   public void onPlayerTouch(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      Block clickedBlock = event.getClickedBlock();
      Action action = event.getAction();

      if (player.getGameMode().equals(GameMode.CREATIVE)) return;
      if (playerWorld.equals(WorldGroups.arenaWorld())) {
         if (action.equals(Action.PHYSICAL)) event.setCancelled(true);
         if (clickedBlock == null) return;
         for (String blocks : blockedBlocks()) {
            if (!clickedBlock.getType().name().contains(blocks)) continue;
            event.setCancelled(true);
         }
         return;
      }

      if (WorldGroups.worldGroups(WorldGroups.survivalWorld()).contains(playerWorld.getName())) return;
      if (WorldGroups.worldGroups(WorldGroups.creativeWorld()).contains(playerWorld.getName())) return;
      if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
      event.setCancelled(true);
   }

   private List<String> blockedBlocks() {
      return Arrays.asList("_TRAPDOOR","GLOW_BERRIES",
              "ANVIL","CHEST","GATE","DAYLIGHT_DETECTOR"
      );
   }

   private void showOthers(Player player) {
      List<String> worldGroup = WorldGroups.worldGroups(player.getWorld());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         if (!Configuration.isAuthenticated(onlinePlayer)) continue;
         player.showPlayer(Configuration.getPlugin, onlinePlayer);
      }
   }

   private void hideOthers(Player player) {
      List<String> worldGroup = WorldGroups.worldGroups(player.getWorld());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         player.hidePlayer(Configuration.getPlugin, onlinePlayer);
      }
   }

}
