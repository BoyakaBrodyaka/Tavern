package me.boyakabrodyaka.hud.hologram.registration;

import org.bukkit.Location;
import org.bukkit.World;

public class RegistrationHologramManager {

    private static final String LINE_TOP = "§6§lРегистратура";
    private static final String LINE_BOTTOM = "§e1";

    private final RegistrationHologramRegistry registry;
    private final RegistrationHologramSpawner spawner;
    private final RegistrationHologramRemover remover;

    public RegistrationHologramManager() {
        this.registry = new RegistrationHologramRegistry();
        this.spawner = new RegistrationHologramSpawner();
        this.remover = new RegistrationHologramRemover();
    }

    public void spawn(World world) {
        String worldName = world.getName();
        if (this.registry.has(worldName)) return;

        RegistrationHologram hologram = new RegistrationHologram(world);
        Location base = hologram.toLocation();
        if (base == null) return;

        RegistrationHologramHolder holder = new RegistrationHologramHolder();
        double spacing = RegistrationHologramCoordinate.LINE_SPACING.getValue();

        holder.add(this.spawner.spawnLine(base.clone().add(0, spacing, 0), LINE_TOP));
        holder.add(this.spawner.spawnLine(base.clone(), LINE_BOTTOM));

        this.registry.register(worldName, holder);
    }

    public void remove(World world) {
        String worldName = world.getName();
        RegistrationHologramHolder holder = this.registry.get(worldName);
        if (holder == null) return;

        this.remover.remove(holder);
        this.registry.unregister(worldName);
    }

    public void clear() {
        for (RegistrationHologramHolder holder : this.registry.getAll()) this.remover.remove(holder);
        this.registry.clear();
    }

    public String getLineTop() {
        return LINE_TOP;
    }

    public String getLineBottom() {
        return LINE_BOTTOM;
    }
}