package me.boyakabrodyaka.operation.order.task;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistrationManager;
import me.boyakabrodyaka.operation.order.OrderManager;
import me.boyakabrodyaka.operation.order.OrderRegistry;
import me.boyakabrodyaka.operation.order.OrderSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class OrderTask extends BukkitRunnable {

    private static final long INITIAL_DELAY = 0L;
    private static final long PERIOD = 20L;

    private final NpcRegistrationManager npcManager;
    private final OrderManager orderManager;
    private final OrderRegistry registry;

    public OrderTask(NpcRegistrationManager npcManager, OrderManager orderManager) {
        this.npcManager = npcManager;
        this.orderManager = orderManager;
        this.registry = orderManager.getRegistry();
    }

    public void start(JavaPlugin plugin) {
        runTaskTimer(plugin, INITIAL_DELAY, PERIOD);
    }

    @Override
    public void run() {
        for (NpcRegistration npc : new ArrayList<>(this.npcManager.getRegistry().getAll())) process(npc);
    }

    private void process(NpcRegistration npc) {
        if (npc.isDeparting()) return;

        if (this.registry.isTaken(npc.getKey()) && this.registry.isLabelExpired(npc.getKey())) {
            this.orderManager.cancel(npc);
            return;
        }

        if (!npc.isSitting()) {
            this.registry.clearStarted(npc.getKey());
            return;
        }

        if (this.registry.isCompleted(npc.getKey())) {
            if (this.registry.has(npc.getKey()) && this.orderManager.isExpired(npc)) this.orderManager.cancel(npc);
            return;
        }

        if (this.registry.isStarted(npc.getKey())) {
            handleStarted(npc);
            return;
        }

        this.registry.markStarted(npc.getKey());
    }

    private void handleStarted(NpcRegistration npc) {
        long elapsed = System.currentTimeMillis() - this.registry.getStartedAt(npc.getKey());
        long delay = randomDelay();

        if (elapsed < delay) return;

        Player player = findPlayer(npc);
        if (player == null) return;

        this.orderManager.request(npc, player);
    }

    private long randomDelay() {
        long diff = OrderSettings.MAX_DELAY_MS - OrderSettings.MIN_DELAY_MS;
        return OrderSettings.MIN_DELAY_MS + (long) (ThreadLocalRandom.current().nextDouble() * diff);
    }

    private Player findPlayer(NpcRegistration npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getWorld().getName().equals(npc.getWorld().getName())) continue;
            return player;
        }

        return null;
    }
}