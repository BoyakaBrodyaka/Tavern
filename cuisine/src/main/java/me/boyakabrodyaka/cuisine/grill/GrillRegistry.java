package me.boyakabrodyaka.cuisine.grill;

import me.boyakabrodyaka.cuisine.grill.station.GrillStation;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class GrillRegistry {

    private final ConcurrentHashMap<String, GrillStation> stations = new ConcurrentHashMap<>();

    public void register(GrillStation station) {
        this.stations.put(station.getKey(), station);
    }

    public GrillStation get(String key) {
        return this.stations.get(key);
    }

    public boolean has(String key) {
        return this.stations.containsKey(key);
    }

    public Collection<GrillStation> getAll() {
        return this.stations.values();
    }

    public void clear() {
        this.stations.clear();
    }
}