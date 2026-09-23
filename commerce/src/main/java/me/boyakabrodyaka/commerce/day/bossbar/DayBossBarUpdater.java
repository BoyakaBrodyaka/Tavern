package me.boyakabrodyaka.commerce.day.bossbar;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.day.DayCoordinate;
import me.boyakabrodyaka.commerce.day.DayManager;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class DayBossBarUpdater {

    private static final String TITLE_FORMAT = "§eДень %d §7| §f%02d:%02d";
    private static final long MILLIS_PER_SECOND = 1000L;
    private static final long SECONDS_PER_MINUTE = 60L;

    private final DayManager dayManager;
    private final DayBossBarRegistry registry;

    public void update(Player player) {
        UUID uuid = player.getUniqueId();

        if (!this.dayManager.isActive(uuid)) {
            this.registry.remove(uuid);
            return;
        }

        BossBar bar = this.registry.get(uuid);
        if (bar == null) bar = this.registry.create(player);

        long remaining = this.dayManager.getRemaining(uuid);
        long total = DayCoordinate.DURATION_MS.getLongValue();

        double progress = calculateProgress(remaining, total);
        long seconds = remaining / MILLIS_PER_SECOND;
        long minutes = seconds / SECONDS_PER_MINUTE;
        long secs = seconds % SECONDS_PER_MINUTE;

        int day = this.dayManager.getCurrentDay(uuid);

        bar.setTitle(String.format(TITLE_FORMAT, day, minutes, secs));
        bar.setProgress(progress);
        bar.setVisible(true);
    }

    private double calculateProgress(long remaining, long total) {
        if (total <= 0L) return 0.0D;

        double progress = (double) remaining / (double) total;

        if (progress < 0.0D) return 0.0D;
        if (progress > 1.0D) return 1.0D;

        return progress;
    }
}