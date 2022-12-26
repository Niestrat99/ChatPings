package de.niestrat.chatpings.hooks;

import de.niestrat.chatpings.config.Config;
import de.niestrat.chatpings.config.Language;
import de.niestrat.chatpings.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarManager {
    public static void createBossBar(Player sender, Player target) {
        String message  = Language.lang.getString("pop-up.message");
        if (message == null) {return;}

        // Config variables
        int duration    = Config.config.getInt("pop-up.duration");
        int limit       = Config.config.getInt("pop-up.bosspinglimit");
        String color    = Config.config.getString("pop-up.bosscolor");

        // Player variables
        String pinger   = HookManager.getNickname(sender);


        final int interval = 20; // 20 Ticks (1 Second)

        BossBar createdBossBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', message.replace("{name}", pinger)), BarColor.valueOf(color), BarStyle.SOLID);
        createdBossBar.addPlayer(target);

        new BukkitRunnable() {
            int runLap  = duration;

            @Override
            public void run() {
                if (runLap == -1) {
                    createdBossBar.setProgress(0);
                    Main.getInstance().getLogger().warning("Loop should be stopped!");
                    cancel();
                    createdBossBar.removePlayer(target);
                    return;
                }
                createdBossBar.setProgress((float)1 / duration * runLap);
                runLap --;
            }
        } .runTaskTimer(Main.getInstance(), 0, interval);
    }
}
