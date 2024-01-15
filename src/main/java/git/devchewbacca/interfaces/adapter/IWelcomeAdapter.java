package git.devchewbacca.interfaces.adapter;

import git.devchewbacca.modules.welcome.objects.Welcome;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public interface IWelcomeAdapter {

    void create(Guild guild, TextChannel channel, String templateName);
    void update(Guild guild, TextChannel channel, String templateName, boolean isEnabled);
    Welcome findByGuildId(long guildId);
}
