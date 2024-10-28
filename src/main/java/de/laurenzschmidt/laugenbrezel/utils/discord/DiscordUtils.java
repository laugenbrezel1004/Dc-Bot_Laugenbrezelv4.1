package de.laurenzschmidt.laugenbrezel.utils.discord;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.ArrayList;
import java.util.List;

public class DiscordUtils {

    /**
     * Deletes a specified number of messages from a particular user in a given message channel.
     * <p>
     * This method asynchronously iterates through the channel's message history,
     * collects messages from the specified author up to the desired amount,
     * and then purges these messages from the channel.
     * </p>
     *
     * @param channel The MessageChannel from which to delete messages
     * @param author  The User whose messages should be deleted
     * @param amount  The maximum number of messages to delete
     */
    public static void deleteFromUser(MessageChannel channel, User author, int amount) {
        List<Message> messages = new ArrayList<>();
        // Asynchronously iterate through the channel's message history
        channel.getIterableHistory().forEachAsync(message -> {
            // Check if the message is from the specified author
            if (message.getAuthor().equals(author)) {
                messages.add(message);
            }
            // Continue iterating if we haven't collected enough messages yet
            return messages.size() < amount;
        }).thenRun(() -> {
            // After collecting messages, purge them from the channel
            channel.purgeMessages(messages);
        });
    }

    /**
     * Adds a role to a member in a guild.
     *
     * @param guild    The guild where the role should be added
     * @param member   The member to whom the role should be added
     * @param roleName The name of the role to be added
     */
    public static void addRoleToMember(Guild guild, Member member, String roleName) {
        // Find the role by name
        Role role = guild.getRolesByName(roleName, true).stream().findFirst().orElse(null);

        // If the role exists, add it to the member
        if (role != null) {
            guild.addRoleToMember(member, role).queue(
                    success -> System.out.println("Role " + roleName + " added to " + member.getUser().getName()),
                    error -> System.err.println("Failed to add role: " + error.getMessage())
            );
        } else {
            System.err.println("Role " + roleName + " not found in the guild.");
        }
    }

    /**
     * Removes a role from a member in a guild.
     *
     * @param guild The guild where the role should be removed
     * @param member The member from whom the role should be removed
     * @param roleName The name of the role to be removed
     */
    public static void removeRoleFromMember(Guild guild, Member member, String roleName) {
        // Find the role by name
        Role role = guild.getRolesByName(roleName, true).stream().findFirst().orElse(null);

        // If the role exists, remove it from the member
        if (role != null) {
            guild.removeRoleFromMember(member, role).queue(
                    success -> System.out.println("Role " + roleName + " removed from " + member.getUser().getName()),
                    error -> System.err.println("Failed to remove role: " + error.getMessage())
            );
        } else {
            System.err.println("Role " + roleName + " not found in the guild.");
        }
    }

}
