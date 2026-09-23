package me.boyakabrodyaka.commerce.energy;

import lombok.Getter;
import me.boyakabrodyaka.commerce.energy.task.EnergyTask;
import org.bukkit.plugin.java.JavaPlugin;

public class EnergyModule {

    private static final long INITIAL_DELAY = 0L;
    private static final long PERIOD = 20L;

    @Getter
    private final EnergyManager energyManager;

    private EnergyTask energyTask;

    public EnergyModule(EnergySettings settings) {
        this.energyManager = new EnergyManager(settings);
    }

    public void startTask(JavaPlugin plugin) {
        this.energyTask = new EnergyTask(this.energyManager);
        this.energyTask.runTaskTimer(plugin, INITIAL_DELAY, PERIOD);
    }

    public void disable() {
        if (this.energyTask != null) this.energyTask.cancel();
        this.energyManager.clear();
    }
}