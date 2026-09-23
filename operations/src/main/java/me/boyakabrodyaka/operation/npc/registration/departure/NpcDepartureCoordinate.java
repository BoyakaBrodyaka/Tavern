package me.boyakabrodyaka.operation.npc.registration.departure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NpcDepartureCoordinate {

    X(-2.0),
    Y(61.0),
    Z(23.0),

    QUEUE_TIMEOUT_MS(20000L),
    FOLLOW_TIMEOUT_MS(20000L),

    DISAPPEAR_X(0.0),
    DISAPPEAR_Y(-100.0),
    DISAPPEAR_Z(0.0),

    ARRIVE_DISTANCE(0.5);

    private final double value;

    public long getLongValue() {
        return (long) this.value;
    }
}