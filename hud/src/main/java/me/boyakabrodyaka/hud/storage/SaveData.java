package me.boyakabrodyaka.hud.storage;

import lombok.Getter;

@Getter
public class SaveData {

    private final boolean occupied;
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final long createdAt;

    public SaveData(boolean occupied, String world, double x, double y, double z, float yaw, float pitch) {
        this(occupied, world, x, y, z, yaw, pitch, System.currentTimeMillis());
    }

    public SaveData(boolean occupied, String world, double x, double y, double z, float yaw, float pitch, long createdAt) {
        this.occupied = occupied;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.createdAt = createdAt;
    }
}