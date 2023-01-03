package de.niestrat.chatpings.hooks;

import de.niestrat.chatpings.config.Config;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class PlaceholderAPIManager {
    public static HashMap<String, Player> getNicknames() {
        // Basically if the option is not set to the nickname piece like "%essentials_nickname%" then nothing happens.
        if (!Config.getString("ping.placeholder").matches("%.+%")) { return new HashMap<>(); }

        HashMap<String, Player> nicknames = new HashMap<>();

        Plugin papi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");

        // Checking if PAPI is installed.
        if (papi != null && papi.isEnabled()) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                String nickname = PlaceholderAPI.setPlaceholders(player, Config.getString("ping.placeholder"));
                nickname = ChatColor.stripColor(nickname);
                nicknames.put(nickname, player);
            }
        }
        return nicknames;
    }

    public static String getNickname(Player player) {
        Plugin papi         = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        String username     = null; // Placeholder (not the API)

        if (!Config.getString("ping.placeholder").matches("%.+%")) { return null; }

        // Checking if PAPI is installed.
        if (papi != null && papi.isEnabled()) {
            username = PlaceholderAPI.setPlaceholders(player, Config.getString("ping.placeholder"));
        }

        return username;
    }
}
