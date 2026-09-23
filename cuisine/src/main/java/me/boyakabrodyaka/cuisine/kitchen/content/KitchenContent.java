package me.boyakabrodyaka.cuisine.kitchen.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class KitchenContent {

    private final String key;
    private final LinkedHashMap<String, Integer> items = new LinkedHashMap<>();

    public void add(String id, int amount) {
        if (amount <= 0) return;
        this.items.merge(id, amount, Integer::sum);
    }

    public void remove(String id, int amount) {
        if (amount <= 0) return;

        Integer current = this.items.get(id);
        if (current == null) return;

        int remaining = current - amount;
        if (remaining <= 0) {
            this.items.remove(id);
            return;
        }

        this.items.put(id, remaining);
    }

    public void removeAll() { this.items.clear(); }
    public boolean isEmpty() { return this.items.isEmpty(); }
    public int getAmount(String id) { return this.items.getOrDefault(id, 0); }
}