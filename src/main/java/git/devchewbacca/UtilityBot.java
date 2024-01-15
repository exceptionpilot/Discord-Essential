package git.devchewbacca;

import git.devchewbacca.listeners.GuildBoostBannerUpdateListener;
import git.devchewbacca.listeners.GuildMemberJoinListener;
import git.devchewbacca.modules.stats.StatsBannerImageDraw;
import git.devchewbacca.modules.welcome.WelcomeImageDraw;
import git.devchewbacca.utils.config.Configuration;
import git.devchewbacca.utils.FileExporter;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class UtilityBot {

    private static UtilityBot instance;
    private final Configuration configuration;
    private final WelcomeImageDraw welcomeImageDraw;
    private final StatsBannerImageDraw statsBannerImageDraw;
    private final JDA jda;

    public UtilityBot() throws LoginException, IllegalArgumentException, IOException {
        instance = this;

        /**
         * @loading
         * loading all kind of configurations
         */
        FileExporter exporter = new FileExporter();
        exporter.exportResourceFile(".env");
        exporter.exportResourceFile("data.db");
        exporter.exportResourceFile("config.json");
        exporter.exportResourceFile("en-EN.json", "lang");
        exporter.exportResourceFile("GITHUB_DARK.json", "templates");

        Dotenv env = Dotenv.load();
        this.configuration = new Configuration();
        this.welcomeImageDraw = new WelcomeImageDraw();
        this.statsBannerImageDraw = new StatsBannerImageDraw();
        JDABuilder builder = JDABuilder.createDefault(env.get("DISCORD_DEV_TOKEN"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
        builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
        builder.enableCache(CacheFlag.ACTIVITY);
        builder.enableCache(CacheFlag.ONLINE_STATUS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setAutoReconnect(true);
        builder.setMaxReconnectDelay(32);
        builder.addEventListeners(new GuildMemberJoinListener());
        builder.addEventListeners(new GuildBoostBannerUpdateListener());
        this.jda = builder.build();
        jda.addEventListener(new CommandManager());
    }

    public static void main(String[] args) {
        try {
            new UtilityBot();
        } catch (LoginException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JDA getJDA() {
        return jda;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public WelcomeImageDraw getWelcomeImageDraw() {
        return welcomeImageDraw;
    }

    public StatsBannerImageDraw getStatsBannerImageDraw() {
        return statsBannerImageDraw;
    }

    public static UtilityBot getInstance() {
        return instance;
    }
}