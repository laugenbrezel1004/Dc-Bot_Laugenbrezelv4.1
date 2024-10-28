package de.laurenzschmidt.message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


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
