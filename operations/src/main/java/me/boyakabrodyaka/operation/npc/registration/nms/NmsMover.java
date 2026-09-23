package me.boyakabrodyaka.operation.npc.registration.nms;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class NmsMover {

    private final NmsPacketSender sender;
    private final NmsRegistry registry;

    public void move(Player player, NpcRegistration npc) {
        EntityPlayer entity = this.registry.get(npc.getEntityId());
        if (entity == null) return;

        Location location = npc.getCurrent();

        this.sender.send(player, new NmsPacketTeleport().buildTeleport(
                entity,
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        ));

        this.sender.send(player, new NmsPacketTeleport().buildHead(entity, location.getYaw()));
    }
}