package me.boyakabrodyaka.operation.npc.registration.nms;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
public class NmsDestroyer {

    private static final double DISAPPEAR_X = 0.0D;
    private static final double DISAPPEAR_Y = -100.0D;
    private static final double DISAPPEAR_Z = 0.0D;
    private static final float DISAPPEAR_YAW = 0.0F;
    private static final float DISAPPEAR_PITCH = 0.0F;
    private static final long SECOND_DESTROY_DELAY = 5L;

    private final NmsPacketSender sender;
    private final NmsRegistry registry;
    private final JavaPlugin plugin;

    public void destroy(Player player, NpcRegistration npc) {
        int entityId = npc.getEntityId();
        EntityPlayer entity = this.registry.get(entityId);

        if (entity == null) {
            this.sender.send(player, new NmsPacketDestroy().buildDestroy(entityId));
            this.registry.unregister(entityId);
            return;
        }

        this.sender.send(player, new NmsPacketDestroy().buildDestroy(entityId));
        this.sender.send(player, new NmsPacketDestroy().buildRemoveInfo(entity));

        entity.setLocation(DISAPPEAR_X, DISAPPEAR_Y, DISAPPEAR_Z, DISAPPEAR_YAW, DISAPPEAR_PITCH);
        this.sender.send(player, new NmsPacketTeleport().buildTeleport(
                entity,
                DISAPPEAR_X, DISAPPEAR_Y, DISAPPEAR_Z,
                DISAPPEAR_YAW, DISAPPEAR_PITCH
        ));

        this.registry.unregister(entityId);

        if (!this.plugin.isEnabled()) return;

        Bukkit.getScheduler().runTaskLater(this.plugin, () ->
                this.sender.send(player, new NmsPacketDestroy().buildDestroy(entityId)), SECOND_DESTROY_DELAY);
    }
}