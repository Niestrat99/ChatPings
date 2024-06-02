package de.niestrat.chatpings.hooks;

import de.niestrat.chatpings.config.Config;
import de.niestrat.chatpings.config.Language;
import de.niestrat.chatpings.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CooldownManager {
    private static final HashMap<String, List<PingRunnable>> cooldown = new HashMap<>();

    public static class PingRunnable extends BukkitRunnable {

        private UUID uuid;
        private long ms;
        private long startingTime;

        public PingRunnable(UUID uuid, long waitingTime) {
            this.uuid = uuid;
            ms = waitingTime;
            if (Config.config.getBoolean("pingcooldown.enabled") && !Bukkit.getPlayer(uuid).hasPermission("chatpings.bypass")) {
                ms += Config.config.getInt("pingcooldown.duration");
            }
            startingTime = System.currentTimeMillis();
            runTaskLater(Main.getInstance());
        }

        public synchronized BukkitTask runTaskLater(Plugin plugin) throws IllegalArgumentException, IllegalStateException {
            return super.runTaskLater(plugin, ms * 20);
        }

        @Override
        public void run() {
            List<PingRunnable> list = cooldown.get(uuid.toString());
            list.remove(this);

        }
    }
    //TODO Debug adding players to list.
    public static int secondsLeftOnCooldown(UUID uuid, Player player) {
        if (player.hasPermission("chatpings.bypass")) return 0;
        if (!cooldown.containsKey(uuid.toString())) { return 0;}
        List<PingRunnable> list = cooldown.get(uuid.toString());
        for (PingRunnable runnable : list) {
            if (runnable.uuid.toString().equals(player.getUniqueId().toString())) {
                return (int) Math.ceil((runnable.startingTime + runnable.ms * 1000 - System.currentTimeMillis()) / 1000.0);
            }
        }
        return 0;
    }

    public static void addPlayerToCooldown(UUID uuid, int pingCooldownTime) {
        PingRunnable pingRunnable = new PingRunnable(uuid, pingCooldownTime);

        if (!cooldown.containsKey(uuid.toString())) {
            cooldown.put(uuid.toString(), new ArrayList<>());
        }
        List<PingRunnable> cooldowns = cooldown.get(uuid.toString());
        cooldowns.add(pingRunnable);
    }

    public static boolean checkForCooldown(Player sender) {
        int cooldown = secondsLeftOnCooldown(sender.getUniqueId(), sender);

        if (cooldown > 0) {
            sender.sendMessage(Language.getString("title") + Language.getString("error.oncooldown").replace("{time}", String.valueOf(cooldown)));
            return true;
        }
        return false;
    }

    public static void removeFromCooldown(Player sender) {
        cooldown.remove(sender.getUniqueId().toString());
    }
}
