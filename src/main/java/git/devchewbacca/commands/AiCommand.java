package git.devchewbacca.commands;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.database.manager.AiManagement;
import git.devchewbacca.interfaces.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AiCommand implements ICommand {

    private AiManagement aiManagement = UtilityBot.getInstance().getAiManagement();

    @Override
    public void execute(SlashCommandInteractionEvent event, User user) {

        if (!event.isFromGuild()) return;

        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
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
                this.aiManagement.insert(event.getGuild().getIdLong(), event.getChannelIdLong());
                event.deferReply(true)
                        .setContent("Ki-Chat-Kanal wurde gesetzt!")
                        .queue();
                break;

            case "unset":

                this.aiManagement.delete(event.getGuild().getIdLong());
                event.deferReply(true)
                        .setContent("Ki-Chat-Kanal wurde zurückgesetzt!")
                        .queue();
                break;

            default:
                event.deferReply(true).setContent(":x: Diese aktion ist fehlgeschlagen.").queue();
        }

    }
}