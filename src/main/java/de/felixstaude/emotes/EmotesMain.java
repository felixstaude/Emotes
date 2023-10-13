package de.felixstaude.emotes;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class EmotesMain extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EmoteManager(this), this);
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
