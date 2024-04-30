package dev.jairusu.panaderoplugin;

import dev.jairusu.panaderoplugin.Methods.Authentication;
import org.bukkit.plugin.java.JavaPlugin;

public final class PanaderoPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Authentication.getFastLoginBukkit.getCore().setAuthPluginHook(new Authentication());
        PanaderoPlugin.this.getLogger().info(this.getName() + " Enabled Successfully!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PanaderoPlugin.this.getLogger().info(this.getName() + " Disabled Successfully!");
    }
}
