package me.boyakabrodyaka.cuisine.kitchen.station;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.kitchen.KitchenRegistry;
import org.bukkit.Location;

@RequiredArgsConstructor
public class KitchenStationMatcher {

    private final KitchenRegistry registry;

    public KitchenStation match(Location location) {
        for (KitchenStation station : this.registry.getAll()) {
            if (!station.matches(location)) continue;
            return station;
        }
        return null;
    }
}