package me.boyakabrodyaka.commerce.day.finisher;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.day.DayManager;
import me.boyakabrodyaka.commerce.day.bossbar.DayBossBarRegistry;
import me.boyakabrodyaka.commerce.storage.StorageLoader;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class DayFinisher {

    private static final int MIN_DAY = 1;

    private final DayManager dayManager;
    private final DayBossBarRegistry bossBarRegistry;
    private final StorageLoader storageLoader;

    public void cancel(Player player) {
        UUID uuid = player.getUniqueId();
        if (!this.dayManager.getRegistry().isStarted(uuid)) return;
        int current = this.dayManager.getCurrentDay(uuid);
        finish(player, current);
    }

    public void complete(Player player) {
        UUID uuid = player.getUniqueId();
        if (!this.dayManager.getRegistry().isStarted(uuid)) return;
        int next = this.dayManager.getCurrentDay(uuid) + 1;
        finish(player, next);
    }

    private void finish(Player player, int day) {
        UUID uuid = player.getUniqueId();
        World world = player.getWorld();
        if (world == null) return;

        this.dayManager.stop(uuid);
        this.bossBarRegistry.remove(uuid);

        if (day < MIN_DAY) return;

        this.storageLoader.saveDay(world.getName(), day);
    }
}