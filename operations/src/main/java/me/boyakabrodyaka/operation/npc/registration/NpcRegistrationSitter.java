package me.boyakabrodyaka.operation.npc.registration;

import me.boyakabrodyaka.operation.dining.chair.DiningChair;
import org.bukkit.Location;

public class NpcRegistrationSitter {

    private static final float DEFAULT_PITCH = 0.0F;

    public void sit(NpcRegistration npc, DiningChair chair) {
        if (npc == null || chair == null) return;

        Location chairLocation = chair.toLocation(npc.getWorld());
        if (chairLocation == null) return;

        chair.setOccupantUuid(npc.getUuid().toString());
        npc.setSitting(chair);

        float yaw = computeYaw(
                chairLocation.getX(),
                chairLocation.getZ(),
                npc.getCenterX(),
                npc.getCenterZ()
        );

        Location seatLocation = new Location(
                npc.getWorld(),
                chairLocation.getX(),
                chairLocation.getY(),
                chairLocation.getZ(),
                yaw,
                DEFAULT_PITCH
        );

        npc.setCurrent(seatLocation);
    }

    public void standUp(NpcRegistration npc) {
        if (npc == null) return;
        if (!npc.isSitting()) return;

        DiningChair chair = npc.getSitting();
        if (chair != null) chair.clearOccupant();

        npc.stopSitting();
    }

    private float computeYaw(double fromX, double fromZ, double toX, double toZ) {
        double dx = toX - fromX;
        double dz = toZ - fromZ;

        return (float) Math.toDegrees(Math.atan2(-dx, dz));
    }
}