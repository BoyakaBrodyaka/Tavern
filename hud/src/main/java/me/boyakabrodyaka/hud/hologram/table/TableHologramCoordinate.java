package me.boyakabrodyaka.hud.hologram.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TableHologramCoordinate {

    X(4.5),
    Y(65.0),
    Z(3.5),

    LINE_SPACING(0.28);

    private final double value;
}