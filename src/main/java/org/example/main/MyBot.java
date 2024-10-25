
package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.Messages.MessageManager;
//import org.example.Messages.MuteMessage;
import org.example.Messages.NeinMessage;
import org.example.Messages.PingMessage;
import org.example.commands.*;
import org.example.listener.MessageListener;

public class MyBot {


    //private final SSHConnector connector;

    public MyBot() throws InterruptedException {

        Message.suppressContentIntentWarning();
/*     ---> Wenn der Bot direkt auf dem Linux-Server läuft, kann man die Befehle nicht über ssh an ihn weitergeben, statdessenn wird
"runtime" von java genutzt. -> SSH nur bei verbindungen die über localhost hinausgehen
        this.connector = new SSHConnector("localhost", 5678, "minecraft", "minecraft");
        try {
            this.connector.connect();
        } catch (Exception exception) {
            System.err.printf("Die Verbindung konnte nicht aufgebaut werden... Error: %s", exception.getMessage());
        }*/
// hier muss noch was gemacht werden
        JDABuilder builder = JDABuilder.createDefault();
        builder.addEventListeners(new MessageListener());
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        CommandManager commandManager = new CommandManager();
        MessageManager messageManager = new MessageManager();
        builder.addEventListeners(commandManager);
        builder.addEventListeners(messageManager);

        JDA jda = builder.build().awaitReady();
        Guild guild = jda.getGuildById(774668646123438121L);
        if (guild != null) {
            guild.upsertCommand("test", "IchBinEinTest").queue();
            commandManager.registerCommand(new TestCommand("test"));
            guild.upsertCommand("startminecraftserver", "Hier kannst du den Minecraftserver zum Leben erwecken!").queue();
            commandManager.registerCommand(new StartMinecraftserverCommand("startminecraftserver"));
            guild.upsertCommand("killminecraftserver", "Hier kannst du den Minecraftserver umbrigen!").queue();
            commandManager.registerCommand(new KillMinecraftserverCommand("killminecraftserver"));
            guild.upsertCommand(Commands.slash("sendminecraftserver", "Hier kannst du Befehle übergeben").addOption(OptionType.STRING, "befehl", "Hier kann man dem MC-Server direkt Befehler übermitteln.")).queue();
            commandManager.registerCommand(new SendMinecraftserverCommand("sendminecraftserver"));
            guild.upsertCommand(Commands.slash("getweather", "Hier kannst du das aktuelle Wetter sehen.").addOption(OptionType.STRING, "stadt", "Von welcher Stadt möchtest du die Wetterinformationen erfahren?")).queue();
            commandManager.registerCommand(new GetWeatherCommand("getweather"));

            messageManager.registerMessage(new PingMessage("ping"));
            messageManager.registerMessage(new NeinMessage("nein"));

        }

        //Runtime.getRuntime().addShutdownHook(new Thread(this.connector::disconnect)); //Die SSH-Verbindung wird geschlossen, wenn das Programm beendet wird

    }

}




