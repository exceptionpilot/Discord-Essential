package git.devchewbacca.lang.object;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.Objects;

public class PlaceholderManagement {

    private final User user;
    private final Member member;
    private final Guild guild;

    public PlaceholderManagement(User user, Member member, Guild guild) {
        this.user = Objects.requireNonNull(user);
        this.member = Objects.requireNonNull(member);
        this.guild = Objects.requireNonNull(guild);
    }

    public String process(String orginalMesssge) {
        String responseMessage;
        responseMessage = orginalMesssge.replace("{memberMention}", member.getAsMention());
        responseMessage = responseMessage.replace("{memberName}", member.getEffectiveName());
        responseMessage = responseMessage.replace("{userMention}", user.getAsMention());
        responseMessage = responseMessage.replace("{userName}", user.getEffectiveName());
        responseMessage = responseMessage.replace("{guildName}", guild.getName());

        return responseMessage;
    }
}
