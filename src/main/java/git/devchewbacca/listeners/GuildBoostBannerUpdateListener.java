package git.devchewbacca.listeners;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.modules.stats.StatsBannerImageDraw;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.*;

public class GuildBoostBannerUpdateListener extends ListenerAdapter {

    JDA jda = UtilityBot.getInstance().getJDA();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onGuildReady(GuildReadyEvent event) {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        this.logger.info("ScheduledExecutorService just started!");
        Guild guild = event.getGuild();
        ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate(()-> {

                this.logger.info("Check guild is able for update!");
                if (guild.getBoostCount() >= 7) {
                    try {
                        this.logger.info("Updating banner for " + guild.getName()+ "!");
                        guild.getManager().setBanner(Icon.from(new StatsBannerImageDraw().draw(guild), Icon.IconType.PNG)).queue();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        },0, 5,TimeUnit.MINUTES);
    }
}
