package me.boyakabrodyaka.operation.npc.registration;

import lombok.Getter;
import me.boyakabrodyaka.operation.npc.registration.nms.NmsDestroyer;
import me.boyakabrodyaka.operation.npc.registration.nms.NmsEntityFactory;
import me.boyakabrodyaka.operation.npc.registration.nms.NmsMover;
import me.boyakabrodyaka.operation.npc.registration.nms.NmsPacketSender;
import me.boyakabrodyaka.operation.npc.registration.nms.NmsRegistry;
import me.boyakabrodyaka.operation.npc.registration.nms.NmsSpawner;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NpcRegistrationSpawner {

    @Getter
    private final NmsRegistry registry;

    private final NmsSpawner spawner;
    private final NmsMover mover;
    private final NmsDestroyer destroyer;
    private final NpcRegistrationFacing facing;

    public NpcRegistrationSpawner(JavaPlugin plugin) {
        NmsPacketSender sender = new NmsPacketSender();
        this.registry = new NmsRegistry();
        this.spawner = new NmsSpawner(new NmsEntityFactory(), this.registry, sender);
        this.mover = new NmsMover(sender, this.registry);
        this.destroyer = new NmsDestroyer(sender, this.registry, plugin);
        this.facing = new NpcRegistrationFacing();
    }

    public void spawn(NpcRegistration npc) {
        this.facing.apply(npc);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isInWorld(player, npc.getWorld())) continue;

            this.spawner.spawn(player, npc);
        }
    }

    public void destroy(NpcRegistration npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isInWorld(player, npc.getWorld())) continue;

            this.destroyer.destroy(player, npc);
        }
    }

    public void destroyFor(Player player, NpcRegistration npc) {
        this.destroyer.destroy(player, npc);
    }

    public void move(NpcRegistration npc) {
        if (shouldFaceNorth(npc)) {
            this.facing.apply(npc);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isInWorld(player, npc.getWorld())) continue;
            this.mover.move(player, npc);
        }
    }

    private boolean shouldFaceNorth(NpcRegistration npc) {
        if (npc.isFollowing()) return false;
        if (npc.isSitting()) return false;
        if (npc.isDeparting()) return false;
        if (npc.hasTargetChair()) return false;

        return true;
    }

    private boolean isInWorld(Player player, World world) {
        return player.getWorld().getName().equals(world.getName());
    }
}