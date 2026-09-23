package me.boyakabrodyaka.cuisine.kitchen.content;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class KitchenContentRegistry {

    private final ConcurrentHashMap<String, KitchenContent> contents = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, KitchenContentHolder> holders = new ConcurrentHashMap<>();

    public KitchenContent get(String key) { return this.contents.computeIfAbsent(key, KitchenContent::new); }

    public KitchenContentHolder getHolder(String key) { return this.holders.computeIfAbsent(key, k -> new KitchenContentHolder()); }

    public Collection<KitchenContentHolder> getAllHolders() { return this.holders.values(); }

    public boolean has(String key) {
        return this.contents.containsKey(key);
    }

    public void remove(String key) {
        this.contents.remove(key);
        this.holders.remove(key);
    }

    public void clear() {
        this.contents.clear();
        this.holders.clear();
    }
}