package de.niestrat.chatpings.config;

import de.niestrat.chatpings.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Language {

    public static File langFile = new File(Main.getInstance().getDataFolder(), "lang.yml");
    public static FileConfiguration lang = YamlConfiguration.loadConfiguration(langFile);

    public static void save() throws IOException {
        lang.save(langFile);
    }

    public static void setDefaults() throws IOException {
        // Title
        lang.addDefault("title", "&0[&bChat&cPings&0]&r ");

        // Reload
        lang.addDefault("reload.start", "&bReloading configurations...");
        lang.addDefault("reload.done", "&bReload complete!");

        // Toggle
        lang.addDefault("toggle.mute", "&bSuccessfully muted pings! &c(Only staff can ping you now!)");
        lang.addDefault("toggle.unmute", "&bSuccessfully unmuted pings!");
        lang.addDefault("toggle.mutetarget", "&bSuccessfully muted pings for &e{player}&b! &c(Only staff can ping them now!)");
        lang.addDefault("toggle.unmutetarget", "&bSuccessfully unmuted pings for &e{player}&b!");
        lang.addDefault("toggle.notifytargetmute", "&bYour pings have been muted by a staff member.");
        lang.addDefault("toggle.notifytargetunmute", "&bYour pings have been unmuted by a staff member.");

        // Prefix
        lang.addDefault("prefix.success", "&bSuccessfully set prefix to &e{prefix}&b!");
        lang.addDefault("prefix.reset", "&bSuccessfully reset prefix to default! (@)");

        // Help
        lang.addDefault("help.reload", "&bReloads the config and language file.");
        lang.addDefault("help.prefix", "&bChanges the prefix used for pings.");
        lang.addDefault("help.toggle", "&bMakes that only staff can ping you.");
        lang.addDefault("help.help", "&bShows you this list of commands this plugin has.");
        lang.addDefault("help.resetcooldown", "&bResets a player's cooldown.");

        // ResetCooldown
        lang.addDefault("cooldown.resetsuccess", "&bSuccessfully reset cooldown for &e{player}&b!");

        // Error Messages
        lang.addDefault("error.permissions", "&cInsufficient permissions!");
        lang.addDefault("error.args", "&cToo few arguments!");
        lang.addDefault("error.prefix", "&cCannot set prefix to &e/ &cbecause it's a command prefix!");
        lang.addDefault("error.oncooldown", "&cYour ping is on a cooldown! Wait &e{time} &cmore second(s)!");
        lang.addDefault("error.player", "&cPlayer &e{player} &cdoes not exist!");
        lang.addDefault("error.notoncooldown", "&cPlayer &e{player} &cis not on cooldown!");

        // Pop Up Text
        lang.addDefault("pop-up.message", "&b&l{name} &6has pinged you!");

        lang.options().copyDefaults(true);
        save();
    }

    public static void reloadLang() throws IOException {
        if (langFile == null) {
            langFile = new File(Main.getInstance().getDataFolder(), "lang.yml");
        }
        lang = YamlConfiguration.loadConfiguration(langFile);
        setDefaults();
        save();
    }

    public static String getString(String path) {
        String str = lang.getString(path);
        if (str == null) return "";
        str = str.replaceAll("''", "'");
        str = str.replaceAll("^'", "");
        str = str.replaceAll("'$", "");
        str = ChatColor.translateAlternateColorCodes('&', str);
        return str;
    }
}
