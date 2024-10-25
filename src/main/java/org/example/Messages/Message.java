package org.example.Messages;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.JDABuilder;

import net.dv8tion.jda.api.hooks.ListenerAdapter;


public abstract class Message {
    private final String name;

    public Message(String name) {
        this.name = name;
    }



    abstract void execute(MessageReceivedEvent event);

    public String getName() {
        return this.name;
    }
}
