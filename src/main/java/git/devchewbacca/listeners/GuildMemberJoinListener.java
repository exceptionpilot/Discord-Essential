package git.devchewbacca.listeners;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.database.manager.WelcomeManagement;
import git.devchewbacca.lang.ResponseMessage;
import git.devchewbacca.lang.object.PlaceholderManagement;
import git.devchewbacca.modules.welcome.WelcomeImageDraw;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.FileUpload;

import java.text.NumberFormat;

public class GuildMemberJoinListener extends ListenerAdapter {

    private WelcomeImageDraw welcomeImageDraw = UtilityBot.getInstance().getWelcomeImageDraw();
    private WelcomeManagement welcomeManagement = UtilityBot.getInstance().getWelcomeManagement();

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        if (welcomeManagement.find(event.getGuild().getIdLong()) == 0L) return;
        if (event.getGuild().getTextChannelById(welcomeManagement.find(event.getGuild().getIdLong())) !=null) {

            TextChannel channel = event.getGuild().getTextChannelById(welcomeManagement.find(event.getGuild().getIdLong()));
            MessageCreateAction messageCreateAction = channel.sendMessage(new ResponseMessage(
                    new PlaceholderManagement(
                            event.getUser(),
                            event.getMember(),
                            event.getGuild()
                    )).getMessage("welcome.join")
            );
            try {
                messageCreateAction.addFiles(FileUpload.fromData(
                        this.welcomeImageDraw.asyncDraw(
                                event.getUser()),
                        "welcome.png")
                ).queue();
            } catch (Exception e) {
                messageCreateAction.queue();
            }
        }
    }
}
