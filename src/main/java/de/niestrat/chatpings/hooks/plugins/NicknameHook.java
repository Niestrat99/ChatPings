package de.niestrat.chatpings.hooks.plugins;

import org.bukkit.entity.Player;

public interface NicknameHook {
    /**
     * Gets the nickname of either of the compatible nickname plugins.
     * @return nickname of player.
     */
    String getNickname(Player player);
}
