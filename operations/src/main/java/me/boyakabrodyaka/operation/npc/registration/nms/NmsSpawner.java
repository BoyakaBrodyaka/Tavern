package me.boyakabrodyaka.operation.npc.registration.nms;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class NmsSpawner {

    private final NmsEntityFactory factory;
    private final NmsRegistry registry;
    private final NmsPacketSender sender;

    public void spawn(Player player, NpcRegistration npc) {
        Location location = npc.getCurrent();

        EntityPlayer entity = this.factory.create(
                npc.getName(),
                npc.getUuid(),
                npc.getWorld(),
                location.getX(),
                location.getY(),
                location.getZ(),
                npc.getSkin()
        );

        if (entity == null) return;

        npc.setEntityId(entity.getId());
        this.registry.register(entity.getId(), entity);

        this.sender.send(player, new NmsPacketAddInfo().build(entity));
        this.sender.send(player, new NmsPacketSpawn().build(entity));
    }
}