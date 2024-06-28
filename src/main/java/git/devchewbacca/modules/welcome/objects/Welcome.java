package git.devchewbacca.modules.welcome.objects;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Welcome {

    private final Guild guild;
    private final TextChannel textChannel;
    private final String templateName;
    private final boolean isEnabled;

    public Welcome(Guild guild, TextChannel textChannel, String templateName, boolean isEnabled) {
        this.guild = guild;
        this.textChannel = textChannel;
        this.templateName = templateName;
        this.isEnabled = isEnabled;
    }

    public Guild getGuild() {
        return guild;
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public String getTemplateName() {
        return templateName;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
