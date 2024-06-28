package git.devchewbacca.commands;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.database.manager.WelcomeManagement;
import git.devchewbacca.interfaces.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class WelcomeCommand implements ICommand {

    private WelcomeManagement welcomeManagement = UtilityBot.getInstance().getWelcomeManagement();

    @Override
    public void execute(SlashCommandInteractionEvent event, User user) {

        if (!event.isFromGuild()) return;

        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            event.deferReply(true)
                    .setContent("Du hast nicht die benötigten Berechtigungen!")
                    .queue();
            return;
        }

        switch (event.getSubcommandName()) {
            case "set":

                if (!event.getChannel().getType().isMessage()) {
                    event.deferReply(true)
                            .setContent("Du befindest dich in keinem Text-Kanal!")
                            .queue();
                    break;
                }
                this.welcomeManagement.insert(event.getGuild().getIdLong(), event.getChannelIdLong());
                event.deferReply(true)
                        .setContent("Willkommens-Kanal wurde gesetzt!")
                        .queue();
                break;

            case "unset":

                this.welcomeManagement.delete(event.getGuild().getIdLong());
                event.deferReply(true)
                        .setContent("Willkommens-Kanal wurde zurückgesetzt!")
                        .queue();
                break;

            default:
                event.deferReply(true).setContent(":x: Diese aktion ist fehlgeschlagen.").queue();
        }

    }
}