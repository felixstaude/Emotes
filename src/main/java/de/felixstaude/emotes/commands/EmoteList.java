package de.felixstaude.emotes.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class EmoteList implements CommandExecutor {

    private final JavaPlugin plugin;

    public EmoteList(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be used by players!");
            return true;
        }

        Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("emotes")) {

            if(strings.length > 0){
                if(strings[0].equalsIgnoreCase("info")){
                    player.sendMessage("§3§lEmotes Info:");
                    player.sendMessage("§7> The emotes are not case sensitive");
                    player.sendMessage("§7> If you only see square boxes instead of emotes, the texture pack is not loading correctly. Please re-enter the server. If that doesn't help, report it to the server admin!");
                    return true;
                }
            }

            player.sendMessage("§3§lAvailable emotes:");
            player.sendMessage(" ");
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("replacements");
            if (section != null) {
                for (String key : section.getKeys(false)) {
                    List<String> names = section.getStringList(key + ".names");
                    String charRepresentation = section.getString(key + ".char");
                    player.sendMessage(charRepresentation + " §8- §6" + String.join(", ", names));
                }
            }

            return true;
        }
        return false;
    }
}
