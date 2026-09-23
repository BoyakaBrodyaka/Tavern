package me.boyakabrodyaka.operation.npc.registration.departure.task;

import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistrationManager;
import me.boyakabrodyaka.operation.npc.registration.departure.NpcDepartureCoordinate;
import me.boyakabrodyaka.operation.npc.registration.departure.NpcDepartureManager;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class NpcDepartureTask extends BukkitRunnable {

    private static final long INITIAL_DELAY = 0L;
    private static final long PERIOD = 10L;

    private final NpcRegistrationManager npcManager;
    private final NpcDepartureManager departureManager;
    private final double arriveDistanceSquared;

    public NpcDepartureTask(NpcRegistrationManager npcManager, NpcDepartureManager departureManager) {
        this.npcManager = npcManager;
        this.departureManager = departureManager;

        double arrive = NpcDepartureCoordinate.ARRIVE_DISTANCE.getValue();
        this.arriveDistanceSquared = arrive * arrive;
    }

    public void start(JavaPlugin plugin) {
        runTaskTimer(plugin, INITIAL_DELAY, PERIOD);
    }

    @Override
    public void run() { for (NpcRegistration npc : new ArrayList<>(this.npcManager.getRegistry().getAll())) process(npc); }

    private void process(NpcRegistration npc) {
        if (npc.isDeparting()) {
            if (hasArrived(npc)) this.npcManager.depart(npc);
            return;
        }

        if (npc.isSitting()) return;
        if (npc.hasTargetChair()) return;
        if (!this.departureManager.shouldLeave(npc)) return;

        Location departure = this.departureManager.getDepartureLocation(npc);
        if (departure == null) return;

        npc.setDepartureLocation(departure);
        npc.setDeparting(true);
        npc.stopFollowing();
        this.departureManager.stopTracking(npc);
    }

    private boolean hasArrived(NpcRegistration npc) {
        Location departure = npc.getDepartureLocation();
        if (departure == null) return false;

        Location current = npc.getCurrent();

        double dx = departure.getX() - current.getX();
        double dy = departure.getY() - current.getY();
        double dz = departure.getZ() - current.getZ();

        return dx * dx + dy * dy + dz * dz <= this.arriveDistanceSquared;
    }
}