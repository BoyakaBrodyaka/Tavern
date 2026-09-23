package me.boyakabrodyaka.cuisine.grill.ingredient;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GrillIngredientResolver {

    private static final String MINECRAFT_PREFIX = "minecraft:";
    private static final String TASTY_GOBLIN_WORM = "tasty_goblin_worm";
    private static final String GOBLIN_WORM = "goblin_worm";

    public String resolveId(ItemStack item) {
        if (item == null) return null;
        if (item.getType() == Material.AIR) return null;

        String modId = resolveModId(item);
        if (modId != null) return modId;

        return MINECRAFT_PREFIX + item.getType().name().toLowerCase();
    }

    private String resolveModId(ItemStack item) {
        String name = item.getType().name().toLowerCase();

        if (name.contains(TASTY_GOBLIN_WORM)) return null;
        if (name.contains(GOBLIN_WORM)) return GOBLIN_WORM;

        return null;
    }
}