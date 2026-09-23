package me.boyakabrodyaka.cuisine.grill.ingredient;

import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class GrillIngredientMatcher {

    private final GrillIngredientRegistry registry;
    private final GrillIngredientResolver resolver;

    public GrillIngredient match(ItemStack item) {
        String id = this.resolver.resolveId(item);
        if (id == null) return null;

        return this.registry.get(id);
    }

    public boolean isIngredient(ItemStack item) {
        return match(item) != null;
    }
}