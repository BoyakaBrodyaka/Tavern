package me.boyakabrodyaka.operation.registration;

import lombok.Getter;

@Getter
public class RegistrationZone {

    private final RegistrationArea area;

    public RegistrationZone() {
        this.area = new RegistrationArea(
                RegistrationCoordinate.MIN_X.getValue(),
                RegistrationCoordinate.MIN_Y.getValue(),
                RegistrationCoordinate.MIN_Z.getValue(),
                RegistrationCoordinate.MAX_X.getValue(),
                RegistrationCoordinate.MAX_Y.getValue(),
                RegistrationCoordinate.MAX_Z.getValue()
        );
    }

    public boolean contains(double x, double y, double z) {
        return this.area.contains(x, y, z);
    }
}