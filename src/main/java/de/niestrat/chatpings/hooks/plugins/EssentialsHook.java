package de.niestrat.chatpings.hooks.plugins;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.earth2me.essentials.UserMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EssentialsHook implements NicknameHook {
    @Override
    public String getNickname(Player player) {
        Plugin essentials = Bukkit.getPluginManager().getPlugin("Essentials");
        String username = null; // Placeholder

        // Check if essentials is installed
        if (essentials != null && essentials.isEnabled()) {
            Essentials mainEssentials = (Essentials) essentials;

            UserMap userMaps = mainEssentials.getUserMap();

            User user = userMaps.getUser(player.getUniqueId());

            if (user.getNickname() != null) {
                username = user.getNickname();
            }
        }
        return username;
    }
}
