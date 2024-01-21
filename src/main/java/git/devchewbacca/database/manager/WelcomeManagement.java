package git.devchewbacca.database.manager;

import git.devchewbacca.interfaces.adapter.IWelcomeAdapter;
import git.devchewbacca.modules.welcome.objects.Welcome;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class WelcomeManagement implements IWelcomeAdapter {

    private final IWelcomeAdapter welcomeAdapter;

    public WelcomeManagement(IWelcomeAdapter welcomeAdapter) {
        this.welcomeAdapter = welcomeAdapter;
    }

    @Override
    public void create(Guild guild, TextChannel channel, String templateName) {
        this.welcomeAdapter.create(guild,channel,templateName);
    }

    @Override
    public void update(Guild guild, TextChannel channel, String templateName, boolean isEnabled) {
        this.welcomeAdapter.update(guild,channel,templateName,isEnabled);
    }

    @Override
    public Welcome findByGuildId(long guildId) {
        return this.findByGuildId(guildId);
    }
}
