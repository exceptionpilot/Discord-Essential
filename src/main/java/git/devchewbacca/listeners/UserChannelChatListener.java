package git.devchewbacca.listeners;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import git.devchewbacca.UtilityBot;
import git.devchewbacca.database.manager.AiManagement;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class UserChannelChatListener extends ListenerAdapter {

    private OpenAiService service = new OpenAiService(UtilityBot.getInstance().getEnv().get("OPENAI_API_TOKEN"));;
    private HashMap<Long, ArrayList<ChatMessage>> messages = new HashMap<>();
    private AiManagement aiManagement = UtilityBot.getInstance().getAiManagement();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (!event.isFromGuild()) return;
        if (event.getChannel().getIdLong() == aiManagement.find(event.getGuild().getIdLong())) {

        Channel channel = event.getChannel();
        if (!channel.getType().isMessage()) return;


        ChatMessage userMessage = new ChatMessage(
                    ChatMessageRole.USER.value(),
                    event.getMessage().getContentDisplay()
        );

        ArrayList<ChatMessage> messagesArray = messages.get(event.getGuild().getIdLong()) == null ? new ArrayList<>() : messages.get(event.getGuild().getIdLong());
        messagesArray.add(userMessage);
        messages.put(event.getGuild().getIdLong(), messagesArray);

            if (!event.getAuthor().isBot()) {

                if (event.getMessage().getContentDisplay().equalsIgnoreCase(".wipecache")) {
                    messages.clear();
                    event.getMessage().reply("Ich habe alle vergangenen nachrichten gelöscht.").queue();
                }

                if (event.getMessage().getContentDisplay().equalsIgnoreCase(".help ai")) {
                    event.getChannel().sendMessage(
                                    "**Ai-Commands:** \n" +
                                            "`.` - Um deine Nachricht zu ignorieren. \n" +
                                            "`.wipecache` - Um den Ai-Chat zu löschen."
                            )
                            .queue();
                    messages.remove(event.getGuild().getIdLong());
                }
                if (event.getMessage().getContentDisplay().startsWith(".")) return;



            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-3.5-turbo-0613")
                    .messages(messages.get(event.getGuild().getIdLong()))
                    .maxTokens(256)
                    .build();

            ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
            messagesArray.add(responseMessage);
            event.getChannel().sendTyping().queue();
            event.getMessage().reply(responseMessage.getContent()).queue();
            }
        }
    }
}
