package me.boyakabrodyaka.cuisine.grill.content.state;

import lombok.Getter;

@Getter
public class GrillContentTiming {

    private static final long BURNT_DURATION = Long.MAX_VALUE;

    private final long rawMs;
    private final long cookedMs;

    public GrillContentTiming(long rawMs, long cookedMs) {
        this.rawMs = Math.max(0L, rawMs);
        this.cookedMs = Math.max(0L, cookedMs);
    }

    public long getDuration(GrillContentStage stage) {
        switch (stage) {
            case RAW: return this.rawMs;
            case COOKED: return this.cookedMs;
            default: return BURNT_DURATION;
        }
    }
}