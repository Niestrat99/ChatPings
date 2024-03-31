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

    public static void popUp(Player target, Player sender) {
        // If Pop Up setting is set to "none".
        if (Config.getString("pop-up-title").equals("none")) {
            return;
        }

        String pinger = HookManager.getNickname(sender);
        if (pinger == null) pinger = PlaceholderAPIManager.getNickname(sender);
        if (pinger == null) pinger = sender.getName();

        // Pop-Up Settings
        String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger));
        int second = 20; // 20 ticks = 1 second
        int fadeIn = Config.config.getInt("pop-up.fade-in") * second;
        int duration = Config.config.getInt("pop-up.duration") * second;
        int fadeOut = Config.config.getInt("pop-up.fade-out") * second;



        // Else it shall show one of these if set right.
        switch (Objects.requireNonNull(Config.config.getString("pop-up.type"))) {
            case "title": {
                target.sendTitle(message, "", fadeIn, duration, fadeOut);
                break;
            }
            case "subtitle": {
                target.sendTitle("", message, fadeIn, duration, fadeOut);
                break;
            }
            case "actionbar": {
                target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                break;
            }
            case "boss": {
                BossBarManager.createBossBar(sender, target);
                break;
            }
            case "everything": {
                target.sendTitle(message, message, fadeIn, duration, fadeOut);
                target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                if (VAC.checkVersion(190)) {
                    BossBarManager.createBossBar(sender, target);
                }
                break;
            }
        }

        /*if (Config.config.getString("pop-up.type").equals("title")) {
            target.sendTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger)), "", (Config.config.getInt("pop-up.fade-in") * 20), (Config.config.getInt("pop-up.duration") * 20), (Config.config.getInt("pop-up.fade-out") * 20));

        } else if (Config.config.getString("pop-up.type").equals("subtitle")) {
            target.sendTitle("", ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger)), (Config.config.getInt("pop-up.fade-in") * 20), (Config.config.getInt("pop-up.duration") * 20), (Config.config.getInt("pop-up.fade-out") * 20));

        } else if (Config.config.getString("pop-up.type").equals("actionbar")) {
            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger))));

        } else if (Config.config.getString("pop-up.type").equals("boss")) {
            BossBarManager.createBossBar(sender, target);

        } else if (Config.config.getString("pop-up.type").equals("everything")) {
            target.sendTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger)), ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger)), (Config.config.getInt("pop-up.fade-in") * 20), (Config.config.getInt("pop-up.duration") * 20), (Config.config.getInt("pop-up.fade-out") * 20));
            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Language.lang.getString("pop-up.message")).replace("{name}", pinger))));
            if (VAC.checkVersion(190)) {
                BossBarManager.createBossBar(sender, target);
            }
        }*/
    }
}
