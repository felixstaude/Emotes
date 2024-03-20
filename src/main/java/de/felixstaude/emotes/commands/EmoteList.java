package de.felixstaude.emotes.commands;

import de.felixstaude.emotes.EmoteManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class EmoteList implements CommandExecutor {

    private final JavaPlugin plugin;

    public EmoteList(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only used by players!");
            return true;
        }

        Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("emotes")) {

            if(strings.length > 0){
                if(strings[0].equalsIgnoreCase("info")){
                    player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Emotes Info:");
                    player.sendMessage(ChatColor.GRAY + "> The emotes are not case sensitive");
                    player.sendMessage(ChatColor.GRAY + "> If you only see square boxes instead of emotes, the texture pack is not loading correctly. Please re-enter the server. If that doesn't help, report it to the server admin!");
                }
                return true;
            }


            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Available emotes:");
        }
        return true;
    }


}
