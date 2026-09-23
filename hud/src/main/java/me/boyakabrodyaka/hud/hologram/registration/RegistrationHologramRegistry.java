package me.boyakabrodyaka.hud.hologram.registration;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class RegistrationHologramRegistry {

    private final ConcurrentHashMap<String, RegistrationHologramHolder> holograms = new ConcurrentHashMap<>();

    public void register(String worldName, RegistrationHologramHolder holder) {
        this.holograms.put(worldName, holder);
    }

    public RegistrationHologramHolder get(String worldName) {
        return this.holograms.get(worldName);
    }

    public boolean has(String worldName) {
        return this.holograms.containsKey(worldName);
    }

    public void unregister(String worldName) {
        this.holograms.remove(worldName);
    }

    public Collection<RegistrationHologramHolder> getAll() {
        return this.holograms.values();
    }

    public void clear() {
        this.holograms.clear();
    }
}