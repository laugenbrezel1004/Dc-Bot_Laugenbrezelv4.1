package de.laurenzschmidt.laugenbrezel;

import de.laurenzschmidt.laugenbrezel.commands.CommandManager;
import de.laurenzschmidt.laugenbrezel.commands.impl.*;
import de.laurenzschmidt.laugenbrezel.listener.MessageListener;
import de.laurenzschmidt.laugenbrezel.messages.MessageManager;
import de.laurenzschmidt.laugenbrezel.messages.impl.NeinMessage;
import de.laurenzschmidt.laugenbrezel.messages.impl.PingMessage;
import de.laurenzschmidt.laugenbrezel.utils.discord.TokenLoader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Laugenbrezel {

    public static Laugenbrezel create() {
        return new Laugenbrezel();
    }

    private static Laugenbrezel instance;

    private JDA jda;
    private final CommandManager commandManager;
    private final MessageManager messageManager;

    private Laugenbrezel() {
        instance = this;
        this.commandManager = CommandManager.create();
        this.messageManager = MessageManager.create();
    }

    public void start() throws InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(new TokenLoader().getValue());
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.addEventListeners(new MessageListener());
        builder.addEventListeners(this.commandManager);
        builder.addEventListeners(this.messageManager);
        this.jda = builder.build().awaitReady();

        this.loadCommands();
        Message.suppressContentIntentWarning();
    }

    public void stop() {
        // Add shutdown logic
    }

    private void loadCommands() {
        // Get the specific guild
        Guild guild = jda.getGuildById(774668646123438121L);
        if (guild == null) return;

        // Slash-Commands
        guild.upsertCommand("test", "IchBinEinTest").queue();
        commandManager.registerCommand(new TestCommand("test"));
        guild.upsertCommand("startminecraftserver", "Hier kannst du den Minecraftserver zum Leben erwecken!").queue();
        commandManager.registerCommand(new StartMinecraftserverCommand("startminecraftserver"));
        guild.upsertCommand("killminecraftserver", "Hier kannst du den Minecraftserver umbrigen!").queue();
        commandManager.registerCommand(new KillMinecraftserverCommand("killminecraftserver"));
        guild.upsertCommand(Commands
                .slash("sendminecraftserver", "Hier kannst du Befehle übergeben")
                .addOption(OptionType.STRING, "befehl", "Hier kann man dem MC-Server direkt Befehler übermitteln.")
        ).queue();
        commandManager.registerCommand(new SendMinecraftserverCommand("sendminecraftserver"));
        guild.upsertCommand(Commands
                .slash("getweather", "Hier kannst du das aktuelle Wetter sehen.")
                .addOption(OptionType.STRING, "stadt", "Von welcher Stadt möchtest du die Wetterinformationen erfahren?")
        ).queue();
        commandManager.registerCommand(new GetWeatherCommand("getweather"));

        // Message-Responses
        messageManager.registerMessage(new PingMessage("ping"));
        messageManager.registerMessage(new NeinMessage("nein"));
    }

    public static Laugenbrezel getInstance() {
        return instance;
    }

}
