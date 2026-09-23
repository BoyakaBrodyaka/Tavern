package me.boyakabrodyaka.hud.scoreboard;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class BoardUpdater extends BukkitRunnable {

    private static final long INITIAL_DELAY = 0L;
    private static final long PERIOD = 20L;

    private final BoardManager boardManager;
    private final JavaPlugin plugin;

    public void start() {
        runTaskTimer(this.plugin, INITIAL_DELAY, PERIOD);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.boardManager.update(player);
        }
    }
}