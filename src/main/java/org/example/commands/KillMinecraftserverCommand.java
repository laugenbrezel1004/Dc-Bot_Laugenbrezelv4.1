package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KillMinecraftserverCommand extends Command {
    public KillMinecraftserverCommand(String name) {
        super(name);
    }

    @Override
    void execute(SlashCommandInteractionEvent event) {
        int pid;
        try {
            Process vms = Runtime.getRuntime().exec("jps");
            BufferedReader reader = new BufferedReader(new InputStreamReader(vms.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Hier kannst du die Ausgabe validieren
                if (line.contains("Laugenbrezel-v4.0-shaded.jar")) {
                    System.out.println("Das Laugenbrezel-v4.0-shaded.jar-Programm wurde gefunden!");
                } else if (line.contains("paper")) {
                    String[] parts = line.split("\\s");
                    pid = Integer.parseInt(parts[0]);
                    Runtime.getRuntime().exec("kill -9 " + pid);
                    event.reply("Der Minecraftserver hat den Löffel abgegeben!").setEphemeral(false).queue();
                } else if (line.contains("Jps")) {
                    System.out.println("Das Jps-Programm wurde gefunden!");
                }

                // Hier könntest du weitere spezifische Validierungen vornehmen
            }
            //connector.execute("stop"); //!!!!!!!!!!!


        } catch (IOException exception) {
            event.reply("Fehler beim Beenden des Servers.\nBitte an den zuständigen Admin wenden!!!").setEphemeral(false).queue();
            throw new RuntimeException(exception);
        }
    }
}
