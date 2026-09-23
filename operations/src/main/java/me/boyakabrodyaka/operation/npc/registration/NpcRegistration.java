package me.boyakabrodyaka.operation.npc.registration;

import lombok.Getter;
import lombok.Setter;
import me.boyakabrodyaka.operation.dining.chair.DiningChair;
import me.boyakabrodyaka.operation.npc.registration.skin.NpcSkin;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

@Getter
@Setter
public class NpcRegistration {

    private final String key;
    private final String name;
    private final UUID uuid;
    private final World world;
    private final Location spawn;
    private final NpcSkin skin;

    private int entityId;
    private Location target;
    private Location current;
    private String type;

    private UUID following;
    private DiningChair sitting;
    private DiningChair targetChair;

    private double centerX;
    private double centerY;
    private double centerZ;

    private float headYaw;

    private boolean departing;
    private Location departureLocation;

    public NpcRegistration(String key, String name, UUID uuid, int entityId, World world, Location spawn, Location target, NpcSkin skin) {
        this.key = key;
        this.name = name;
        this.uuid = uuid;
        this.entityId = entityId;
        this.world = world;
        this.spawn = spawn;
        this.target = target;
        this.current = spawn.clone();
        this.skin = skin;
        this.type = null;
        this.following = null;
        this.sitting = null;
        this.targetChair = null;
        this.centerX = 0;
        this.centerY = 0;
        this.centerZ = 0;
        this.headYaw = NpcRegistrationFacing.NORTH_YAW;
        this.departing = false;
        this.departureLocation = null;
    }

    public boolean isFollowing() {
        return this.following != null;
    }

    public void stopFollowing() {
        this.following = null;
    }

    public boolean isSitting() {
        return this.sitting != null;
    }

    public void stopSitting() {
        this.sitting = null;
    }

    public boolean hasTargetChair() {
        return this.targetChair != null;
    }

    public void clearTargetChair() {
        this.targetChair = null;
    }
}