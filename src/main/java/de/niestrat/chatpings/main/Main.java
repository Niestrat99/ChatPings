package de.niestrat.chatpings.main;

import de.niestrat.chatpings.UpdateChecker;
import de.niestrat.chatpings.commands.*;
import de.niestrat.chatpings.config.Config;
import de.niestrat.chatpings.config.Language;
import de.niestrat.chatpings.config.MutePings;
import de.niestrat.chatpings.hooks.VAC;
import de.niestrat.chatpings.listeners.PingListener;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    public static String title(String title) {
        title = "&0[&bChat&cPings&0]&r " + title;
        return ChatColor.translateAlternateColorCodes('&', title);
    }

    public static Main Instance;

    public static Main getInstance() {
        return Instance;
    }

    @Override
    public void onEnable() {
        Instance = this;

        getLogger().info(title(ChatColor.GREEN + "ChatPings is now enabled!"));

        regEvent(new PingListener());

        regCommand("pingreload", new Reload());
        regCommand("pinghelp", new Help());
        regCommand("pingtoggle", new Toggle());
        regCommand("pingprefix", new Prefix());
        regCommand("pinginfo", new Info());
        regCommand("pingresetcooldown", new ResetCooldown());

        if ("/".equals(Config.config.getString("ping.Prefix"))) {
            Config.config.set("ping.Prefix", "@");
            try {
                Config.save();
            } catch (IOException e) {
                log(Level.SEVERE, "Failed to save config!", this.getClass(), e);
            }
            getLogger().warning("Illegal prefix found in config item 'ping.Prefix' - resetting to default.");
        }

        /*if ("boss".equals(Config.config.getString("pop-up.title")) && !VAC.checkVersion(190)) {
            Config.config.set("pop-up.title", "none");
            try {
                Config.save();
            } catch (IOException e) {
                log(Level.SEVERE, "Failed to save config!", this.getClass(), e);
            }
            getLogger().warning("Server cannot use boss health for ping pop ups - must be atleast version 1.19! - resetting to default.");
        }*/

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
                        log(Level.SEVERE, "Something went wrong while reading MutePings!", this.getClass(), e);
                    }
                }
            }.runTaskAsynchronously(this);
        } catch (IOException e) {
            log(Level.SEVERE, "Something went wrong while loading configurations!", this.getClass(), e);
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
                    log(Level.WARNING, "Something went wrong while checking for updates!", this.getClass(), e);
                }
            }
        }.runTaskAsynchronously(this);
    }

    @Override
    public void onDisable() {
        try {
            MutePings.write();
        } catch (IOException e) {
            log(Level.SEVERE, "Something went wrong when disabling the plugin!", this.getClass(), e);
        }
    }

    private static void regCommand(String command, CommandExecutor commandClass) {
        Objects.requireNonNull(getInstance().getCommand(command)).setExecutor(commandClass);
    }

    private static void regEvent(Listener eventClass) {
        getInstance().getServer().getPluginManager().registerEvents(eventClass, getInstance());
    }

    public static void log(Level level, String message, Class<?> classFile, Exception stacktrace) {
        getInstance().getLogger().log(
                level,
                message
                + "\nTriggered in: " + classFile.getName()
                + "\nStacktrace:\n" + stacktrace
        );
    }
}
