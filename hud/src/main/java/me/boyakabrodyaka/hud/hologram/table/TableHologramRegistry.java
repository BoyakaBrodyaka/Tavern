package me.boyakabrodyaka.hud.hologram.table;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class TableHologramRegistry {

    private final ConcurrentHashMap<String, TableHologramHolder> holograms = new ConcurrentHashMap<>();

    public void register(String worldName, TableHologramHolder holder) {
        this.holograms.put(worldName, holder);
    }

    public TableHologramHolder get(String worldName) {
        return this.holograms.get(worldName);
    }

    public boolean has(String worldName) {
        return this.holograms.containsKey(worldName);
    }

    public void unregister(String worldName) {
        this.holograms.remove(worldName);
    }

    public Collection<TableHologramHolder> getAll() {
        return this.holograms.values();
    }

    public void clear() {
        this.holograms.clear();
    }
}