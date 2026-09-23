package me.boyakabrodyaka.operation.dining;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiningCoordinate {

    TABLE_MIN_X(3.0),
    TABLE_MIN_Y(60.0),
    TABLE_MIN_Z(2.0),
    TABLE_MAX_X(5.0),
    TABLE_MAX_Y(62.0),
    TABLE_MAX_Z(4.0),

    CHAIR_FACING_RANGE(0.5);

    private final double value;
}