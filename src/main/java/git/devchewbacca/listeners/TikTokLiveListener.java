package git.devchewbacca.listeners;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.database.manager.TikTokLiveManagement;
import git.devchewbacca.modules.tiktok.TikTokLiveWorker;
import io.github.jwdeveloper.tiktok.TikTokLive;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class TikTokLiveListener extends ListenerAdapter {

    private TikTokLiveManagement tikTokLiveManagement = UtilityBot.getInstance().getTikTokLiveManagement();
    private JDA jda = UtilityBot.getInstance().getJDA();

    public TikTokLiveListener () {
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<git.devchewbacca.modules.tiktok.TikTokLive> tikTokLives = tikTokLiveManagement.findMany(event.getGuild().getIdLong());
        System.out.println(tikTokLives.size());

        TikTokLiveWorker tikTokLiveWorker = new TikTokLiveWorker();
        for (git.devchewbacca.modules.tiktok.TikTokLive tikTokLive : tikTokLives) {

            tikTokLiveWorker.connect(tikTokLive);

        }

    }
}
