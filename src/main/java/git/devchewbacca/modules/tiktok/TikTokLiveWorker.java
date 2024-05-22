package git.devchewbacca.modules.tiktok;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.database.manager.TikTokLiveManagement;
import io.github.jwdeveloper.tiktok.TikTokLive;
import io.github.jwdeveloper.tiktok.live.LiveClient;
import io.github.jwdeveloper.tiktok.live.LiveRoomInfo;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class TikTokLiveWorker {


    private HashMap<String, CompletableFuture<LiveClient>> tikTokLiveMap = new HashMap<>();
    private TikTokLiveManagement tikTokLiveManagement = UtilityBot.getInstance().getTikTokLiveManagement();

    public TikTokLiveWorker() {}

    public void connect(git.devchewbacca.modules.tiktok.TikTokLive tikTokLive) {
        CompletableFuture<LiveClient> live = TikTokLive.newClient(tikTokLive.getUsername())
                .configure((settings) ->
                {
                    settings.setLogLevel(Level.ALL); // Log level
                    settings.setPrintToConsole(true); // Printing all logs to console even if log level is Level.OFF
                    settings.setRetryOnConnectionFailure(true); // Reconnecting if TikTok user is offline
                    settings.setRetryConnectionTimeout(Duration.ofSeconds(10)); // Timeout before next reconnection
                })
                .onConnected((liveClient, event) -> {

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("[" + tikTokLive.getUsername() + "](https://tiktok.com/@" + tikTokLive.getUsername() + ") hat ein Livestream gestartet.");
                    builder.setColor(Color.decode("#ff0050"));

                    UtilityBot.getInstance().getJDA().getGuildById(tikTokLive.getGuildId())
                            .getTextChannelById(tikTokLive.getTextChannelId())
                            .sendMessageEmbeds(builder.build()).queue();
                })
                .onLiveEnded(((liveClient, tikTokLiveEndedEvent) -> {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("[" + tikTokLive.getUsername() + "](https://tiktok.com/@" + tikTokLive.getUsername() + ") hat den Livestream beendet.");
                    builder.setColor(Color.decode("#ff0050"));

                    UtilityBot.getInstance().getJDA().getGuildById(tikTokLive.getGuildId())
                            .getTextChannelById(tikTokLive.getTextChannelId())
                            .sendMessageEmbeds(builder.build()).queue();

                    git.devchewbacca.modules.tiktok.TikTokLive tikTok = tikTokLiveManagement.find(tikTokLive.getGuildId(), tikTokLive.getUsername());
                    if (tikTok != null) {
                        connect(tikTok);
                    }
                }))
                .buildAndConnectAsync();
    }

    public void disconnect(String username) {
        CompletableFuture<LiveClient> future = tikTokLiveMap.get(username);
        if (future != null) {
            future.cancel(true);
            tikTokLiveMap.remove(username);
        }
    }
}
