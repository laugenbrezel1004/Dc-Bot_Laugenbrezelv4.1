package org.example.Messages;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class NeinMessage extends Message {


    public NeinMessage(String name) {
        super(name);
    }



    @Override
    void execute(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equalsIgnoreCase("nein")) {
            event.getChannel().sendMessage("Doch").submit();
        }

    }
}
