package me.boyakabrodyaka.operation.npc.registration.departure;

import java.util.concurrent.ConcurrentHashMap;

public class NpcDepartureRegistry {

    private final ConcurrentHashMap<String, Long> queueTimers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> followTimers = new ConcurrentHashMap<>();

    public void startQueue(String key) {
        this.queueTimers.put(key, System.currentTimeMillis());
        this.followTimers.remove(key);
    }

    public void startFollow(String key) {
        this.followTimers.put(key, System.currentTimeMillis());
        this.queueTimers.remove(key);
    }

    public void reset(String key) {
        this.queueTimers.remove(key);
        this.followTimers.remove(key);
    }

    public boolean expiredQueue(String key, long timeout) {
        Long start = this.queueTimers.get(key);
        if (start == null) return false;

        return System.currentTimeMillis() - start >= timeout;
    }

    public boolean expiredFollow(String key, long timeout) {
        Long start = this.followTimers.get(key);
        if (start == null) return false;

        return System.currentTimeMillis() - start >= timeout;
    }

    public boolean hasQueue(String key) {
        return this.queueTimers.containsKey(key);
    }

    public boolean hasFollow(String key) {
        return this.followTimers.containsKey(key);
    }

    public void clear() {
        this.queueTimers.clear();
        this.followTimers.clear();
    }
}