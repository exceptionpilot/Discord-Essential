package git.devchewbacca.commands;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.database.manager.TikTokLiveManagement;
import git.devchewbacca.interfaces.ICommand;
import git.devchewbacca.lang.ResponseMessage;
import git.devchewbacca.lang.object.PlaceholderManagement;
import git.devchewbacca.modules.stats.StatsBannerImageDraw;
import git.devchewbacca.modules.tiktok.TikTokLive;
import git.devchewbacca.modules.tiktok.TikTokLiveWorker;
import git.devchewbacca.modules.welcome.WelcomeImageDraw;
import git.devchewbacca.utils.config.Configuration;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.*;
import java.util.List;

public class TikTokCommand implements ICommand {

    private TikTokLiveManagement tikTokLiveManagement = UtilityBot.getInstance().getTikTokLiveManagement();

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
            case "list":

                List<TikTokLive> tikTokLives = tikTokLiveManagement.findMany(event.getGuild().getIdLong());

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("TikTok Lives");
                builder.setColor(Color.decode("#ff0050"));
                for (TikTokLive tikTokLive : tikTokLives) {
                    builder.appendDescription("[" + tikTokLive.getUsername() + "](https://tiktok.com/@" + tikTokLive.getUsername() + ") | " + event.getGuild().getTextChannelById(tikTokLive.getTextChannelId()).getAsMention() + "\n");
                }
                event.replyEmbeds(builder.build()).queue();
                break;

            case "add":

                if (!event.getChannel().getType().isMessage()) {
                    event.deferReply(true)
                            .setContent("Du befindest dich in keinem Text-Kanal!")
                            .queue();
                    break;
                }

                String optionAccount = event.getOption("username").getAsString();
                this.tikTokLiveManagement.insert(event.getGuild().getIdLong(), optionAccount, event.getChannelIdLong());
                TikTokLiveWorker tikTokLiveWorker = new TikTokLiveWorker();
                tikTokLiveWorker.connect(new TikTokLive(event.getGuild().getIdLong(), optionAccount, event.getChannel().getIdLong()));
                event.deferReply(true)
                        .setContent("Neuer Account wurde hinzugefügt!")
                        .queue();
                break;

            case "remove":
                if (!event.getChannel().getType().isMessage()) {
                    event.deferReply(true)
                            .setContent("Du befindest dich in keinem Text-Kanal!")
                            .queue();
                    break;
                }

                String deleteAccount = event.getOption("username").getAsString();
                this.tikTokLiveManagement.delete(event.getGuild().getIdLong(), deleteAccount);
                TikTokLiveWorker tikTokLiveWorkerDel = new TikTokLiveWorker();
                tikTokLiveWorkerDel.disconnect(deleteAccount);
                event.deferReply(true)
                        .setContent("Neuer Account wurde entfernt!")
                        .queue();
                break;

                default:
                    event.deferReply(true).setContent(":x: Diese aktion ist fehlgeschlagen.").queue();
        }

    }
}
