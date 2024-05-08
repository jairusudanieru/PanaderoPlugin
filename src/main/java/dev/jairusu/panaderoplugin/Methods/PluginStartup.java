package dev.jairusu.panaderoplugin.Methods;

import dev.jairusu.panaderoplugin.Commands.*;
import dev.jairusu.panaderoplugin.Configuration;
import dev.jairusu.panaderoplugin.Events.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.Objects;

public class PluginStartup {

   public static void registerEvents() {
      Bukkit.getPluginManager().registerEvents(new AdvancementGain(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new ChangeWorld(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new CommandSend(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new GriefProtect(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new InventoryMove(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new LaunchPad(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new PlayerAsyncChat(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new PlayerAuth(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new PlayerDamage(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new PlayerDeath(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new PlayerInteract(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new PlayerJoinLeave(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new PlayerMove(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new PlayerRespawn(), Configuration.getPlugin);
      Bukkit.getPluginManager().registerEvents(new PlayerSleep(), Configuration.getPlugin);
   }

   public static void registerCommands() {
      registerCommand("afk", new AFK(), new AFK());
      registerCommand("arenastart", new ArenaStart(), new ArenaStart());
      registerCommand("changepassword", new ChangePassword(), new ChangePassword());
      registerCommand("login", new Login(), new Login());
      registerCommand("panadero", new Main(), new Main());
      registerCommand("register", new Register(), new Register());
      registerCommand("reply", new Reply(), new Reply());
      registerCommand("setarena", new SetArena(), new SetArena());
      registerCommand("setlocation", new SetLocation(), new SetLocation());
      registerCommand("spawn", new Spawn(), new Spawn());
      registerCommand("suicide", new Suicide(), new Suicide());
      registerCommand("whisper", new Whisper(), new Whisper());
   }

   private static void registerCommand(String command, CommandExecutor executor, TabCompleter completer) {
      PluginCommand pluginCommand = Objects.requireNonNull(Bukkit.getPluginCommand(command));
      pluginCommand.setExecutor(executor);
      pluginCommand.setTabCompleter(completer);
   }

}
