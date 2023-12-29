package git.devchewbacca.commands;

import git.devchewbacca.interfaces.ICommand;
import git.devchewbacca.modules.welcome.ImageDraw;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;

public class TestCommand implements ICommand {

    @Override
    public void execute(SlashCommandInteractionEvent event, User user) {
        try {
            event.reply("Hi!")
                    .addFiles(FileUpload.fromData(new ImageDraw().draw(user), "test.png"))
                    .queue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
