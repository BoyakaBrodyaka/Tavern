package me.boyakabrodyaka.operation.npc.registration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NpcRegistrationCoordinate {

    SPAWN_X(-2.5),
    SPAWN_Y(61.0),
    SPAWN_Z(30.5),

    TARGET_X(-2.532),
    TARGET_Y(61.0),
    TARGET_Z(17.395),

    ZONE_MIN_X(-3.032),
    ZONE_MIN_Y(60.5),
    ZONE_MIN_Z(16.895),
    ZONE_MAX_X(-2.032),
    ZONE_MAX_Y(61.5),
    ZONE_MAX_Z(17.895);

    private final double value;
}