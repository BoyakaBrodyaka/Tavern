package me.boyakabrodyaka.operation.npc.registration;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.dining.chair.DiningChair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class NpcRegistrationWalker {

    private static final double FOLLOW_STOP_DISTANCE = 0.1D;
    private static final double NPC_HEAD_HEIGHT = 1.62D;

    private final NpcRegistrationSettings settings;
    private final NpcRegistrationSitter sitter;

    public void walk(NpcRegistration npc) {
        if (npc.isSitting()) return;

        if (npc.isDeparting()) {
            walkToDeparture(npc);
            return;
        }

        if (npc.hasTargetChair()) {
            walkToChair(npc);
            return;
        }

        if (npc.isFollowing()) {
            followPlayer(npc);
            return;
        }

        walkToTarget(npc);
    }

    private void walkToDeparture(NpcRegistration npc) {
        Location departure = npc.getDepartureLocation();
        if (departure == null) return;

        Location current = npc.getCurrent();
        double distance = distance(current, departure);

        if (distance < this.settings.getReachDistance()) return;

        Location next = step(current, departure, distance);
        npc.setCurrent(next);
        npc.setHeadYaw(next.getYaw());
    }

    private void walkToChair(NpcRegistration npc) {
        DiningChair chair = npc.getTargetChair();
        if (chair == null) return;

        Location chairLocation = chair.toLocation(npc.getWorld());
        if (chairLocation == null) return;

        Location current = npc.getCurrent();
        double distance = distance(current, chairLocation);

        if (distance < this.settings.getReachDistance()) {
            Location exact = new Location(
                    npc.getWorld(),
                    chairLocation.getX(),
                    chairLocation.getY(),
                    chairLocation.getZ(),
                    current.getYaw(),
                    current.getPitch()
            );
            npc.setCurrent(exact);
            npc.clearTargetChair();
            this.sitter.sit(npc, chair);
            return;
        }

        Location next = step(current, chairLocation, distance);
        npc.setCurrent(next);
        npc.setHeadYaw(next.getYaw());
    }

    private void walkToTarget(NpcRegistration npc) {
        Location current = npc.getCurrent();
        Location target = npc.getTarget();
        double distance = distance(current, target);

        if (distance < this.settings.getReachDistance()) {
            Location next = new Location(
                    current.getWorld(),
                    target.getX(), target.getY(), target.getZ(),
                    NpcRegistrationFacing.NORTH_YAW, 0.0F
            );
            npc.setCurrent(next);
            npc.setHeadYaw(NpcRegistrationFacing.NORTH_YAW);
            return;
        }

        Location next = step(current, target, distance);
        npc.setCurrent(next);
        npc.setHeadYaw(next.getYaw());
    }

    private void followPlayer(NpcRegistration npc) {
        Player player = Bukkit.getPlayer(npc.getFollowing());
        if (player == null || !player.isOnline()) {
            npc.stopFollowing();
            return;
        }

        if (!player.getWorld().getName().equals(npc.getWorld().getName())) {
            npc.stopFollowing();
            return;
        }

        Location current = npc.getCurrent();
        Location behind = getBehindLocation(player);
        double distanceToTarget = distance(current, behind);

        if (distanceToTarget < FOLLOW_STOP_DISTANCE) return;

        double step = Math.min(this.settings.getFollowSpeed(), distanceToTarget);
        double dx = behind.getX() - current.getX();
        double dz = behind.getZ() - current.getZ();

        double nx = current.getX() + (dx / distanceToTarget) * step;
        double nz = current.getZ() + (dz / distanceToTarget) * step;

        float bodyYaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        float[] headRotation = getLookRotation(npc, player);

        Location next = new Location(current.getWorld(), nx, current.getY(), nz, bodyYaw, 0.0F);
        npc.setCurrent(next);
        npc.setHeadYaw(headRotation[0]);
    }

    private Location step(Location current, Location target, double distance) {
        double step = Math.min(this.settings.getFollowSpeed(), distance);

        double dx = target.getX() - current.getX();
        double dy = target.getY() - current.getY();
        double dz = target.getZ() - current.getZ();

        double nx = current.getX() + (dx / distance) * step;
        double ny = current.getY() + (dy / distance) * step;
        double nz = current.getZ() + (dz / distance) * step;

        float bodyYaw = (float) Math.toDegrees(Math.atan2(-dx, dz));

        return new Location(current.getWorld(), nx, ny, nz, bodyYaw, 0.0F);
    }

    private Location getBehindLocation(Player player) {
        Location location = player.getLocation();
        double yaw = Math.toRadians(location.getYaw());
        double distance = this.settings.getFollowDistance();

        double x = location.getX() + Math.sin(yaw) * distance;
        double z = location.getZ() - Math.cos(yaw) * distance;

        return new Location(location.getWorld(), x, location.getY(), z);
    }

    private float[] getLookRotation(NpcRegistration npc, Player player) {
        Location npcLocation = npc.getCurrent();
        Location playerHead = player.getEyeLocation();

        double dx = playerHead.getX() - npcLocation.getX();
        double dy = playerHead.getY() - (npcLocation.getY() + NPC_HEAD_HEIGHT);
        double dz = playerHead.getZ() - npcLocation.getZ();

        double distanceXZ = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        float pitch = (float) Math.toDegrees(-Math.atan2(dy, distanceXZ));

        return new float[]{yaw, pitch};
    }

    private double distance(Location from, Location to) {
        double dx = from.getX() - to.getX();
        double dy = from.getY() - to.getY();
        double dz = from.getZ() - to.getZ();

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public boolean reached(NpcRegistration npc) {
        if (npc.isFollowing()) return false;
        if (npc.isSitting()) return false;
        if (npc.hasTargetChair()) return false;
        if (npc.isDeparting()) return false;

        return distance(npc.getCurrent(), npc.getTarget()) < this.settings.getReachDistance();
    }
}