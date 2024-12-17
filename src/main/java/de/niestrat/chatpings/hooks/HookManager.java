package de.niestrat.chatpings.hooks;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.earth2me.essentials.UserMap;
import de.niestrat.chatpings.hooks.plugins.EssentialsHook;
import de.niestrat.chatpings.hooks.plugins.NickApiHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class HookManager {

    public static HashMap<String, Player> getNicknames() {
        HashMap<String, Player> nicknames = new HashMap<>();

        //TODO Make placeholderAPI somehow work with the new shit
        //TODO This is very confusing, come back once brains work properly.

        return nicknames;

        /*Plugin essentials = Bukkit.getPluginManager().getPlugin("Essentials");

        // Checking if Essentials is installed.
        if (essentials != null && essentials.isEnabled()) {

            Essentials mainEssentials = (Essentials) essentials;

            UserMap userMaps = mainEssentials.getUserMap();

            for (Player player : Bukkit.getOnlinePlayers()) {
                User user = userMaps.getUser(player.getUniqueId());

                if (user.getNickname() != null) {
                    nicknames.put(user.getNickname().toLowerCase(), player);
                }
            }
        }
        return nicknames;
        ------------------------
        SUMMARY
        ------------------------
        If Essentials is installed, do the following.
        In Essentials, you get a UserMap (something from the E-API.
        Next, you loop through the online players and get the online user out of the UserMap.
        This UserMap tells all sorts of information about the player including its nickname.
        If the user has a nickname set, it'll be put into a nicknames HashMap.

        */
    }

    @Nullable
    public static String getNickname(Player player) {
        String nickname = null;
        String essNick = new EssentialsHook().getNickname(player);
        String nApiNick = new NickApiHook().getNickname(player);

        if (essNick != null) {
            nickname = essNick;
        }
        if (nApiNick != null) {
            nickname = nApiNick;
        }

        return nickname;
    }
}
