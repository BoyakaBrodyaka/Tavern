package me.boyakabrodyaka.operation.dining.chair;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class DiningChairRegistry {

    private final ConcurrentHashMap<String, DiningChair> chairs = new ConcurrentHashMap<>();

    public void register(DiningChair chair) {
        this.chairs.put(chair.getKey(), chair);
    }

    public DiningChair get(String key) {
        return this.chairs.get(key);
    }

    public Collection<DiningChair> getAll() {
        return this.chairs.values();
    }

    public int size() {
        return this.chairs.size();
    }

    public void clear() {
        this.chairs.clear();
    }

    public DiningChair findNearby(Location location, double radius) {
        if (location == null) return null;

        DiningChair closest = null;
        double closestDistanceSquared = radius * radius;

        for (DiningChair chair : this.chairs.values()) {
            Location chairLocation = chair.toLocation(location.getWorld());

            double dx = chairLocation.getX() - location.getX();
            double dy = chairLocation.getY() - location.getY();
            double dz = chairLocation.getZ() - location.getZ();

            double distanceSquared = dx * dx + dy * dy + dz * dz;
            if (distanceSquared > closestDistanceSquared) continue;

            closestDistanceSquared = distanceSquared;
            closest = chair;
        }

        return closest;
    }
}