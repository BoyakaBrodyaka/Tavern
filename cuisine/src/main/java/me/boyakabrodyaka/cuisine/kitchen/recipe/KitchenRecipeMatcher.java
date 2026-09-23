package me.boyakabrodyaka.cuisine.kitchen.recipe;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContent;

@RequiredArgsConstructor
public class KitchenRecipeMatcher {

    private final KitchenRecipeRegistry registry;
    private final KitchenRecipeChecker checker;

    public KitchenRecipe find(KitchenContent content) {
        if (content == null) return null;
        if (content.isEmpty()) return null;

        for (KitchenRecipe recipe : this.registry.getAll()) {
            if (!this.checker.matches(recipe, content)) continue;
            return recipe;
        }
        return null;
    }
}