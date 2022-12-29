package de.niestrat.chatpings.commands;

import de.niestrat.chatpings.config.Language;
import de.niestrat.chatpings.hooks.CooldownManager;
import de.niestrat.chatpings.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


//TODO Replace Main.title with Language.getString("title") ...you skunk.
public class ResetCooldown implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("chatpings.admin")) {
            sender.sendMessage(Language.getString("title") + Language.getString("error.permissions"));
            return false;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            CooldownManager.removeFromCooldown(player);
            sender.sendMessage(Language.getString("title") + Language.getString("cooldown.resetsuccess").replace("{player}", sender.getName()));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(Language.getString("title") + Language.getString("error.player").replace("{player}", args[0]));
            return false;
        }

        if (!CooldownManager.checkForCooldown(target)) {
            sender.sendMessage(Language.getString("title") + Language.getString("error.notoncooldown").replace("{player}", args[0]));
            return false;
        }

        CooldownManager.removeFromCooldown(target);
        sender.sendMessage(Language.getString("title") + Language.getString("cooldown.resetsuccess").replace("{player}", args[0]));
        return false;
    }
}
