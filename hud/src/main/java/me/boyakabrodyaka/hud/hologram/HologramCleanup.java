package me.boyakabrodyaka.hud.hologram;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HologramCleanup {

    private static final String WORLD_SUFFIX = "_map";

    private final List<String> hologramLines;

    public HologramCleanup(String lineRegistrationTop, String lineRegistrationBottom,
                           String lineTableTop, String lineTableBottom) {
        this.hologramLines = Collections.unmodifiableList(
                Arrays.asList(lineRegistrationTop, lineRegistrationBottom, lineTableTop, lineTableBottom)
        );
    }

    public void cleanupWorld(World world) {
        if (world == null) return;

        for (Entity entity : world.getEntities()) {
            if (!(entity instanceof ArmorStand)) continue;

            ArmorStand stand = (ArmorStand) entity;
            String name = stand.getCustomName();

            if (name == null) continue;
            if (!this.hologramLines.contains(name)) continue;

            stand.remove();
        }
    }

    public void cleanupAll() {
        for (World world : Bukkit.getWorlds()) {
            if (!world.getName().contains(WORLD_SUFFIX)) continue;

            cleanupWorld(world);
        }
    }
}