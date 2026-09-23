package me.boyakabrodyaka.cuisine.kitchen.recipe;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class KitchenRecipe {

    private final String key;
    private final List<KitchenRecipeIngredient> ingredients;
    private final KitchenRecipeResult result;

    public KitchenRecipe(String key, List<KitchenRecipeIngredient> ingredients, KitchenRecipeResult result) {
        this.key = key;
        this.ingredients = Collections.unmodifiableList(ingredients);
        this.result = result;
    }
}