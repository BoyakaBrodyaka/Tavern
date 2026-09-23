package me.boyakabrodyaka.operation.npc.registration.nms;

import net.minecraft.server.v1_12_R1.Packet;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NmsPacketSender {

    public void send(Player player, Packet<?> packet) {
        if (player == null) return;
        if (packet == null) return;

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}