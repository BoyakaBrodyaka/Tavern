package me.boyakabrodyaka.operation.npc.registration.nms;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityTeleport;

public class NmsPacketTeleport {

    private static final float YAW_DIVISOR = 256.0F;
    private static final float DEGREES_IN_CIRCLE = 360.0F;

    public PacketPlayOutEntityTeleport buildTeleport(Entity entity, double x, double y, double z, float yaw, float pitch) {
        entity.setLocation(x, y, z, yaw, pitch);
        entity.setPosition(x, y, z);
        entity.yaw = yaw;
        entity.pitch = pitch;
        return new PacketPlayOutEntityTeleport(entity);
    }

    public PacketPlayOutEntityHeadRotation buildHead(Entity entity, float yaw) {
        byte packedYaw = (byte) (yaw * YAW_DIVISOR / DEGREES_IN_CIRCLE);
        return new PacketPlayOutEntityHeadRotation(entity, packedYaw);
    }
}