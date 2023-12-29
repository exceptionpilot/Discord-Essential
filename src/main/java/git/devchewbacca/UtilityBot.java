package git.devchewbacca;

import git.devchewbacca.modules.welcome.ImageDraw;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class UtilityBot {

    private static UtilityBot instance;
    private final JDA jda;

    public UtilityBot() throws LoginException, IllegalArgumentException, IOException {
        instance = this;

        Dotenv env = Dotenv.load();
        JDABuilder builder = JDABuilder.createDefault(env.get("DEV_TOKEN"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
        builder.setAutoReconnect(true);
        builder.disableCache(CacheFlag.ACTIVITY);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL); // TODO: add caching management
        builder.setMaxReconnectDelay(32);
        this.jda = builder.build();
        jda.addEventListener(new CommandManager());
    }

    public static void main(String[] args) {
        try {
            // Loading files!
            new UtilityBot();
        } catch (LoginException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JDA getJDA() {
        return jda;
    }

    public static UtilityBot getInstance() {
        return instance;
    }
}