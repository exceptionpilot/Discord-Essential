package git.devchewbacca.commands;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.interfaces.ICommand;
import git.devchewbacca.lang.ResponseMessage;
import git.devchewbacca.lang.object.PlaceholderManagement;
import git.devchewbacca.modules.stats.StatsBannerImageDraw;
import git.devchewbacca.modules.welcome.WelcomeImageDraw;
import git.devchewbacca.utils.config.Configuration;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;

public class DebugCommand implements ICommand {

    Configuration configuration = UtilityBot.getInstance().getConfiguration();
    WelcomeImageDraw welcomeImageDraw = UtilityBot.getInstance().getWelcomeImageDraw();
    private StatsBannerImageDraw statsBannerImage = UtilityBot.getInstance().getStatsBannerImageDraw();

    @Override
    public void execute(SlashCommandInteractionEvent event, User user) {
        try {
            event.reply(new ResponseMessage(
                    new PlaceholderManagement(
                            user,
                            event.getMember(),
                            event.getGuild()
                    )).getMessage("welcome.join")
            ).addFiles(
                    FileUpload.fromData(
                            statsBannerImage.draw(event.getGuild()),
                            "welcome.png"))
                    .queue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
