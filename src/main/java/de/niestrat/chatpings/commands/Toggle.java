package de.niestrat.chatpings.commands;

import de.niestrat.chatpings.config.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Toggle implements CommandExecutor {

    public static final List<UUID> mutePing = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender.hasPermission("chatpings.toggle")) {
            Player player = (Player)sender;
            UUID uuid = player.getUniqueId();

            if (mutePing.contains(uuid)) {
                mutePing.remove(uuid);
                sender.sendMessage(Language.getString("title") + Language.getString("toggle.unmute"));
            } else {
                mutePing.add(uuid);
                sender.sendMessage(Language.getString("title") + Language.getString("toggle.mute"));
            }
        } else {
            sender.sendMessage(Language.getString("title") + Language.getString("error.permissions"));
        }
        return false;
    }
}
