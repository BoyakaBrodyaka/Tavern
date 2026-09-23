package me.boyakabrodyaka.cuisine.kitchen.ingredient;

import java.util.concurrent.ConcurrentHashMap;

public class KitchenIngredientRegistry {

    private static final String TASTY_GOBLIN_WORM_ID = "tasty_goblin_worm";
    private static final String TASTY_GOBLIN_WORM_NAME = "§dПриготовленный червяк";

    private static final String WORM_STEW_ID = "worm_stew";
    private static final String WORM_STEW_NAME = "§6Рагу из червей";

    private final ConcurrentHashMap<String, KitchenIngredient> ingredients = new ConcurrentHashMap<>();

    public KitchenIngredientRegistry() {
        register(TASTY_GOBLIN_WORM_ID, TASTY_GOBLIN_WORM_NAME);
        register(WORM_STEW_ID, WORM_STEW_NAME);
    }

    private void register(String id, String displayName) {
        this.ingredients.put(id, new KitchenIngredient(id, displayName));
    }

    public KitchenIngredient get(String id) {
        return this.ingredients.get(id);
    }

    public boolean has(String id) {
        return this.ingredients.containsKey(id);
    }

    public void clear() {
        this.ingredients.clear();
    }
}