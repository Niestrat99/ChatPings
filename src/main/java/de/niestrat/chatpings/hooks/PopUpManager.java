package de.niestrat.chatpings.hooks;

import de.niestrat.chatpings.config.Config;
import de.niestrat.chatpings.config.Language;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PopUpManager {

    private static Player getPlayer(String nickname) {
        Player player = HookManager.getNicknames().get(nickname);

        if (player != null) {
            return player;
        } else {
            return Bukkit.getPlayer(nickname);
        }
    }

    public static void popUp(Player target, Player sender) {
        // If Pop Up setting is set to "none".
        if (Config.getString("pop-up-title").equals("none")) {
            return;
        }

        String pinger = HookManager.getNickname(sender);

        // Else it shall show one of these if set right.
        if (Config.config.getString("pop-up.title").equals("title")) {
            target.sendTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger)), "", (Config.config.getInt("pop-up.fade-in") * 20), (Config.config.getInt("pop-up.duration") * 20), (Config.config.getInt("pop-up.fade-out") * 20));

        } else if (Config.config.getString("pop-up.title").equals("subtitle")) {
            target.sendTitle("", ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger)), (Config.config.getInt("pop-up.fade-in") * 20), (Config.config.getInt("pop-up.duration") * 20), (Config.config.getInt("pop-up.fade-out") * 20));

        } else if (Config.config.getString("pop-up.title").equals("actionbar")) {
            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger))));

        } else if (Config.config.getString("pop-up.title").equals("boss")) {
            BossBarManager.createBossBar(sender, target);

        } else if (Config.config.getString("pop-up.title").equals("everything")) {
            target.sendTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger)), ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger)), (Config.config.getInt("pop-up.fade-in") * 20), (Config.config.getInt("pop-up.duration") * 20), (Config.config.getInt("pop-up.fade-out") * 20));
            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger))));
            if (VAC.checkVersion(190)) {
                BossBarManager.createBossBar(sender, target);
            }
        }
    }
}
