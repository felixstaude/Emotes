package de.felixstaude.emotes;

import de.felixstaude.emotes.commands.EmoteList;
import org.bukkit.plugin.java.JavaPlugin;

public final class EmotesMain extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // register events
        getServer().getPluginManager().registerEvents(new EmoteManager(this), this);
        // register commands
        this.getCommand("emotes").setExecutor(new EmoteList(this));

        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
