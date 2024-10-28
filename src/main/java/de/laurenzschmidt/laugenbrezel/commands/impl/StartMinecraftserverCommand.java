package de.laurenzschmidt.laugenbrezel.commands.impl;

import de.laurenzschmidt.laugenbrezel.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StartMinecraftserverCommand extends Command {

    public StartMinecraftserverCommand(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        // TODO: Add check if server is online!
        try {
            Runtime.getRuntime().exec("java -jar paper-1.20.4-381.jar nogui");
            event.reply("Der Minecraftserver hat das Licht der Welt erblickt!").setEphemeral(false).queue();
        } catch (Exception exception) {
            event.reply("Fehler beim Starten des Servers.\nBitte an den zuständigen Admin wenden!!!").setEphemeral(false).queue();
            throw new RuntimeException(exception);
        }
    }

}
