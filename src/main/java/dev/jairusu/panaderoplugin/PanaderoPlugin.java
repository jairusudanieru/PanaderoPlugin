package dev.jairusu.panaderoplugin;

import com.github.games647.fastlogin.bukkit.FastLoginBukkit;
import dev.jairusu.panaderoplugin.CustomFiles.DiscordFile;
import dev.jairusu.panaderoplugin.CustomFiles.MessageFile;
import dev.jairusu.panaderoplugin.Methods.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class PanaderoPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        DiscordFile.checkConfigFile();
        MessageFile.checkConfigFile();
        PluginStartup.registerEvents();
        PluginStartup.registerCommands();
        WorldGroups.setDefaults();
        IdleManager.checkPlayerStatus();
        DiscordBot.enableBot();
        LobbyManager.makeInventory();
        JavaPlugin.getPlugin(FastLoginBukkit.class).getCore().setAuthPluginHook(new AuthPluginHook());
        PanaderoPlugin.this.getLogger().info(this.getName() + " Enabled Successfully!");
    }

    @Override
    public void onDisable() {
        DiscordBot.disableBot();
        IdleManager.clearAFKTeamEntries();
        PanaderoPlugin.this.getLogger().info(this.getName() + " Disabled Successfully!");
    }

}
