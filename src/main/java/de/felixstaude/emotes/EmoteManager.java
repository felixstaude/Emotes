package de.felixstaude.emotes;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;


public class EmoteManager implements Listener {
    private final JavaPlugin plugin;

    public EmoteManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String originalMessage = event.getMessage();

        String replacedMessage = replaceEmotesInString(originalMessage);
        event.setMessage(replacedMessage);

        Bukkit.getConsoleSender().sendMessage(String.format("<%s> %s", player.getName(), originalMessage));
    }

    private Map<String, Emote> getEmotes() {
        Map<String, Emote> emotes = new HashMap<>();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("replacements");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                List<String> name = section.getStringList(key + ".names");
                String charRepresentation = section.getString(key + ".char");
                emotes.put(key, new Emote(name, charRepresentation));
            }
        }
        return emotes;
    }

    private String replaceEmotesInString(String input) {
        Map<String, Emote> emotes = getEmotes();

        for (Emote emote : emotes.values()) {
            for (String alias : emote.getNames()) {
                Pattern pattern = Pattern.compile(Pattern.quote(alias), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(input);
                if (matcher.find()) {
                    input = matcher.replaceAll(Matcher.quoteReplacement(emote.getCharRepresentation()));
                }
            }
        }

        return input;
    }

    private static class Emote {

        private List<String> names;
        private String charRepresentation;

        public Emote(List<String> names, String charRepresentation) {
            this.names = names;
            this.charRepresentation = charRepresentation;
        }

        public List<String> getNames() {
            return names;
        }

        public String getCharRepresentation() {
            return charRepresentation;
        }
    }
}

