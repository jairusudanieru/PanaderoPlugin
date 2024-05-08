package dev.jairusu.panaderoplugin.Methods;

import com.github.games647.fastlogin.bukkit.FastLoginBukkit;
import com.github.games647.fastlogin.core.PremiumStatus;
import dev.jairusu.panaderoplugin.Configuration;
import dev.jairusu.panaderoplugin.CustomFiles.PasswordFile;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Authentication {

   public static FastLoginBukkit getFastLoginBukkit = JavaPlugin.getPlugin(FastLoginBukkit.class);

   public static void authenticatePlayer(Player player) {
      Location spawnLocation = Configuration.getLocation("location.spawnLocation");
      if (Configuration.getBoolean("config.teleportOnSpawn") && spawnLocation != null) {
         player.teleport(spawnLocation);
      }
      if (getFastLoginBukkit.getStatus(player.getUniqueId()).equals(PremiumStatus.CRACKED)) {
         if (!PasswordFile.getPlayerFile(player).exists() || PasswordFile.getPlayerFileConfig(player).get("data.password") == null) {
            registerPlayer(player);
            return;
         }

         player.setMetadata("unlogged", new FixedMetadataValue(Configuration.getPlugin, "unlogged"));
         player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, 1, true, true));
         player.setGameMode(GameMode.SPECTATOR);
         player.sendMessage(Configuration.text("<gray>/login (password)"));
      } else {
         if (!PasswordFile.getPlayerFile(player).exists() || PasswordFile.getPlayerFileConfig(player).get("data.username") == null) {
            autoRegisterPlayer(player);
         }
      }
   }

   private static void registerPlayer(Player player) {
      player.setMetadata("unlogged", new FixedMetadataValue(Configuration.getPlugin, "unlogged"));
      player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, 1, true, true));
      player.setGameMode(GameMode.SPECTATOR);
      player.sendMessage(Configuration.text("<gray>/register (password) (confirmPassword)"));
   }

   private static void autoRegisterPlayer(Player player) {
      PasswordFile.savePasswordFile(player, "data.username", player.getName());
      PasswordFile.savePasswordFile(player, "data.uuid", player.getUniqueId().toString());
      PasswordFile.savePasswordFile(player, "data.password", null);
      PasswordFile.savePasswordFile(player, "data.status", "premium");
   }

}
