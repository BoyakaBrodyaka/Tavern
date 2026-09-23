package me.boyakabrodyaka.cuisine.kitchen.station;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.kitchen.KitchenRegistry;

@RequiredArgsConstructor
public class KitchenStationRemover {

    private final KitchenRegistry registry;

    public void remove(String key) {
        this.registry.unregister(key);
    }
}