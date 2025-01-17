package de.laurenzschmidt.laugenbrezel.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class Command {

    private final String name;

    public Command(String name) {
        this.name = name;
    }

    protected abstract void execute(SlashCommandInteractionEvent event);

    public String getName() {
        return this.name;
    }

}
