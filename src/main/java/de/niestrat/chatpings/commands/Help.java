package de.niestrat.chatpings.commands;

import de.niestrat.chatpings.config.Language;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Help implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender.hasPermission("chatpings.help")) {
            if (sender.hasPermission("chatpings.admin")) {
                sender.sendMessage(Language.getString("title") + ChatColor.AQUA + "/pingreload - " + ChatColor.RESET + Language.getString("help.reload"));
                sender.sendMessage(Language.getString("title") + ChatColor.AQUA + "/pingprefix <prefix> - " + ChatColor.RESET + Language.getString("help.prefix"));
                sender.sendMessage(Language.getString("title") + ChatColor.AQUA + "/pingresetcooldown <player> - " + ChatColor.RESET + Language.getString("help.resetcooldown"));
            }
            sender.sendMessage(Language.getString("title") + ChatColor.AQUA + "/pingtoggle - " + ChatColor.RESET + Language.getString("help.toggle"));
            sender.sendMessage(Language.getString("title") + ChatColor.AQUA + "/pinghelp - " + ChatColor.RESET + Language.getString("help.help"));
        }
        return false;
    }
}
