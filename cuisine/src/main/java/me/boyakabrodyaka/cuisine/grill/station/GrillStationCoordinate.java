package me.boyakabrodyaka.cuisine.grill.station;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GrillStationCoordinate {

    X(-9.0),
    Y(61.0),
    Z(4.0),

    LABEL_OFFSET_Y(1.2),
    LABEL_CENTER_OFFSET(0.5);

    private final double value;
}