package de.niestrat.chatpings.commands;

import de.niestrat.chatpings.config.Config;
import de.niestrat.chatpings.config.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Reload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender.hasPermission("chatpings.admin")) {
            sender.sendMessage(Language.getString("title") + Language.getString("reload.start"));
            try {
                Config.reloadConfig();
                Language.reloadLang();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage(Language.getString("title") + Language.getString("reload.done"));
        } else {
            sender.sendMessage(Language.getString("title") + Language.getString("error.permissions"));
        }
        return false;
    }
}
