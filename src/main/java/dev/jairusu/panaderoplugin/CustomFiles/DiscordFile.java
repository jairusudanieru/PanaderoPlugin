package dev.jairusu.panaderoplugin.CustomFiles;

import dev.jairusu.panaderoplugin.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DiscordFile {

   private static FileConfiguration fileConfiguration;
   private static File file;

   public static void checkConfigFile() {
      file = new File(Configuration.getPlugin.getDataFolder(), "discord.yml");
      if (!file.exists()) {
         boolean getParentFile = file.getParentFile().mkdirs();
         if (!getParentFile) return;
         Configuration.getPlugin.saveResource("discord.yml", false);
      }
      fileConfiguration = YamlConfiguration.loadConfiguration(file);
   }

   public static FileConfiguration getFileConfig() {
      return fileConfiguration;
   }

   public static void reloadFile() {
      fileConfiguration = YamlConfiguration.loadConfiguration(file);
   }

}
