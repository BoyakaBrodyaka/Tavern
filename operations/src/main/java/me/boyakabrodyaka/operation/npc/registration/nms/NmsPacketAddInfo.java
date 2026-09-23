package me.boyakabrodyaka.operation.npc.registration.nms;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;

public class NmsPacketAddInfo {

    public PacketPlayOutPlayerInfo build(EntityPlayer entity) {
        return new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entity);
    }
}