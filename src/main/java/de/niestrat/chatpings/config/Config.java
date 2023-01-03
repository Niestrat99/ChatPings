package de.niestrat.chatpings.config;

import de.niestrat.chatpings.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    public static File configFile = new File(Main.getInstance().getDataFolder(), "config.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    public static void save() throws IOException {
        config.save(configFile);
    }

    public static void setDefaults() throws IOException {
        // Main Ping settings
        config.addDefault("ping.Prefix", "@");
        config.addDefault("ping.everyoneFormat", "everyone");
        config.addDefault("ping.someoneFormat", "someone");
        config.addDefault("ping.placeholder", "enter placeholder here");

        // Ping Cooldown Settings
        config.addDefault("pingcooldown.enabled", false);
        config.addDefault("pingcooldown.duration", 5);

        // Everyone Ping settings
        config.addDefault("everyonePing.color", "&b&l");
        config.addDefault("everyonePing.sound", "ENTITY_EXPERIENCE_ORB_PICKUP");
        config.addDefault("everyonePing.volume", "3.0F");
        config.addDefault("everyonePing.pitch", "0.5F");

        // Player Ping settings
        config.addDefault("playerPing.color", "&b&l");
        config.addDefault("playerPing.sound", "ENTITY_EXPERIENCE_ORB_PICKUP");
        config.addDefault("playerPing.volume", "3.0F");
        config.addDefault("playerPing.pitch", "0.5F");

        // Someone Ping settings
        config.addDefault("someonePing.color", "&b&l");
        config.addDefault("someonePing.sound", "ENTITY_EXPERIENCE_ORB_PICKUP");
        config.addDefault("someonePing.volume", "3.0F");
        config.addDefault("someonePing.pitch", "0.5F");
        config.addDefault("someonePing.pingYourself", true);
        config.addDefault("someonePing.includePicked", true);

        // Pop-Up settings
        config.addDefault("pop-up.title", "none"); // Options are "none", "title", "subtitle" and "actionbar".
        config.addDefault("pop-up.fade-in", 1);
        config.addDefault("pop-up.duration", 3); // Number set in seconds.
        config.addDefault("pop-up.fade-out", 1);
        config.addDefault("pop-up.bosscolor", "WHITE");
        config.options().copyDefaults(true);
        save();
    }
    public static void reloadConfig() throws IOException {
        if (configFile == null) {
            configFile = new File(Main.getInstance().getDataFolder(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        setDefaults();
        save();
    }

    public static String getString(String path) {
        String str = config.getString(path);
        if (str == null) return "";
        str = str.replaceAll("''", "'");
        str = str.replaceAll("^'", "");
        str = str.replaceAll("'$", "");
        str = ChatColor.translateAlternateColorCodes('&', str);
        return str;
    }

    public static float getFloat(String path) {
        String str = Config.getString(path);
        return Float.parseFloat(str);
    }
}
