package me.boyakabrodyaka.hud.hologram.table;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class TableHologramSpawner {

    public ArmorStand spawnLine(Location location, String text) {
        World world = location.getWorld();
        if (world == null) return null;

        ArmorStand stand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setCustomName(text);
        stand.setCustomNameVisible(true);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setInvulnerable(true);
        stand.setCollidable(false);
        stand.setSilent(true);
        stand.setSmall(true);
        stand.setBasePlate(false);
        stand.setArms(false);
        stand.setMarker(true);
        stand.setAI(false);

        return stand;
    }
}