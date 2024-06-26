package dev.jairusu.panaderoplugin.CustomFiles;

import dev.jairusu.panaderoplugin.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PasswordFile {

   public static File getPlayerFile(Player player) {
      return new File(Configuration.getPlugin.getDataFolder()  + File.separator + "Passwords", player.getName() + ".yml");
   }

   public static FileConfiguration getPlayerFileConfig(Player player) {
      File file = getPlayerFile(player);
      if (!file.exists()) {
         boolean getParentFile = file.getParentFile().mkdirs();
         if (!getParentFile) Configuration.getPlugin.getLogger().info("Error in getting password.yml parent file!");
         try {
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) throw new IOException("Unable to create file!");
         } catch (IOException error) {
            throw new RuntimeException(error);
         }
      }

      return YamlConfiguration.loadConfiguration(file);
   }

   public static void savePasswordFile(Player player, String path, String value) {
      File file = getPlayerFile(player);
      FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
      try {
         fileConfiguration.set(path, value);
         fileConfiguration.save(file);
      } catch (IOException exception) {
         Configuration.getPlugin.getLogger().info("Unable to save file!");
      }
   }

}
