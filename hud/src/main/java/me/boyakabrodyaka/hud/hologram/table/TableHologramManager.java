package me.boyakabrodyaka.hud.hologram.table;

import org.bukkit.Location;
import org.bukkit.World;

public class TableHologramManager {

    private static final String LINE_TOP = "§6§lСтол";
    private static final String LINE_BOTTOM = "§e1";

    private final TableHologramRegistry registry;
    private final TableHologramSpawner spawner;
    private final TableHologramRemover remover;

    public TableHologramManager() {
        this.registry = new TableHologramRegistry();
        this.spawner = new TableHologramSpawner();
        this.remover = new TableHologramRemover();
    }

    public void spawn(World world) {
        String worldName = world.getName();
        if (this.registry.has(worldName)) return;

        TableHologram hologram = new TableHologram(world);
        Location base = hologram.toLocation();
        if (base == null) return;

        TableHologramHolder holder = new TableHologramHolder();
        double spacing = TableHologramCoordinate.LINE_SPACING.getValue();

        holder.add(this.spawner.spawnLine(base.clone().add(0, spacing, 0), LINE_TOP));
        holder.add(this.spawner.spawnLine(base.clone(), LINE_BOTTOM));

        this.registry.register(worldName, holder);
    }

    public void remove(World world) {
        String worldName = world.getName();
        TableHologramHolder holder = this.registry.get(worldName);
        if (holder == null) return;

        this.remover.remove(holder);
        this.registry.unregister(worldName);
    }

    public void clear() {
        for (TableHologramHolder holder : this.registry.getAll()) this.remover.remove(holder);
        this.registry.clear();
    }

    public String getLineTop() {
        return LINE_TOP;
    }

    public String getLineBottom() {
        return LINE_BOTTOM;
    }
}