package me.boyakabrodyaka.commerce.energy;

import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class EnergyManager {

    private final EnergySettings settings;
    private final ConcurrentHashMap<String, Energy> energies = new ConcurrentHashMap<>();

    public Energy get(Player player) {
        World world = player.getWorld();
        if (world == null) return null;
        return getOrCreate(world.getName());
    }

    public Energy get(String key) { return this.energies.get(key); }

    public Energy getOrCreate(String key) {
        return this.energies.computeIfAbsent(key, k -> new Energy(
                this.settings.getMax(),
                this.settings.getSprintCost(),
                this.settings.getRegenAmount()
        ));
    }

    public void remove(String key) { this.energies.remove(key); }

    public void clear() { this.energies.clear(); }
}