package me.boyakabrodyaka.hud.hologram.registration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegistrationHologramCoordinate {

    X(-2.511),
    Y(65.0),
    Z(15.876),

    LINE_SPACING(0.28);

    private final double value;
}