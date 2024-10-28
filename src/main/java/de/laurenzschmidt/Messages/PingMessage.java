package de.laurenzschmidt.Messages;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingMessage extends Message{
    public PingMessage(String name) {
        super(name);
    }

    @Override
    void execute(MessageReceivedEvent event) {

            event.getChannel().sendMessage("pong").submit();


    }
}