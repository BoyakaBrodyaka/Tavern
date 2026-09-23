package me.boyakabrodyaka.cuisine.kitchen.station;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.kitchen.KitchenRegistry;
import org.bukkit.World;

@RequiredArgsConstructor
public class KitchenStationSpawner {

    private final KitchenRegistry registry;
    private final KitchenStationFactory factory;

    public KitchenStation spawn(World world) {
        KitchenStation station = this.factory.create(world);
        this.registry.register(station);

        return station;
    }
}