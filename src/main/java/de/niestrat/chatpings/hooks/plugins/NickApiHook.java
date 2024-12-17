package de.niestrat.chatpings.hooks.plugins;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.haoshoku.nick.api.NickAPI;

public class NickApiHook implements NicknameHook {
    @Override
    public String getNickname(Player player) {
        Plugin nickAPI = Bukkit.getPluginManager().getPlugin("nickAPI");
        String username = null;

        if (nickAPI != null && nickAPI.isEnabled()) {
            if (NickAPI.isNicked(player)) {
                username = NickAPI.getNickedPlayers().get(player.getUniqueId());
            }
        }
        return username;
    }
}
