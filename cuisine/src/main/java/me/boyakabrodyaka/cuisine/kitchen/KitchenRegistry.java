package me.boyakabrodyaka.cuisine.kitchen;

import me.boyakabrodyaka.cuisine.kitchen.station.KitchenStation;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class KitchenRegistry {

    private final ConcurrentHashMap<String, KitchenStation> stations = new ConcurrentHashMap<>();

    public void register(KitchenStation station) {
        this.stations.put(station.getKey(), station);
    }

    public KitchenStation get(String key) {
        return this.stations.get(key);
    }

    public boolean has(String key) {
        return this.stations.containsKey(key);
    }

    public void unregister(String key) {
        this.stations.remove(key);
    }

    public Collection<KitchenStation> getAll() {
        return this.stations.values();
    }

    public void clear() {
        this.stations.clear();
    }
}