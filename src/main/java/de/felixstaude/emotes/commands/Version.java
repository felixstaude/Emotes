package de.felixstaude.emotes.commands;

import de.felixstaude.emotes.EmotesMain;
import de.felixstaude.emotes.update.PluginUpdateChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Version implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only used by players!");
            return true;
        }
        Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("emotes")) {
            if(strings.length > 0) {
                if (strings[0].equalsIgnoreCase("version")) {
                    new PluginUpdateChecker(EmotesMain.getInstance()).checkForUpdate(EmotesMain.getInstance().getCurrentVersion());
                }
                return true;
            }
        }

            return false;
    }
}
