package git.devchewbacca.listeners;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import git.devchewbacca.UtilityBot;
import git.devchewbacca.database.manager.AiManagement;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserChannelChatListener extends ListenerAdapter {

    private OpenAiService service = new OpenAiService("sk-proj-bszz4pB5Db0K8ujRSwj1T3BlbkFJDDEFMuQrsb7VnvHwrOQz");
    private List<ChatMessage> messages = new ArrayList<>();
    private AiManagement aiManagement = UtilityBot.getInstance().getAiManagement();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild()) return;
        if (event.getChannel().getIdLong() == aiManagement.find(event.getGuild().getIdLong())) {

        Channel channel = event.getChannel();
        if (!channel.getType().isMessage()) return;
        if (!event.getAuthor().isBot()) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (event.getMessage().getContentDisplay().equalsIgnoreCase(".wipecache")) {
                    messages.clear();
                    event.getMessage().reply("Ich habe alle vergangenen nachrichten gelöscht.").queue();
                }
            }

            if (event.getMessage().getContentDisplay().equalsIgnoreCase(".help ai")) {
                event.getChannel().sendMessage(
                        "**Ai-Commands:** \n" +
                        "`.` - Um deine Nachricht zu ignorieren. \n" +
                                "`.wipecache` - Um den Ai-Chat zu löschen."
                        )
                        .queue();
            }

            if (event.getMessage().getContentDisplay().startsWith(".")) return;

            ChatMessage userMessage = new ChatMessage(
                    ChatMessageRole.USER.value(),
                    event.getMessage().getContentDisplay()
            );

            messages.add(userMessage);

            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-3.5-turbo-0613")
                    .messages(messages)
                    .maxTokens(256)
                    .build();

            ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
            messages.add(responseMessage);
            event.getChannel().sendTyping().queue();
            event.getMessage().reply(responseMessage.getContent()).queue();
            }
        }
    }
}
