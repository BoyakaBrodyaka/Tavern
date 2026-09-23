package me.boyakabrodyaka.cuisine.kitchen.station;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KitchenStationCoordinate {

    X(-9.0),
    Y(61.0),
    Z(7.0),

    LABEL_OFFSET_Y(1.2),
    LABEL_CENTER_OFFSET(0.5);

    private final double value;
}