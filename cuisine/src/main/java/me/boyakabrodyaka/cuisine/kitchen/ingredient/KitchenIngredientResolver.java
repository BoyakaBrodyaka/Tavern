package me.boyakabrodyaka.cuisine.kitchen.ingredient;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.kitchen.bridge.KitchenModBridge;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class KitchenIngredientResolver {

    private static final String MINECRAFT_PREFIX = "minecraft:";
    private static final String TASTY_GOBLIN_WORM = "tasty_goblin_worm";
    private static final String WORM_STEW = "worm_stew";

    private final KitchenModBridge modBridge;

    public String resolveId(ItemStack item) {
        if (item == null) return null;
        if (item.getType() == Material.AIR) return null;

        String modId = resolveModId(item);
        if (modId != null) return modId;

        return MINECRAFT_PREFIX + item.getType().name().toLowerCase();
    }

    private String resolveModId(ItemStack item) {
        if (this.modBridge == null) return null;
        if (!this.modBridge.isAvailable()) return null;

        String name = item.getType().name().toLowerCase();

        if (name.contains(TASTY_GOBLIN_WORM)) return TASTY_GOBLIN_WORM;
        if (name.contains(WORM_STEW)) return WORM_STEW;

        return null;
    }
}