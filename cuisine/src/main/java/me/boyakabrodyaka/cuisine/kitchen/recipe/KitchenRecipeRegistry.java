package me.boyakabrodyaka.cuisine.kitchen.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class KitchenRecipeRegistry {

    private static final String WORM_STEW_KEY = "worm_stew";
    private static final String WORM_STEW_INGREDIENT = "tasty_goblin_worm";
    private static final int WORM_STEW_INGREDIENT_AMOUNT = 4;
    private static final int WORM_STEW_RESULT_AMOUNT = 1;

    private final ConcurrentHashMap<String, KitchenRecipe> recipes = new ConcurrentHashMap<>();

    public KitchenRecipeRegistry() {
        register(
                WORM_STEW_KEY,
                Arrays.asList(new KitchenRecipeIngredient(WORM_STEW_INGREDIENT, WORM_STEW_INGREDIENT_AMOUNT)),
                new KitchenRecipeResult(WORM_STEW_KEY, WORM_STEW_RESULT_AMOUNT)
        );
    }

    private void register(String key, List<KitchenRecipeIngredient> ingredients, KitchenRecipeResult result) {
        this.recipes.put(key, new KitchenRecipe(key, ingredients, result));
    }

    public Collection<KitchenRecipe> getAll() {
        return this.recipes.values();
    }

    public List<KitchenRecipe> asList() {
        return new ArrayList<>(this.recipes.values());
    }

    public void clear() {
        this.recipes.clear();
    }
}