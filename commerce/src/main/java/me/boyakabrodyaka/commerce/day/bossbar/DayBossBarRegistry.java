package me.boyakabrodyaka.commerce.day.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DayBossBarRegistry {

    private static final String TITLE = "";
    private static final BarColor COLOR = BarColor.YELLOW;
    private static final BarStyle STYLE = BarStyle.SEGMENTED_20;

    private final ConcurrentHashMap<UUID, BossBar> bars = new ConcurrentHashMap<>();

    public BossBar create(Player player) {
        BossBar bar = Bukkit.createBossBar(TITLE, COLOR, STYLE);
        bar.addPlayer(player);
        bar.setVisible(true);
        this.bars.put(player.getUniqueId(), bar);
        return bar;
    }

    public BossBar get(UUID uuid) { return this.bars.get(uuid); }

    public void remove(UUID uuid) {
        BossBar bar = this.bars.remove(uuid);
        if (bar == null) return;

        bar.removeAll();
        bar.setVisible(false);
    }

    public void clear() {
        for (BossBar bar : this.bars.values()) {
            bar.removeAll();
            bar.setVisible(false);
        }
        this.bars.clear();
    }
}