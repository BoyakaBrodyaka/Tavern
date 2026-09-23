package me.boyakabrodyaka.cuisine.kitchen.ingredient;

import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class KitchenIngredientMatcher {

    private final KitchenIngredientRegistry registry;
    private final KitchenIngredientResolver resolver;

    public KitchenIngredient match(ItemStack item) {
        String id = this.resolver.resolveId(item);
        if (id == null) return null;

        return this.registry.get(id);
    }
}