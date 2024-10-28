package de.laurenzschmidt.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class TestCommand extends Command{
    public TestCommand(String name) {
        super(name);
    }

    @Override
    void execute(SlashCommandInteractionEvent event) {
        event.reply("Du hast den Testbefehl ausgeführt!").setEphemeral(false).queue();

    }
}
