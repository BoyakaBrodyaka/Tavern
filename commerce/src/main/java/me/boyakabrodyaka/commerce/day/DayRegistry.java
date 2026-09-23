package me.boyakabrodyaka.commerce.day;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DayRegistry {

    private final ConcurrentHashMap<UUID, Long> startedAt = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Integer> currentDay = new ConcurrentHashMap<>();

    public void markStarted(UUID uuid, long timestamp) {
        this.startedAt.put(uuid, timestamp);
    }

    public void reset(UUID uuid) {
        this.startedAt.remove(uuid);
    }

    public long getStartedAt(UUID uuid) {
        Long value = this.startedAt.get(uuid);
        return value == null ? 0L : value;
    }

    public boolean isStarted(UUID uuid) {
        return this.startedAt.containsKey(uuid);
    }

    public int getCurrentDay(UUID uuid) {
        Integer value = this.currentDay.get(uuid);
        return value == null ? 0 : value;
    }

    public void setCurrentDay(UUID uuid, int day) {
        this.currentDay.put(uuid, day);
    }

    public void clear() {
        this.startedAt.clear();
        this.currentDay.clear();
    }
}