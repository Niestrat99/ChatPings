package de.niestrat.chatpings.hooks;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.earth2me.essentials.UserMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class HookManager {

    public static HashMap<String, Player> getNicknames() {
        HashMap<String, Player> nicknames = new HashMap<>();

        Plugin essentials = Bukkit.getPluginManager().getPlugin("Essentials");

        // Checking if Essentials is installed.
        if (essentials != null && essentials.isEnabled()) {

            Essentials mainEssentials = (Essentials) essentials;

            UserMap userMaps = mainEssentials.getUserMap();

            for (Player player : Bukkit.getOnlinePlayers()) {
                User user = userMaps.getUser(player.getUniqueId());

                if (user.getNickname() != null) {
                    nicknames.put(user.getNickname(), player);
                }
            }
        } else {
            System.out.println("It's not there! (Oh, the misery!)");
        }
        return nicknames;
    }

    public static String getNickname(Player player) {
        Plugin essentials = Bukkit.getPluginManager().getPlugin("Essentials");
        String username = null; // Placeholder

        // Check if essentials is installed
        if (essentials != null && essentials.isEnabled()) {
            Essentials mainEssentials = (Essentials) essentials;

            UserMap userMaps = mainEssentials.getUserMap();

            User user = userMaps.getUser(player.getUniqueId());

            if (user.getNickname() != null) {
                username = user.getNickname();
            } else {
                username = player.getName();
            }
        }
        return username;
    }
}
