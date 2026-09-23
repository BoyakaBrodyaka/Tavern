package me.boyakabrodyaka.commerce.day;

import lombok.Getter;
import me.boyakabrodyaka.commerce.day.bossbar.DayBossBarRegistry;
import me.boyakabrodyaka.commerce.day.bossbar.DayBossBarUpdater;
import me.boyakabrodyaka.commerce.day.finisher.DayFinisher;
import me.boyakabrodyaka.commerce.day.listener.DayListener;
import me.boyakabrodyaka.commerce.day.task.DayTask;
import me.boyakabrodyaka.commerce.storage.StorageLoader;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class DayModule {

    private final DayManager dayManager;
    private final DayBossBarRegistry bossBarRegistry;
    private final DayBossBarUpdater bossBarUpdater;
    private final DayFinisher dayFinisher;
    private final DayTask dayTask;
    private final DayListener dayListener;

    public DayModule(StorageLoader storageLoader) {
        DayRegistry registry = new DayRegistry();
        this.dayManager = new DayManager(registry);
        this.bossBarRegistry = new DayBossBarRegistry();
        this.bossBarUpdater = new DayBossBarUpdater(this.dayManager, this.bossBarRegistry);
        this.dayFinisher = new DayFinisher(this.dayManager, this.bossBarRegistry, storageLoader);
        this.dayTask = new DayTask(this.dayManager, this.bossBarUpdater, this.dayFinisher);
        this.dayListener = new DayListener(this.dayFinisher);
    }

    public void start(JavaPlugin plugin) {
        this.dayTask.start(plugin);
        plugin.getServer().getPluginManager().registerEvents(this.dayListener, plugin);
    }

    public void disable() {
        this.dayTask.cancel();
        this.bossBarRegistry.clear();
        this.dayManager.getRegistry().clear();
    }
}