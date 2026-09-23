package me.boyakabrodyaka.commerce.day;

import lombok.Getter;

@Getter
public enum DayCoordinate {

    DURATION_MS(300000L),
    WORLD_SUFFIX("_map");

    private final long longValue;
    private final String key;

    DayCoordinate(long longValue) {
        this.longValue = longValue;
        this.key = null;
    }

    DayCoordinate(String key) {
        this.longValue = 0L;
        this.key = key;
    }
}