package me.boyakabrodyaka.cuisine.grill.content.item;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.grill.ingredient.GrillIngredientStackFactory;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

@RequiredArgsConstructor
public class GrillContentItemSpawner {

    private static final double SPAWN_OFFSET_X = 0.5D;
    private static final double SPAWN_OFFSET_Y = 0.05D;
    private static final double SPAWN_OFFSET_Z = 0.5D;
    private static final int PICKUP_DELAY = Integer.MAX_VALUE;

    private final GrillIngredientStackFactory stackFactory;

    public GrillContentItem spawn(Location location, String id, int amount) {
        World world = location.getWorld();
        if (world == null) return null;

        ItemStack stack = this.stackFactory.create(id, amount);
        if (stack == null) return null;

        Location spawnLocation = location.clone().add(SPAWN_OFFSET_X, SPAWN_OFFSET_Y, SPAWN_OFFSET_Z);

        Item item = world.dropItem(spawnLocation, stack);
        item.setPickupDelay(PICKUP_DELAY);
        item.setVelocity(new Vector(0, 0, 0));
        item.setGravity(false);
        item.setCustomNameVisible(false);
        item.setSilent(true);

        return new GrillContentItem(id, item);
    }

    public GrillContentItem replace(GrillContentItem contentItem, Location location, String newId, int amount) {
        if (contentItem == null) return null;

        Item oldEntity = contentItem.getEntity();
        if (oldEntity != null && !oldEntity.isDead()) oldEntity.remove();

        return spawn(location, newId, amount);
    }
}