package me.boyakabrodyaka.operation.npc.registration;

public class NpcRegistrationZone {

    private final NpcRegistrationArea area;

    public NpcRegistrationZone() {
        this.area = new NpcRegistrationArea(
                NpcRegistrationCoordinate.ZONE_MIN_X.getValue(),
                NpcRegistrationCoordinate.ZONE_MIN_Y.getValue(),
                NpcRegistrationCoordinate.ZONE_MIN_Z.getValue(),
                NpcRegistrationCoordinate.ZONE_MAX_X.getValue(),
                NpcRegistrationCoordinate.ZONE_MAX_Y.getValue(),
                NpcRegistrationCoordinate.ZONE_MAX_Z.getValue()
        );
    }

    public boolean contains(double x, double y, double z) {
        return this.area.contains(x, y, z);
    }
}