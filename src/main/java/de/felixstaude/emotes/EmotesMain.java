package de.felixstaude.emotes;

import de.felixstaude.emotes.commands.EmoteList;
import de.felixstaude.emotes.update.PluginUpdateChecker;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class EmotesMain extends JavaPlugin {
    private static EmotesMain instance;
    private String currentVersion = "v1.1.2";

    @Override
    public void onEnable() {
        new PluginUpdateChecker(this).checkForUpdate(currentVersion);
        instance = this;
        // Plugin startup logic
        // register events
        getServer().getPluginManager().registerEvents(new EmoteManager(this), this);
        getServer().getPluginManager().registerEvents(new PluginUpdateChecker(this), this);
        // register commands
        this.getCommand("emotes").setExecutor(new EmoteList(this));

        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static EmotesMain getInstance() {
        return instance;
    }

    public String getCurrentVersion(){
        return currentVersion;
    }
}
