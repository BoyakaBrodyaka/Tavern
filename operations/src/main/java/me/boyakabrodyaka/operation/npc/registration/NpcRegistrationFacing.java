package me.boyakabrodyaka.operation.npc.registration;

import org.bukkit.Location;

public class NpcRegistrationFacing {

    public static final float NORTH_YAW = 180.0F;
    private static final float DEFAULT_PITCH = 0.0F;

    public void apply(NpcRegistration npc) {
        Location current = npc.getCurrent();

        Location next = new Location(
                current.getWorld(),
                current.getX(),
                current.getY(),
                current.getZ(),
                NORTH_YAW,
                DEFAULT_PITCH
        );

        npc.setCurrent(next);
    }
}