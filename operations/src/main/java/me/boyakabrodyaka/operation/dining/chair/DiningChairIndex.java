package me.boyakabrodyaka.operation.dining.chair;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiningChairIndex {

    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4);

    private final int index;
}