package de.laurenzschmidt.listener;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import de.laurenzschmidt.Messages.InsultProvider;
import de.laurenzschmidt.Messages.RegexGenerator;
import de.laurenzschmidt.utils.DiscordUtils;

import java.util.regex.Pattern;

public class MessageListener extends ListenerAdapter {
    private InsultProvider insultProvider;
    private RegexGenerator regexGenerator;

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
    /* if (event.getMessage().getContentRaw().startsWith("#eval"))
        {
            Interpreter interpreter = new Interpreter();
            try {
                interpreter.set("event", event);
                interpreter.eval(event.getMessage().getContentRaw().replace("#eval",""));
                //#eval event.getChannel().sendMessage(System.getProperty("user.name")).submit();
            } catch ( EvalError e) {
                event.getChannel().sendMessage("Befehl gib es nicht:\n " + e.getMessage()).queue();            }
        }
hier interagiere ich mit meinem system -> nicht mit dem linux server über ssh
*/

}


