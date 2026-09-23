package me.boyakabrodyaka.operation.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderCoordinate {

    MIN_ITEMS(1),
    MAX_ITEMS(3),

    MIN_AMOUNT(1),
    MAX_AMOUNT(3),

    LABEL_OFFSET_Y(1.8),
    LABEL_LINE_HEIGHT(0.25),

    QUEUE_TIMEOUT_MS(20000L),
    LABEL_TIMEOUT_MS(60000L);

    private final double value;

    public int getIntValue() {
        return (int) this.value;
    }

    public long getLongValue() {
        return (long) this.value;
    }
}