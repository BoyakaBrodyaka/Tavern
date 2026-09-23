package me.boyakabrodyaka.operation.order;

import lombok.Getter;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class OrderRequest {

    private final NpcRegistration npc;
    private final LinkedHashMap<String, Integer> items;
    private final long timestamp;

    public OrderRequest(NpcRegistration npc, LinkedHashMap<String, Integer> items, long timestamp) {
        this.npc = npc;
        this.items = items;
        this.timestamp = timestamp;
    }

    public Map<String, Integer> getItems() {
        return this.items;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public boolean hasItem(String dishName) {
        return this.items.containsKey(dishName);
    }

    public int getAmount(String dishName) {
        return this.items.getOrDefault(dishName, 0);
    }

    public boolean removeOne(String dishName) {
        Integer amount = this.items.get(dishName);
        if (amount == null) return false;

        if (amount <= 1) {
            this.items.remove(dishName);
            return true;
        }

        this.items.put(dishName, amount - 1);
        return false;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - this.timestamp >= OrderCoordinate.QUEUE_TIMEOUT_MS.getLongValue();
    }
}