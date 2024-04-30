package dev.jairusu.panaderoplugin.Methods;

import dev.jairusu.panaderoplugin.Configuration;
import dev.jairusu.panaderoplugin.CustomFiles.DiscordFile;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class DiscordBot extends ListenerAdapter {

   public static JDA JAVADISCORD;

   public void enableBot() {
      String botToken = DiscordFile.getFileConfig().getString("botToken");
      if (botToken == null || botToken.equals("botToken") || botToken.isEmpty()) return;
      String activityName = DiscordFile.getFileConfig().getString("activityName");
      if (activityName == null || activityName.isEmpty()) activityName = "Minecraft";
      Activity activity = Activity.playing(activityName);
      if (activityName.contains("Listening") || activityName.contains("listening")) {
         activityName = activityName.replace("Listening ","").replace("listening ","");
         activity = Activity.listening(activityName);
      }

      try {
         JDA jda = JDABuilder.createDefault(botToken, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                 .setActivity(activity)
                 .addEventListeners(this)
                 .build();
         jda.awaitReady();
         JAVADISCORD = jda;
         Configuration.getPlugin.getLogger().info("[PandeDiscord] Discord bot successfully enabled!");
      } catch (Exception error) {
         Configuration.getPlugin.getLogger().info(error.toString());
      }
   }

   public void disableBot() {
      String botToken = DiscordFile.getFileConfig().getString("botToken");
      if (botToken == null || botToken.equals("botToken") || botToken.isEmpty()) return;

      try {
         JAVADISCORD.shutdown();
         Bukkit.getLogger().info("[PandeDiscord] Discord bot successfully disabled!");
      } catch (Exception error) {
         Configuration.getPlugin.getLogger().info(error.toString());
      }
   }

   @Override
   public void onMessageReceived(@NotNull MessageReceivedEvent event) {
      String message = event.getMessage().getContentDisplay();
      if (message.isEmpty() || message.isBlank()) return;

      if (event.getAuthor().isBot() || event.getAuthor().isSystem()) return;
      String author = event.getAuthor().getName();

      String channel = event.getChannel().getId();
      String fullMessage = DiscordFile.getFileConfig().getString("message.messageFormat");
      if (fullMessage == null) return;
      fullMessage = fullMessage.replace("%author%",author)
              .replace("%message%",message);

      List<String> worldGroups = DiscordFile.worldGroups(channel);
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroups.contains(onlinePlayer.getWorld().getName())) continue;
         onlinePlayer.sendMessage(Configuration.text(fullMessage));
      }

   }

}