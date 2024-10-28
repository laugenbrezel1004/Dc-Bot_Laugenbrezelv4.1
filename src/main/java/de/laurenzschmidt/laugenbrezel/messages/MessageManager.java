package de.laurenzschmidt.laugenbrezel.messages;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageManager extends ListenerAdapter {

    public static MessageManager create() {
        return new MessageManager();
    }

    private final List<Message> messageList = new ArrayList<>();

    private MessageManager() {
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        for (Message message : messageList) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(message.getName())) {
                message.execute(event);
            }
        }
    }

    public void registerMessage(Message message) {
        this.messageList.add(message);
    }

}
