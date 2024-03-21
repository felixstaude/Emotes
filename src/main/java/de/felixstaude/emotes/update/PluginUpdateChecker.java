package de.felixstaude.emotes.update;

import de.felixstaude.emotes.EmotesMain;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class PluginUpdateChecker implements Listener {

    private final EmotesMain emotesMain;

    public PluginUpdateChecker(EmotesMain emotesMain) {
        this.emotesMain = emotesMain;
        scheduleUpdateCheckEveryEvenHour();
    }

    private void scheduleUpdateCheckEveryEvenHour() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        ZonedDateTime nextRun = now.withMinute(0).withSecond(0);
        if (now.getHour() % 2 != 0) {
            // skip if hour is odd
            nextRun = nextRun.plusHours(1);
        }
        nextRun = nextRun.plusHours(2 - (now.getHour() % 2)); // check for even hours

        long initialDelay = ChronoUnit.SECONDS.between(now, nextRun);
        long period = 7200L;
        Bukkit.getScheduler().runTaskTimerAsynchronously(emotesMain, () -> checkForUpdate(emotesMain.getCurrentVersion()), initialDelay * 20L, period * 20L);
    }

    public void checkForUpdate(String currentVersion) {
        Bukkit.getScheduler().runTaskAsynchronously(emotesMain, () -> {
            try {
                URL url = new URL("https://api.github.com/repos/felixstaude/emotes/releases/latest");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                connection.disconnect();

                String tagString = "\"tag_name\":\"";
                int tagIndex = response.indexOf(tagString);
                if (tagIndex != -1) {
                    int start = tagIndex + tagString.length();
                    int end = response.indexOf("\"", start);
                    String latestVersion = response.substring(start, end);

                    if (!currentVersion.equals(latestVersion)) {
                        Bukkit.getConsoleSender().sendMessage("There is a new version available: " + latestVersion);

                        // message to every op
                        String message = "There is a new version available: " + latestVersion;
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if (player.isOp()) {
                                player.sendMessage(message);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(event.getPlayer().isOp()){
            checkForUpdate(EmotesMain.getInstance().getCurrentVersion());
        }
    }
}
