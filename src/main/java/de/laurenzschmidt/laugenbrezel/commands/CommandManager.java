package de.laurenzschmidt.laugenbrezel.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    public static CommandManager create() {
        return new CommandManager();
    }

    private final List<Command> commandList = new ArrayList<>();

    private CommandManager(){
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        for (Command command : commandList) {
            if (command.getName().equalsIgnoreCase(event.getName())) {
                command.execute(event);
            }
        }
    }

    public void registerCommand(Command command) {
        this.commandList.add(command);
    }

}
