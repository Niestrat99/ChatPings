package de.niestrat.chatpings.commands;

import de.niestrat.chatpings.config.Language;
import de.niestrat.chatpings.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Info implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender.hasPermission("chatpings.admin")) {
            sender.sendMessage(ChatColor.AQUA + "-- " + ChatColor.RESET + Language.getString("title") + ChatColor.AQUA + "--");
            sender.sendMessage(ChatColor.AQUA + "- Developed by: " + ChatColor.RESET + Main.getInstance().getDescription().getAuthors());
            sender.sendMessage(ChatColor.AQUA + "- Version: " + ChatColor.RESET + Main.getInstance().getDescription().getVersion());
            sender.sendMessage(ChatColor.AQUA + "- Spigot Link: " + ChatColor.RESET + "https://www.spigotmc.org/resources/chatpings.76261/");
            sender.sendMessage(ChatColor.AQUA + "--- " + ChatColor.RESET + "Found a bug or got any questions?" + ChatColor.AQUA + " ---");
            sender.sendMessage(ChatColor.AQUA + "- Discord: " + ChatColor.RESET + "https://discord.gg/jntpdcM");
            sender.sendMessage(ChatColor.AQUA + "- Spigot Discussions: " + ChatColor.RESET + "https://www.spigotmc.org/threads/chatpings.76261/");

        } else {
            sender.sendMessage(Language.getString("title") + Language.getString("error.permissions"));
        }
        return false;
    }
}
