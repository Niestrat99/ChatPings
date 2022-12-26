package de.niestrat.chatpings.main;

import de.niestrat.chatpings.UpdateChecker;
import de.niestrat.chatpings.commands.*;
import de.niestrat.chatpings.config.Config;
import de.niestrat.chatpings.config.Language;
import de.niestrat.chatpings.config.MutePings;
import de.niestrat.chatpings.hooks.CooldownManager;
import de.niestrat.chatpings.hooks.VAC;
import de.niestrat.chatpings.listeners.PingListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main extends JavaPlugin {

    public static String title(String title) {
        title = "&0[&bChat&cPings&0]&r " + title;
        return ChatColor.translateAlternateColorCodes('&', title);
    }

    private int version;

    public static Main Instance;

    public static Main getInstance() {
        return Instance;
    }

    @Override
    public void onEnable() {
        Instance = this;

        getLogger().info(title(ChatColor.GREEN + "ChatPings is now enabled!"));

        getServer().getPluginManager().registerEvents(new PingListener(), this);

        getCommand("pingreload").setExecutor(new Reload());
        getCommand("pinghelp").setExecutor(new Help());
        getCommand("pingtoggle").setExecutor(new Toggle());
        getCommand("pingprefix").setExecutor(new Prefix());
        getCommand("pinginfo").setExecutor(new Info());
        getCommand("pingresetcooldown").setExecutor(new ResetCooldown());

        if ("/".equals(Config.config.getString("ping.Prefix"))) {
            Config.config.set("ping.Prefix", "@");
            try {
                Config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getLogger().warning("Illegal prefix found in config item 'ping.Prefix' - resetting to default.");
        }

        if ("boss".equals(Config.config.getString("pop-up.title")) && !VAC.checkVersion(190)) {
            Config.config.set("pop-up.title", "none");
            try {
                Config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getLogger().warning("Server cannot use boss health for ping pop ups - must be atleast version 1.19! - resetting to default.");
        }

        try {
            Config.setDefaults();
            Language.setDefaults();
            new BukkitRunnable() {

                @Override
                public void run() {
                    try {
                        MutePings.create();
                        MutePings.read();
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Object[] update = UpdateChecker.getUpdate();
                    if (update != null) {
                        getServer().getConsoleSender().sendMessage(title(ChatColor.AQUA + "" + ChatColor.BOLD + "A new version is available!") + "\n" + title(ChatColor.AQUA + "" + ChatColor.BOLD + "Current version you're using: " + ChatColor.WHITE + getDescription().getVersion()) + "\n" + title(ChatColor.AQUA + "" + ChatColor.BOLD + "Latest version available: " + update[2]));
                        getLogger().info(title(ChatColor.AQUA + "Download link: https://www.spigotmc.org/resources/chatpings.76261/"));
                    } else {
                        getLogger().info(title(ChatColor.AQUA + "Plugin is up to date!"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(this);
    }

    @Override
    public void onDisable() {
        try {
            MutePings.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
