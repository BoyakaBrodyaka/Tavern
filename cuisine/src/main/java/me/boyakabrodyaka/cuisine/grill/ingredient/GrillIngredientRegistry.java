package me.boyakabrodyaka.cuisine.grill.ingredient;

import java.util.concurrent.ConcurrentHashMap;

public class GrillIngredientRegistry {

    private static final String GOBLIN_WORM_ID = "goblin_worm";
    private static final String GOBLIN_WORM_NAME = "§2Червяк";

    private final ConcurrentHashMap<String, GrillIngredient> ingredients = new ConcurrentHashMap<>();

    public GrillIngredientRegistry() {
        register(GOBLIN_WORM_ID, GOBLIN_WORM_NAME);
    }

    private void register(String id, String displayName) {
        this.ingredients.put(id, new GrillIngredient(id, displayName));
    }

    public GrillIngredient get(String id) {
        return this.ingredients.get(id);
    }

    public boolean has(String id) {
        return this.ingredients.containsKey(id);
    }

    public void clear() {
        this.ingredients.clear();
    }
}