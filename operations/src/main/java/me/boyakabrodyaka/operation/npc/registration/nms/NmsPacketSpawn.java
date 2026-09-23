package me.boyakabrodyaka.operation.npc.registration.nms;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;

public class NmsPacketSpawn {

    public PacketPlayOutNamedEntitySpawn build(EntityPlayer entity) {
        return new PacketPlayOutNamedEntitySpawn(entity);
    }
}