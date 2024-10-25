/*package org.example.Messages;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.utils.DiscordUtils;

import java.util.ArrayList;
import java.util.List;

public class MuteMessage extends Message {


    public MuteMessage(String name) {
        super(name);
    }


    @Override
    void execute(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return; // Ignoriert Nachrichten von anderen Bots

        if (event.getMessage().getContentRaw().equalsIgnoreCase("arschloch")) {
            //String id = event.getMessageId();
            User user = event.getAuthor();
            String nameUser = event.getAuthor().getName();
            MessageChannel channel = event.getChannel();
            DiscordUtils.deleteFromUser(channel, user, 1);
            event.getChannel().sendMessage("The message from user: " + nameUser + " has been deleted, due to offensive content. The user got also muted due to this incident").queue();
            DiscordUtils.addRoleToMember(event.getGuild(), event.getMember(), "muted");
            return;
        }
    }
}
*/