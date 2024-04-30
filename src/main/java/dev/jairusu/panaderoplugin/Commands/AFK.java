package dev.jairusu.panaderoplugin.Commands;

import dev.jairusu.panaderoplugin.Methods.IdleManager;
import dev.jairusu.panaderoplugin.Methods.WorldGroups;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AFK implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
      return new ArrayList<>();
   }

   @Override
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (!(sender instanceof Player)) return true;
      Player player = (Player) sender;
      World playerWorld = player.getWorld();

      List<String> worldGroups = WorldGroups.worldGroups(WorldGroups.survivalWorld());
      if (!worldGroups.contains(playerWorld.getName())) {
         sender.sendMessage("Unknown command. Type \"/help\" for help.");
         return true;
      }

      if (IdleManager.afkTeam().hasEntry(player.getName())) {
         IdleManager.removeToAFKTeam(player, IdleManager.afkTeam());
         return true;
      }

      IdleManager.addToAFKTeam(player, IdleManager.afkTeam());
      return true;
   }

}