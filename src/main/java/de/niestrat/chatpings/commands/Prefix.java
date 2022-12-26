package de.niestrat.chatpings.commands;

import de.niestrat.chatpings.config.Config;
import de.niestrat.chatpings.config.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Prefix implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // Check if sender has admin permissions
        if (!sender.hasPermission("chatpings.admin")) {
            sender.sendMessage(Language.getString("title") + Language.getString("error.permissions"));
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage(Language.getString("title") + Language.getString("error.args"));
            return false;
        }

        if (args[0].equals("/")) {
            sender.sendMessage(Language.getString("title") + Language.getString("error.prefix"));
            return false;
        }

        if (args[0].equalsIgnoreCase("reset")) {
            Config.config.set("ping.Prefix", "@");
            try {
                Config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage(Language.getString("title") + Language.getString("prefix.reset"));
        } else {
            Config.config.set("ping.Prefix", args[0]);
            try {
                Config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage(Language.getString("title") + Language.getString("prefix.success").replaceAll("\\{prefix}", args[0]));
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> suggestion = new ArrayList<>();
        if (args.length == 1) {
            suggestion.add("reset");
        }
        Collections.sort(suggestion);
        return suggestion;
    }
}
