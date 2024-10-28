package de.laurenzschmidt.laugenbrezel.commands.impl;

import de.laurenzschmidt.laugenbrezel.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import nl.vv32.rcon.Rcon;

import java.io.IOException;
import java.util.Objects;

public class SendMinecraftserverCommand extends Command {

    public SendMinecraftserverCommand(String name) {
        super(name);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        // TODO: Add check if server is online!
        String command = Objects.requireNonNull(event.getOption("befehl")).getAsString();
        try {
            try (Rcon rcon = Rcon.open("localhost", 25575)) {
                if (rcon.authenticate("minecraft")) {
                    rcon.sendCommand(command);
                }
            }
        } catch (IOException exception) {
            event.reply("Es ist ein Fehler aufgetreten.\nBitte an den zuständigen Admin wenden!!!").setEphemeral(false).queue();
            throw new RuntimeException(exception);
        }
    }

}
