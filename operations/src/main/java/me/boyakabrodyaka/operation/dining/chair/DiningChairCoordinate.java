package me.boyakabrodyaka.operation.dining.chair;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiningChairCoordinate {

    CHAIR_1_X(4.5),
    CHAIR_1_Y(61.0),
    CHAIR_1_Z(1.5),

    CHAIR_2_X(6.5),
    CHAIR_2_Y(61.0),
    CHAIR_2_Z(3.5),

    CHAIR_3_X(4.5),
    CHAIR_3_Y(61.0),
    CHAIR_3_Z(5.5),

    CHAIR_4_X(2.5),
    CHAIR_4_Y(61.0),
    CHAIR_4_Z(3.5);

    private final double value;
}