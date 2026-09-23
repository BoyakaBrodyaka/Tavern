package me.boyakabrodyaka.cuisine.grill.content;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class GrillContentRegistry {

    private final ConcurrentHashMap<String, GrillContent> contents = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, GrillContentHolder> holders = new ConcurrentHashMap<>();

    public GrillContent get(String key) {
        return this.contents.computeIfAbsent(key, GrillContent::new);
    }

    public GrillContentHolder getHolder(String key) {
        return this.holders.computeIfAbsent(key, k -> new GrillContentHolder());
    }

    public Collection<String> getAllKeys() {
        return this.holders.keySet();
    }

    public Collection<GrillContentHolder> getAllHolders() {
        return this.holders.values();
    }

    public void clear() {
        this.contents.clear();
        this.holders.clear();
    }
}