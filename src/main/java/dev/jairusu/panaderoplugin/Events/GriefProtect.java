package dev.jairusu.panaderoplugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GriefProtect implements Listener {

   @EventHandler
   public void onFarmTrample(EntityChangeBlockEvent event) {
      if (event.getBlock().getType().equals(Material.FARMLAND)) {
         event.setCancelled(true);
      }
   }

   @EventHandler
   public void onCreeperExplode(EntityExplodeEvent event) {
      if (event.getEntityType().equals(EntityType.CREEPER)) {
         event.setCancelled(true);
      }
   }

   @EventHandler
   public void onCreeperDamage(EntityDamageByEntityEvent event) {
      EntityType damageFrom = event.getDamager().getType();
      EntityType entityType = event.getEntityType();

      boolean isArmorStand = entityType.equals(EntityType.ARMOR_STAND);
      boolean isItemFrame = entityType.equals(EntityType.ITEM_FRAME);
      boolean isMinecart = entityType.equals(EntityType.MINECART);

      if (!damageFrom.equals(EntityType.CREEPER)) return;
      if (!isArmorStand && !isItemFrame && !isMinecart) return;
      event.setCancelled(true);
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      World creative = Bukkit.getWorld("creative");
      if (creative == null) return;
      if (!playerWorld.equals(creative)) return;

      if (player.getInventory().getItemInMainHand().getType().name().contains("SPAWN_EGG") ||
              player.getInventory().getItemInOffHand().getType().name().contains("SPAWN_EGG")) {
         event.setCancelled(true);
      }
   }


}
