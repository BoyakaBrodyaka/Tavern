package me.boyakabrodyaka.cuisine.kitchen.recipe;

import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContent;

import java.util.List;

public class KitchenRecipeChecker {

    public boolean matches(KitchenRecipe recipe, KitchenContent content) {
        if (recipe == null) return false;
        if (content == null) return false;

        List<KitchenRecipeIngredient> ingredients = recipe.getIngredients();

        if (ingredients.size() != content.getItems().size()) return false;

        for (KitchenRecipeIngredient ingredient : ingredients) {
            int have = content.getAmount(ingredient.getId());
            if (have != ingredient.getAmount()) return false;
        }

        return true;
    }
}