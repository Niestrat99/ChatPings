package de.niestrat.chatpings.commands;

import de.niestrat.chatpings.config.Language;
import org.bukkit.Bukkit;
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
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length > 0) {
          if (sender.hasPermission("chatpings.admin.toggle")) {
              Player target = Bukkit.getPlayerExact(args[0]);
              if (target != null) {
                  UUID uuid = target.getUniqueId();

                  if (mutePing.contains(uuid)) {
                      mutePing.remove(uuid);
                      sender.sendMessage(Language.getString("title") + Language.getString("toggle.unmutetarget").replace("{player}", target.getName()));
                      target.sendMessage(Language.getString("title") + Language.getString("toggle.notifytargetunmute"));
                  } else {
                      mutePing.add(uuid);
                      sender.sendMessage(Language.getString("title") + Language.getString("toggle.mutetarget").replace("{player}", target.getName()));
                      target.sendMessage(Language.getString("title") + Language.getString("toggle.notifytargetmute"));
                  }
                  return true;
              } else {
                  sender.sendMessage(Language.getString("title") + Language.getString("error.player").replace("{player}", args[0]));
                  return false;
              }
          } else {
              sender.sendMessage(Language.getString("title") + Language.getString("error.permissions"));
              return false;
          }
        }
        // No argument or player is null (when command sent by player)
        if (sender instanceof Player) {
            if (sender.hasPermission("chatpings.toggle")) {
                Player player = (Player) sender;
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
                return false;
            }
        } else {
            sender.sendMessage(Language.getString("title") + Language.getString("error.args"));
            return false;
        }
        return true;
    }
}
