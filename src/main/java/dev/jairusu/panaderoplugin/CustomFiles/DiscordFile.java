package dev.jairusu.panaderoplugin.CustomFiles;

import dev.jairusu.panaderoplugin.Configuration;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

   public static List<String> worldGroups(String channelId) {
      ConfigurationSection section = getFileConfig().getConfigurationSection("worldGroups");
      if (section == null) return new ArrayList<>();
      for (String groupName : section.getKeys(false)) {
         if (!groupName.equals(channelId)) continue;
         return section.getStringList(groupName);
      }
      return new ArrayList<>();
   }

}
