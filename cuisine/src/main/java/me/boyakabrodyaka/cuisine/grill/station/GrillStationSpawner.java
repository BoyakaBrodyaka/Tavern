package me.boyakabrodyaka.cuisine.grill.station;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.grill.GrillRegistry;
import org.bukkit.World;

@RequiredArgsConstructor
public class GrillStationSpawner {

    private final GrillRegistry registry;
    private final GrillStationFactory factory;

    public GrillStation spawn(World world) {
        GrillStation station = this.factory.create(world);
        this.registry.register(station);

        return station;
    }
}