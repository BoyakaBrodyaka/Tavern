package me.boyakabrodyaka.operation.order;

import me.boyakabrodyaka.operation.order.label.OrderLabel;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRegistry {

    private final ConcurrentHashMap<String, OrderRequest> requests = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> started = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Boolean> completed = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Boolean> taken = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> takenAt = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, OrderLabel> labels = new ConcurrentHashMap<>();

    public void register(String npcKey, OrderRequest request) {
        this.requests.put(npcKey, request);
    }

    public OrderRequest get(String npcKey) {
        return this.requests.get(npcKey);
    }

    public boolean has(String npcKey) {
        return this.requests.containsKey(npcKey);
    }

    public void remove(String npcKey) {
        this.requests.remove(npcKey);
        this.taken.remove(npcKey);
        this.takenAt.remove(npcKey);
    }

    public Collection<OrderRequest> getAll() {
        return this.requests.values();
    }

    public void markStarted(String npcKey) {
        this.started.put(npcKey, System.currentTimeMillis());
    }

    public boolean isStarted(String npcKey) {
        return this.started.containsKey(npcKey);
    }

    public long getStartedAt(String npcKey) {
        Long value = this.started.get(npcKey);
        return value == null ? 0L : value;
    }

    public void clearStarted(String npcKey) {
        this.started.remove(npcKey);
    }

    public void markCompleted(String npcKey) {
        this.completed.put(npcKey, true);
    }

    public boolean isCompleted(String npcKey) {
        return this.completed.containsKey(npcKey);
    }

    public void markTaken(String npcKey) {
        this.taken.put(npcKey, true);
        this.takenAt.put(npcKey, System.currentTimeMillis());
    }

    public boolean isTaken(String npcKey) {
        return this.taken.containsKey(npcKey);
    }

    public long getTakenAt(String npcKey) {
        Long value = this.takenAt.get(npcKey);
        return value == null ? 0L : value;
    }

    public boolean isLabelExpired(String npcKey) {
        Long start = this.takenAt.get(npcKey);
        if (start == null) return false;

        return System.currentTimeMillis() - start >= OrderCoordinate.LABEL_TIMEOUT_MS.getLongValue();
    }

    public void registerLabel(String npcKey, OrderLabel label) {
        this.labels.put(npcKey, label);
    }

    public OrderLabel getLabel(String npcKey) {
        return this.labels.get(npcKey);
    }

    public void removeLabel(String npcKey) {
        this.labels.remove(npcKey);
    }

    public Collection<OrderLabel> getAllLabels() {
        return this.labels.values();
    }

    public void clear() {
        this.requests.clear();
        this.started.clear();
        this.completed.clear();
        this.taken.clear();
        this.takenAt.clear();
        this.labels.clear();
    }
}