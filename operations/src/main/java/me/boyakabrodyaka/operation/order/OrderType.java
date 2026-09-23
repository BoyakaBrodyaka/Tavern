package me.boyakabrodyaka.operation.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderType {

    WITCH("witch"),
    GOBLIN("goblin"),
    ORC("orc");

    private final String key;
}