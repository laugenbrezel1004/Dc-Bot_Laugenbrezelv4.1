package de.laurenzschmidt.utils;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DiscordUtils {

    public static void deleteFromUser(MessageChannel channel, User author, int amount) {
        List<Message> messages = new ArrayList<>();
        // loop asynchronously through the channels message history
        channel.getIterableHistory().forEachAsync(message -> {
            // check if the message is from the desired author
            if (message.getAuthor().equals(author))
                messages.add(message);
            return messages.size() < amount;
        }).thenRun(
                // purging the messages
                () -> channel.purgeMessages(messages)
        );
    }

    public static void addRoleToMember(Guild guild, Member member, String roleName) {
        // Rufen Sie die Rolle ab
        Role role = guild.getRolesByName(roleName, true).stream().findFirst().orElse(null);
        if (role == null) {
            System.out.println("Rolle '" + roleName + "' nicht gefunden.");
            return;
        }
        // Fügen Sie die Rolle zum Mitglied hinzu
        guild.addRoleToMember(member, role).queue(
                success -> System.out.println("Rolle erfolgreich hinzugefügt."),
                error -> System.out.println("Fehler beim Hinzufügen der Rolle: " + error.getMessage()));
        guild.removeRoleFromMember(member, role).queueAfter(
                60, TimeUnit.SECONDS,
                removeSuccess -> System.out.println("Rolle erfolgreich entfernt."),
                error -> System.out.println("Fehler beim Entfernen der Rolle: " + error.getMessage())
        );
    }

}
