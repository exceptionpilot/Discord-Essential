package git.devchewbacca;

import git.devchewbacca.commands.DebugCommand;
import git.devchewbacca.interfaces.ICommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
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

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(Commands.slash("debug", "Get the help page for the " + jda.getSelfUser().getName() + "!"));

        // Context Commands
        //commands.addCommands(Commands.context(Command.Type.USER, "Open Ticket"));

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