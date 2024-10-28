package de.laurenzschmidt.laugenbrezel.listener;

import de.laurenzschmidt.laugenbrezel.utils.InsultProvider;
import de.laurenzschmidt.laugenbrezel.utils.RegexGenerator;
import de.laurenzschmidt.laugenbrezel.utils.discord.DiscordUtils;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.regex.Pattern;

public class MessageListener extends ListenerAdapter {

    private final InsultProvider insultProvider;
    private final RegexGenerator regexGenerator;

    public MessageListener() {
        this.insultProvider = new InsultProvider();
        this.regexGenerator = new RegexGenerator();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        for (String word : insultProvider.getWords()) {
            Pattern pattern = Pattern.compile((regexGenerator.get(word)));
            if (event.getMessage().getContentRaw().toLowerCase().matches(pattern.pattern())) {
                User user = event.getAuthor();
                String nameUser = event.getAuthor().getName();
                MessageChannel channel = event.getChannel();
                DiscordUtils.deleteFromUser(channel, user, 1);
                event.getChannel().sendMessage("The message from user: " + nameUser + " has been deleted, due to offensive content. The user got also muted due to this incident").queue();
                DiscordUtils.addRoleToMember(event.getGuild(), event.getMember(), "muted");
            }
        }
    }

}


