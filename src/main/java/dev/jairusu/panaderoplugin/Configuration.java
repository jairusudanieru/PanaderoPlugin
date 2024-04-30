package dev.jairusu.panaderoplugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Configuration {

   public static JavaPlugin getPlugin = JavaPlugin.getPlugin(PanaderoPlugin.class);

   public static String getString(String path) {
      return getPlugin.getConfig().getString(path);
   }

   public static Integer getInteger(String path) {
      return getPlugin.getConfig().getInt(path);
   }

   public static Boolean getBoolean(String path) {
      return getPlugin.getConfig().getBoolean(path);
   }

   public static Double getDouble(String path) {
      return getPlugin.getConfig().getDouble(path);
   }

   public static Long getLong(String path) {
      return getPlugin.getConfig().getLong(path);
   }

   public static Location getLocation(String path) {
      return getPlugin.getConfig().getLocation(path);
   }

   public static ConfigurationSection getConfigSection(String path) {
      return getPlugin.getConfig().getConfigurationSection(path);
   }

   public static List<String> getStringList(String path) {
      return getPlugin.getConfig().getStringList(path);
   }

   public static Component text(String text) {
      MiniMessage miniMessage = MiniMessage.miniMessage();
      return miniMessage.deserialize(text);
   }

   public static Boolean isAuthenticated(Player player) {
      return !player.hasMetadata("unlogged");
   }

   public static void setLocation(String key, Location location) {
      getPlugin.getConfig().set(key, location);
      getPlugin.saveConfig();
   }


}
