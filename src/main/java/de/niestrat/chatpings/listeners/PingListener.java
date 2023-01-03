package de.niestrat.chatpings.listeners;

import de.niestrat.chatpings.commands.Toggle;
import de.niestrat.chatpings.config.Config;
import de.niestrat.chatpings.config.Language;
import de.niestrat.chatpings.hooks.CooldownManager;
import de.niestrat.chatpings.hooks.HookManager;
import de.niestrat.chatpings.hooks.PlaceholderAPIManager;
import de.niestrat.chatpings.hooks.PopUpManager;
import de.niestrat.chatpings.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingListener implements Listener {



    @EventHandler
    // Functions used to be separated in three different classes, now been compiled into one.
    public void ping(AsyncPlayerChatEvent e) {

        Player sender = e.getPlayer();

        // For Everyone and Someone pings
        String formatMessage = e.getFormat();
        String regularMessage = e.getMessage();


        // For Player Pings
        String playerFormatMessage = createPing(formatMessage, sender);
        String playerRegularMessage = createPing(regularMessage, sender);

        // Ping Types
        String everyone = Config.getString("ping.Prefix") + Config.config.getString("ping.everyoneFormat");
        String someone = Config.getString("ping.Prefix") + Config.config.getString("ping.someoneFormat");

        // Player Ping
        if (playerRegularMessage != null) {
            e.setMessage(playerRegularMessage);
        } else {
            e.setCancelled(true);
            return;
        }
        

        // EVERYONE PING
        // This line of code is to stop the API from shitting itself about NullPointerExceptions

        if (formatMessage.contains(everyone) ||regularMessage.contains(everyone)) {
            if (!sender.hasPermission("chatpings.admin")) { return; }

            formatMessage = formatMessage.replace(everyone, Config.getString("everyonePing.color") + Config.getString("ping.Prefix") + Config.config.getString("ping.everyoneFormat") + "&r");
            regularMessage = regularMessage.replace(everyone, Config.getString("everyonePing.color") + Config.getString("ping.Prefix") + Config.config.getString("ping.everyoneFormat") + "&r");

            if (CooldownManager.checkForCooldown(sender)) {
                e.setCancelled(true);
                return;
            }
            e.setMessage(ChatColor.translateAlternateColorCodes('&', regularMessage));
            e.setFormat(ChatColor.translateAlternateColorCodes('&', formatMessage));

            for (Player everyPlayer : Bukkit.getOnlinePlayers()) {
                everyPlayer.playSound(everyPlayer.getLocation(), Sound.valueOf(Config.getString("everyonePing.sound")), Config.getFloat("everyonePing.volume"), Config.getFloat("everyonePing.pitch"));
                PopUpManager.popUp(everyPlayer, sender);
            }
            CooldownManager.addPlayerToCooldown(sender.getUniqueId());
        }

        // SOMEONE PING
        if (formatMessage.contains(someone) || regularMessage.contains(someone)) {
            if (!sender.hasPermission("chatpings.someone")) { return; }

            // Get the amount of players and get a random player using the randomizer
            int playerCount = Bukkit.getOnlinePlayers().size();
            Random rand = new Random();
            Player target = new ArrayList<>(Bukkit.getOnlinePlayers()).get(rand.nextInt(playerCount));

            if (playerCount == 1 && !Config.config.getBoolean("someonePing.pingYourself")) { return; }

            while (target.getName().equals(sender.getName())) {
                if (!Config.config.getBoolean("someonePing.pingYourself")) {
                    target = new ArrayList<>(Bukkit.getOnlinePlayers()).get(rand.nextInt(playerCount));
                } else {
                    break;
                }
            }

            if (Config.config.getBoolean("someonePing.includePicked")) {
                if (CooldownManager.checkForCooldown(sender)) {
                    e.setCancelled(true);
                    return;
                }
                formatMessage = formatMessage.replace(someone, Config.getString("someonePing.color") + Config.getString("ping.Prefix") + Config.config.getString("ping.someoneFormat") + "(" + target.getName() + ")&r");
                regularMessage = regularMessage.replace(someone, Config.getString("someonePing.color") + Config.getString("ping.Prefix") + Config.config.getString("ping.someoneFormat") +"(" + target.getName() + ")&r");
                PopUpManager.popUp(target, sender);

            } else {
                if (CooldownManager.checkForCooldown(sender)) {
                    e.setCancelled(true);
                    return;
                }
                formatMessage = formatMessage.replace(someone, Config.getString("someonePing.color") + Config.getString("ping.Prefix") + Config.config.getString("ping.someoneFormat") + "&r");
                regularMessage = regularMessage.replace(someone, Config.getString("someonePing.color") + Config.getString("ping.Prefix") + Config.config.getString("ping.someoneFormat") +"&r");
                PopUpManager.popUp(target, sender);
            }



            e.setFormat(ChatColor.translateAlternateColorCodes('&', formatMessage));
            e.setMessage(ChatColor.translateAlternateColorCodes('&', regularMessage));

            target.playSound(target.getLocation(), Sound.valueOf(Config.getString("someonePing.sound")), Config.getFloat("someonePing.volume"), Config.getFloat("someonePing.pitch"));
            CooldownManager.addPlayerToCooldown(sender.getUniqueId());
        }
    }

    private Player getPlayer(String nickname) {
        Player player = HookManager.getNicknames().get(nickname);

        if (player != null) {
            return player;
        } else {
            player = PlaceholderAPIManager.getNicknames().get(nickname);
            // If PlaceholderAPI's Player is not null then it shall return the player, otherwise it returns Bukkit's player.
            return player != null ? player : Bukkit.getPlayer(nickname);

        }
    }

    // Private String for the player pings
    private String createPing(String message, Player sender) {
        if (!sender.hasPermission("chatpings.player")) { return message; }

        String pattern = "(" + Pattern.quote(Config.getString("ping.Prefix")) + ")" + "([A-Za-z0-9_]+)";
        Pattern playerNamePattern = Pattern.compile(pattern);
        Matcher playerMatch = playerNamePattern.matcher(message);

        while (playerMatch.find()) {
            String playerName = playerMatch.group(2);
            Player player = getPlayer(playerName);
            String ping = playerMatch.group(0);
            if (player != null) {
                if (player.isOnline()) {
                    if (Toggle.mutePing.contains(player.getUniqueId())) {
                        if (sender.hasPermission("chatpings.bypass")) {
                            if (CooldownManager.checkForCooldown(sender)) {
                                return null;
                            }
                            message = message.replace(ping, Config.getString("playerPing.color") + Config.getString("ping.Prefix") + playerName + "&r");
                            player.playSound(player.getLocation(), Sound.valueOf(Config.getString("playerPing.sound")), Config.getFloat("playerPing.volume"), Config.getFloat("playerPing.pitch"));
                            PopUpManager.popUp(player, sender);

                            CooldownManager.addPlayerToCooldown(sender.getUniqueId());
                        }

                    } else {
                        if (CooldownManager.checkForCooldown(sender)) {
                            return null;
                        }
                        message = message.replace(ping, Config.getString("playerPing.color") + Config.getString("ping.Prefix") + playerName + "&r");
                        player.playSound(player.getLocation(), Sound.valueOf(Config.getString("playerPing.sound")), Config.getFloat("playerPing.volume"), Config.getFloat("playerPing.pitch"));
                        PopUpManager.popUp(player, sender);

                        CooldownManager.addPlayerToCooldown(sender.getUniqueId());
                    }

                }
            }
        } return ChatColor.translateAlternateColorCodes('&', message);
    }
}



