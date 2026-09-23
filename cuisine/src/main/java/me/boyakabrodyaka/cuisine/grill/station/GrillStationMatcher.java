package me.boyakabrodyaka.cuisine.grill.station;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.grill.GrillRegistry;
import org.bukkit.Location;

@RequiredArgsConstructor
public class GrillStationMatcher {

    private final GrillRegistry registry;

    public GrillStation match(Location location) {
        for (GrillStation station : this.registry.getAll()) {
            if (!station.matches(location)) continue;
            return station;
        }
        return null;
    }
}