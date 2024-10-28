package de.laurenzschmidt.laugenbrezel.messages.impl;

import de.laurenzschmidt.laugenbrezel.messages.Message;
import de.laurenzschmidt.laugenbrezel.utils.discord.DiscordUtils;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MuteMessage extends Message {

    public MuteMessage(String name) {
        super(name);
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        // Ignore messages from bots
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().equalsIgnoreCase("arschloch")) {
            User user = event.getAuthor();
            String nameUser = event.getAuthor().getName();
            MessageChannel channel = event.getChannel();
            DiscordUtils.deleteFromUser(channel, user, 1);
            event.getChannel().sendMessage("The message from user: " + nameUser + " has been deleted, due to offensive content. The user got also muted due to this incident").queue();
            DiscordUtils.addRoleToMember(event.getGuild(), event.getMember(), "muted");
        }
    }

}
