package me.boyakabrodyaka.operation.order.label;

import org.bukkit.entity.ArmorStand;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderLabel {

    private final LinkedHashMap<String, ArmorStand> itemStands;

    public OrderLabel() {
        this.itemStands = new LinkedHashMap<>();
    }

    public Map<String, ArmorStand> getItemStands() {
        return this.itemStands;
    }

    public ArmorStand getItemStand(String dishName) {
        return this.itemStands.get(dishName);
    }

    public void putItemStand(String dishName, ArmorStand stand) {
        if (stand == null) return;
        this.itemStands.put(dishName, stand);
    }

    public void removeItemStand(String dishName) {
        this.itemStands.remove(dishName);
    }

    public boolean isEmpty() {
        return this.itemStands.isEmpty();
    }
}