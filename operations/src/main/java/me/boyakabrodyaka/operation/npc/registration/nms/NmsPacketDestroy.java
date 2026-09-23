package me.boyakabrodyaka.operation.npc.registration.nms;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;

public class NmsPacketDestroy {

    public PacketPlayOutEntityDestroy buildDestroy(int entityId) {
        return new PacketPlayOutEntityDestroy(new int[]{entityId});
    }

    public PacketPlayOutPlayerInfo buildRemoveInfo(EntityPlayer entity) {
        return new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entity);
    }
}