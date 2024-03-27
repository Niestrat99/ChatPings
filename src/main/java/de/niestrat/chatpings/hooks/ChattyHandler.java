package de.niestrat.chatpings.hooks;

import de.niestrat.chatpings.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.mrbrikster.chatty.Chatty;
import ru.mrbrikster.chatty.chat.Chat;
import ru.mrbrikster.chatty.chat.ChatManager;

public class ChattyHandler {

    public static Chat getChat(Player player, String message) {
        final ChatManager chatManager = Chatty.instance().getExact(ChatManager.class);
        Chat currentChat = chatManager.getCurrentChat(player);

        for (Chat chatChannel : chatManager.getChats()) {
            if (!chatChannel.isWriteAllowed(player)) continue;
            if (chatChannel.getSymbol().isEmpty()) {
                if (currentChat == null) currentChat = chatChannel;
            } else {
                if (message.startsWith(chatChannel.getSymbol())) {
                    currentChat = chatChannel;
                }
            }
        }

        return currentChat;
    }
    public static boolean checkForIgnoredChat(Player player, String message) {
        /*
            - Check if Chatty is installed
            - Check for chat channel?
            - If chat channel is in the list then ignore!
        */

        if (!Bukkit.getPluginManager().isPluginEnabled("Chatty")) { return true; }
        Chat receivedChat = getChat(player, message);

        if (receivedChat != null) {
            return !Config.config.getStringList("ping.ignored-chats").contains(receivedChat.getName());
        }
        return true;
    }
}
