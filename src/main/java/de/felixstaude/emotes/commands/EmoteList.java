package de.felixstaude.emotes.commands;

import de.felixstaude.emotes.EmoteManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class EmoteList implements CommandExecutor {

    private final JavaPlugin plugin;

    public EmoteList(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden!");
            return true;
        }

        Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("emotes")) {
            if(strings.length > 0){
                if(strings[0].equalsIgnoreCase("info")){
                    player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Emotes Info:");
                    player.sendMessage(ChatColor.GRAY + "> Groß- und Kleinschreibung sind nicht wichtig bei den Emotes");
                    player.sendMessage(ChatColor.GRAY + "> Solltest du nur Viereckige Kästen statt Emotes sehen, lädt das Texturepack nicht richtig, betrete den Server neu. Sollte das nicht helfen, melde es dem Admin des Servers!");
                }
                return true;
            }

            Map<String, String> replacements = getReplacements();

            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Verfügbare Emotes:");
            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                player.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_GRAY + "> "
                        + ChatColor.GRAY + entry.getKey() +
                        ChatColor.BOLD + "" + ChatColor.DARK_GRAY + " -> "
                        + ChatColor.WHITE + entry.getValue());
            }
        }

        return true;
    }


    private Map<String, String> getReplacements() {
        EmoteManager emoteManager = new EmoteManager(plugin);
        return emoteManager.getReplacements();
    }
}
