package me.boyakabrodyaka.operation.npc.registration;

import me.boyakabrodyaka.operation.dining.chair.DiningChair;
import me.boyakabrodyaka.operation.npc.registration.departure.NpcDepartureManager;
import me.boyakabrodyaka.operation.npc.registration.name.NpcNameRegistry;
import me.boyakabrodyaka.operation.npc.registration.skin.NpcSkinRegistry;
import me.boyakabrodyaka.operation.npc.registration.type.NpcTypeRegistry;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class NpcRegistrationManager {

    private static final double WALK_SPEED = 0.04D;
    private static final double REACH_DISTANCE = 0.15D;
    private static final double FOLLOW_DISTANCE = 1.5D;
    private static final double FOLLOW_SPEED = 0.22D;
    private static final double ZONE_INTERACT_RADIUS_SQUARED = 25.0D;
    private static final String KEY_SEPARATOR = "_";

    private final AtomicInteger keyCounter = new AtomicInteger(0);

    private final NpcRegistrationRegistry registry;
    private final NpcRegistrationFactory factory;
    private final NpcRegistrationSpawner spawner;
    private final NpcRegistrationWalker walker;
    private final NpcRegistrationQueue queue;
    private final NpcRegistrationZone zone;
    private final NpcRegistrationSitter sitter;
    private final NpcDepartureManager departureManager;

    public NpcRegistrationManager(JavaPlugin plugin, NpcTypeRegistry typeRegistry, NpcNameRegistry nameRegistry, NpcSkinRegistry skinRegistry, NpcDepartureManager departureManager) {
        this.registry = new NpcRegistrationRegistry();
        this.factory = new NpcRegistrationFactory(typeRegistry, nameRegistry, skinRegistry);
        this.spawner = new NpcRegistrationSpawner(plugin);
        this.sitter = new NpcRegistrationSitter();
        this.walker = new NpcRegistrationWalker(new NpcRegistrationSettings(
                WALK_SPEED,
                REACH_DISTANCE,
                FOLLOW_DISTANCE,
                FOLLOW_SPEED
        ), this.sitter);
        this.queue = new NpcRegistrationQueue();
        this.zone = new NpcRegistrationZone();
        this.departureManager = departureManager;
    }

    public void tick() {
        for (NpcRegistration npc : new ArrayList<>(this.registry.getAll())) {
            if (npc.isSitting()) {
                this.spawner.move(npc);
                continue;
            }

            if (!npc.isDeparting() && this.walker.reached(npc)) continue;

            this.walker.walk(npc);
            this.spawner.move(npc);
        }
    }

    public void consume(NpcRegistration npc) {
        clearSitting(npc);
        this.departureManager.stopTracking(npc);
        this.spawner.destroy(npc);
        this.registry.unregister(npc.getKey());
        reorder(npc.getWorld());
    }

    public void depart(NpcRegistration npc) {
        clearSitting(npc);

        Location disappear = this.departureManager.getDisappearLocation(npc);
        if (disappear != null) {
            npc.setCurrent(disappear);
            this.spawner.move(npc);
        }

        this.departureManager.stopTracking(npc);
        this.spawner.destroy(npc);
        this.registry.unregister(npc.getKey());
        reorder(npc.getWorld());
    }

    public void departAll(World world) {
        for (NpcRegistration npc : new ArrayList<>(this.registry.getAll())) {
            if (!npc.getWorld().getName().equals(world.getName())) continue;
            if (npc.isDeparting()) continue;

            clearSitting(npc);
            npc.stopFollowing();
            npc.clearTargetChair();

            Location departure = this.departureManager.getDepartureLocation(npc);
            if (departure == null) continue;

            npc.setDepartureLocation(departure);
            npc.setDeparting(true);

            this.departureManager.stopTracking(npc);
        }
    }

    public boolean follow(NpcRegistration npc, Player player) {
        UUID uuid = player.getUniqueId();

        if (hasFollower(uuid)) return false;
        if (npc.isSitting()) return false;
        if (npc.isDeparting()) return false;

        npc.setFollowing(uuid);
        this.departureManager.startFollowTracking(npc);
        reorder(npc.getWorld());
        return true;
    }

    public boolean hasFollower(UUID uuid) {
        for (NpcRegistration npc : this.registry.getAll()) {
            if (!npc.isFollowing()) continue;
            if (!npc.getFollowing().equals(uuid)) continue;

            return true;
        }

        return false;
    }

    public NpcRegistration findFollower(Player player) {
        UUID uuid = player.getUniqueId();

        for (NpcRegistration npc : this.registry.getAll()) {
            if (!npc.isFollowing()) continue;
            if (!npc.getFollowing().equals(uuid)) continue;

            return npc;
        }

        return null;
    }

    public NpcRegistration findNearby(Player player, double radius) {
        NpcRegistration closest = null;
        double closestDistanceSquared = radius * radius;

        for (NpcRegistration npc : this.registry.getAll()) {
            if (!isInWorld(npc, player.getWorld())) continue;

            double distanceSquared = distanceSquared(npc.getCurrent(), player.getLocation());
            if (distanceSquared > closestDistanceSquared) continue;

            closestDistanceSquared = distanceSquared;
            closest = npc;
        }

        return closest;
    }

    public void sitOnChair(NpcRegistration npc, DiningChair chair, double centerX, double centerY, double centerZ) {
        if (npc == null || chair == null) return;
        if (chair.isOccupied()) return;

        this.departureManager.stopTracking(npc);
        npc.stopFollowing();
        npc.setCenterX(centerX);
        npc.setCenterY(centerY);
        npc.setCenterZ(centerZ);
        npc.setTargetChair(chair);
        reorder(npc.getWorld());
    }

    public void standUp(NpcRegistration npc) {
        this.sitter.standUp(npc);
        this.departureManager.startQueueTracking(npc);
        reorder(npc.getWorld());
    }

    public void spawnInWorld(World world) {
        int currentCount = countInWorld(world);
        if (currentCount >= this.queue.getMax()) return;

        Location target = this.queue.getQueuePosition(world, currentCount);
        String key = world.getName() + KEY_SEPARATOR + this.keyCounter.getAndIncrement();

        NpcRegistration npc = this.factory.create(key, world, target);
        this.registry.register(npc);
        this.spawner.spawn(npc);
        this.departureManager.startQueueTracking(npc);
        reorder(world);
    }

    public void despawnFor(Player player, String worldName) {
        for (NpcRegistration npc : this.registry.getAll()) {
            if (!npc.getWorld().getName().equals(worldName)) continue;
            this.spawner.destroyFor(player, npc);
        }
    }

    public void reorder(World world) {
        List<NpcRegistration> list = new ArrayList<>();

        for (NpcRegistration npc : this.registry.getAll()) {
            if (npc.isFollowing()) continue;
            if (npc.isSitting()) continue;
            if (npc.hasTargetChair()) continue;
            if (npc.isDeparting()) continue;
            if (!isInWorld(npc, world)) continue;

            list.add(npc);
        }

        list.sort(Comparator.comparingLong(npc -> extractIndex(npc.getKey())));

        for (int i = 0; i < list.size(); i++) {
            NpcRegistration npc = list.get(i);
            npc.setTarget(this.queue.getQueuePosition(world, i));
        }
    }

    public void clearWorld(World world) {
        for (NpcRegistration npc : new ArrayList<>(this.registry.getAll())) {
            if (!npc.getWorld().getName().equals(world.getName())) continue;

            clearSitting(npc);
            this.departureManager.stopTracking(npc);
            this.spawner.destroy(npc);
            this.registry.unregister(npc.getKey());
        }
    }

    public void clear() {
        for (NpcRegistration npc : this.registry.getAll()) {
            clearSitting(npc);
            this.departureManager.stopTracking(npc);
            this.spawner.destroy(npc);
        }

        this.registry.clear();
    }

    public boolean isInZone(NpcRegistration npc) {
        Location location = npc.getCurrent();
        return this.zone.contains(location.getX(), location.getY(), location.getZ());
    }

    public NpcRegistration findFirstInZone(Player player) {
        NpcRegistration closest = null;
        double closestDistanceSquared = ZONE_INTERACT_RADIUS_SQUARED;

        for (NpcRegistration npc : this.registry.getAll()) {
            if (!isInWorld(npc, player.getWorld())) continue;
            if (!this.isInZone(npc)) continue;
            if (npc.isSitting()) continue;
            if (npc.isDeparting()) continue;

            double distanceSquared = distanceSquared(npc.getCurrent(), player.getLocation());
            if (distanceSquared > closestDistanceSquared) continue;

            closestDistanceSquared = distanceSquared;
            closest = npc;
        }

        return closest;
    }

    public int countInWorld(World world) {
        int count = 0;

        for (NpcRegistration npc : this.registry.getAll()) {
            if (npc.isFollowing()) continue;
            if (npc.isSitting()) continue;
            if (npc.hasTargetChair()) continue;
            if (npc.isDeparting()) continue;
            if (!isInWorld(npc, world)) continue;

            count++;
        }

        return count;
    }

    public NpcRegistrationRegistry getRegistry() {
        return this.registry;
    }

    public NpcRegistrationQueue getQueue() {
        return this.queue;
    }

    public NpcDepartureManager getDepartureManager() {
        return this.departureManager;
    }

    private void clearSitting(NpcRegistration npc) {
        if (npc.isSitting() && npc.getSitting() != null) {
            npc.getSitting().clearOccupant();
        }

        npc.stopSitting();
    }

    private boolean isInWorld(NpcRegistration npc, World world) {
        return npc.getWorld().getName().equals(world.getName());
    }

    private double distanceSquared(Location from, Location to) {
        double dx = from.getX() - to.getX();
        double dy = from.getY() - to.getY();
        double dz = from.getZ() - to.getZ();

        return dx * dx + dy * dy + dz * dz;
    }

    private long extractIndex(String key) {
        try {
            return Long.parseLong(key.substring(key.lastIndexOf(KEY_SEPARATOR.charAt(0)) + 1));
        } catch (NumberFormatException exception) {
            return 0L;
        }
    }
}