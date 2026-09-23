package me.boyakabrodyaka.operation.npc.registration;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class NpcRegistrationRegistry {

    private final ConcurrentHashMap<String, NpcRegistration> npcs = new ConcurrentHashMap<>();

    public void register(NpcRegistration npc) {
        if (npc == null) return;
        this.npcs.put(npc.getKey(), npc);
    }

    public NpcRegistration get(String key) {
        return this.npcs.get(key);
    }

    public void unregister(String key) {
        this.npcs.remove(key);
    }

    public Collection<NpcRegistration> getAll() {
        return this.npcs.values();
    }

    public int size() {
        return this.npcs.size();
    }

    public void clear() {
        this.npcs.clear();
    }
}