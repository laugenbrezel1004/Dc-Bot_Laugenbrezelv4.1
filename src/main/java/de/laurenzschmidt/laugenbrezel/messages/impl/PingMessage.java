package de.laurenzschmidt.laugenbrezel.messages.impl;

import de.laurenzschmidt.laugenbrezel.messages.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingMessage extends Message {

    public PingMessage(String name) {
        super(name);
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        event.getChannel().sendMessage("pong").submit();
    }

}
