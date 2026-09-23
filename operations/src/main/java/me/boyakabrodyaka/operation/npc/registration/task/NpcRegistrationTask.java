package me.boyakabrodyaka.operation.npc.registration.task;

import me.boyakabrodyaka.commerce.Commerce;
import me.boyakabrodyaka.commerce.day.DayManager;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistrationManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class NpcRegistrationTask extends BukkitRunnable {

    private static final long INITIAL_DELAY = 0L;
    private static final long PERIOD = 1L;
    private static final int SPAWN_TICK_INTERVAL = 200;
    private static final String WORLD_SUFFIX = "_map";

    private final NpcRegistrationManager manager;
    private final String pluginName;

    private DayManager dayManager;
    private int tick = 0;

    public NpcRegistrationTask(NpcRegistrationManager manager, String pluginName) {
        this.manager = manager;
        this.pluginName = pluginName;
        this.dayManager = resolveDayManager();
    }

    public void start(JavaPlugin plugin) {
        runTaskTimer(plugin, INITIAL_DELAY, PERIOD);
    }

    @Override
    public void run() {
        if (this.dayManager == null) this.dayManager = resolveDayManager();
        if (this.dayManager == null) return;

        updateActiveWorlds();

        this.tick++;

        if (this.tick % SPAWN_TICK_INTERVAL == 0) updateSpawns();
    }

    private void updateActiveWorlds() {
        for (World world : Bukkit.getWorlds()) {
            if (!isMapWorld(world)) continue;
            if (world.getPlayers().isEmpty()) continue;

            if (!isWorldActive(world)) this.manager.departAll(world);
        }

        this.manager.tick();
    }

    private void updateSpawns() {
        for (World world : Bukkit.getWorlds()) {
            if (!isMapWorld(world)) continue;

            if (world.getPlayers().isEmpty()) {
                this.manager.clearWorld(world);
                continue;
            }

            if (!isWorldActive(world)) continue;

            this.manager.spawnInWorld(world);
        }
    }

    private boolean isWorldActive(World world) {
        for (Player player : world.getPlayers()) {
            if (this.dayManager.isActive(player.getUniqueId())) return true;
        }

        return false;
    }

    private boolean isMapWorld(World world) {
        return world.getName().contains(WORLD_SUFFIX);
    }

    private DayManager resolveDayManager() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(this.pluginName);
        if (!(plugin instanceof Commerce)) return null;

        Commerce commerce = (Commerce) plugin;
        return commerce.getDayManager();
    }
}