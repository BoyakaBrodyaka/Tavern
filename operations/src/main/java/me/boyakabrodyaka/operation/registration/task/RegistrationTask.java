package me.boyakabrodyaka.operation.registration.task;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.registration.RegistrationManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class RegistrationTask extends BukkitRunnable {

    private static final long INITIAL_DELAY = 0L;
    private static final long PERIOD = 10L;

    private final RegistrationManager manager;

    public void start(JavaPlugin plugin) {
        runTaskTimer(plugin, INITIAL_DELAY, PERIOD);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) this.manager.update(player);
    }
}