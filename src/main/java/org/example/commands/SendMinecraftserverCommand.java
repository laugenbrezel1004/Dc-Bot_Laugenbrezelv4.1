package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import nl.vv32.rcon.Rcon;

import java.io.IOException;
import java.util.Objects;

public class SendMinecraftserverCommand extends Command {
    public SendMinecraftserverCommand(String name) {
        super(name);
    }

    @Override
    void execute(SlashCommandInteractionEvent event) {
        /*  if (!this.connector.isConnected()) {
                    event.reply("Linux-Server ist offline!").setEphemeral(false).queue();
                    return;
                }*/
        String command = Objects.requireNonNull(event.getOption("befehl")).getAsString();
        try {
            //String execute = connector.execute(command);//!!!!!!!!!!!
            //event.reply(execute).setEphemeral(false).queue();

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
