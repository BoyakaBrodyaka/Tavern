package me.boyakabrodyaka.commerce.day;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DayManager {

    private final DayRegistry registry;

    public boolean start(UUID uuid) {
        if (this.registry.isStarted(uuid)) return false;
        this.registry.markStarted(uuid, System.currentTimeMillis());
        return true;
    }

    public void stop(UUID uuid) { this.registry.reset(uuid); }

    public boolean isActive(UUID uuid) {
        if (!this.registry.isStarted(uuid)) return false;
        return System.currentTimeMillis() - this.registry.getStartedAt(uuid) < DayCoordinate.DURATION_MS.getLongValue();
    }

    public long getElapsed(UUID uuid) {
        if (!this.registry.isStarted(uuid)) return 0L;
        return System.currentTimeMillis() - this.registry.getStartedAt(uuid);
    }

    public long getRemaining(UUID uuid) {
        if (!this.registry.isStarted(uuid)) return 0L;
        long remaining = DayCoordinate.DURATION_MS.getLongValue() - getElapsed(uuid);
        return Math.max(remaining, 0L);
    }

    public int getCurrentDay(UUID uuid) { return this.registry.getCurrentDay(uuid); }

    public void setCurrentDay(UUID uuid, int day) { this.registry.setCurrentDay(uuid, day); }
}