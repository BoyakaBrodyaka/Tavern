package me.boyakabrodyaka.commerce.day.task;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.day.DayManager;
import me.boyakabrodyaka.commerce.day.bossbar.DayBossBarUpdater;
import me.boyakabrodyaka.commerce.day.finisher.DayFinisher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

@RequiredArgsConstructor
public class DayTask extends BukkitRunnable {

    private static final long INITIAL_DELAY = 0L;
    private static final long PERIOD = 20L;

    private final DayManager dayManager;
    private final DayBossBarUpdater bossBarUpdater;
    private final DayFinisher dayFinisher;

    public void start(JavaPlugin plugin) { runTaskTimer(plugin, INITIAL_DELAY, PERIOD); }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();

            if (!this.dayManager.getRegistry().isStarted(uuid)) continue;
            if (this.dayManager.isActive(uuid)) {
                this.bossBarUpdater.update(player);
                continue;
            }
            this.dayFinisher.complete(player);
        }
    }
}