package me.boyakabrodyaka.operation.npc.registration.nms;

import net.minecraft.server.v1_12_R1.EntityPlayer;

import java.util.concurrent.ConcurrentHashMap;

public class NmsRegistry {

    private final ConcurrentHashMap<Integer, EntityPlayer> entities = new ConcurrentHashMap<>();

    public void register(int entityId, EntityPlayer entity) {
        if (entity == null) return;
        this.entities.put(entityId, entity);
    }

    public EntityPlayer get(int entityId) {
        return this.entities.get(entityId);
    }

    public void unregister(int entityId) {
        this.entities.remove(entityId);
    }

    public void clear() {
        this.entities.clear();
    }
}