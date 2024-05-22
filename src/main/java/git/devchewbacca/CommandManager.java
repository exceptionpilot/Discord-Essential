package git.devchewbacca;

import git.devchewbacca.commands.AiCommand;
import git.devchewbacca.commands.DebugCommand;
import git.devchewbacca.commands.TikTokCommand;
import git.devchewbacca.commands.WelcomeCommand;
import git.devchewbacca.interfaces.ICommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager extends ListenerAdapter {

    private final Map<String, ICommand> commandsMap;
    private JDA jda;

    public CommandManager() {
        jda = UtilityBot.getInstance().getJDA();

        commandsMap = new ConcurrentHashMap<>();

        commandsMap.put("debug", new DebugCommand());
        commandsMap.put("tiktok", new TikTokCommand());
        commandsMap.put("ai", new AiCommand());
        commandsMap.put("welcome", new WelcomeCommand());

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(Commands.slash("debug", "Get the help page for the " + jda.getSelfUser().getName() + "!"));

        commands.addCommands(Commands.slash("tiktok", "Erhalte Live benachrichtigungen.")
                .addSubcommands(new SubcommandData("list", "Liste alle Accounts auf!"))
                .addSubcommands(new SubcommandData("add", "Folge einem TikTok Account!").addOption(OptionType.STRING, "username", "Setze deinen Accountnamen!"))
                .addSubcommands(new SubcommandData("remove", "Entferne einen TikTok Account!").addOption(OptionType.STRING, "username", "Wähle den Account!"))
        );

        commands.addCommands(Commands.slash("ai", "unset")
                .addSubcommands(new SubcommandData("set", "unset"))
                .addSubcommands(new SubcommandData("unset", "unset"))
        );

        commands.addCommands(Commands.slash("welcome", "unset")
                .addSubcommands(new SubcommandData("set", "unset"))
                .addSubcommands(new SubcommandData("unset", "unset"))
        );

        commands.queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String commandName = event.getName();

        ICommand command;

        if ((command = commandsMap.get(commandName)) != null) {
            command.execute(event, event.getUser());
        }

    }
}