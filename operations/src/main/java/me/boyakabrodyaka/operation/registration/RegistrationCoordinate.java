package me.boyakabrodyaka.operation.registration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegistrationCoordinate {

    MIN_X(-2.511),
    MIN_Y(60.0),
    MIN_Z(12.874),
    MAX_X(-1.008),
    MAX_Y(64.0),
    MAX_Z(15.867);

    private final double value;
}