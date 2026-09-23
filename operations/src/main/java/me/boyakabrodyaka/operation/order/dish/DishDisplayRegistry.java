package me.boyakabrodyaka.operation.order.dish;

import java.util.concurrent.ConcurrentHashMap;

public class DishDisplayRegistry {

    private static final String TASTY_GOBLIN_WORM_ID = "tavernmod:tasty_goblin_worm";
    private static final String TASTY_GOBLIN_WORM_NAME = "Приготовленный червяк";

    private static final String WORM_STEW_ID = "tavernmod:worm_stew";
    private static final String WORM_STEW_NAME = "Червичное рагу";

    private final ConcurrentHashMap<String, String> displayNames = new ConcurrentHashMap<>();

    public DishDisplayRegistry() {
        register(TASTY_GOBLIN_WORM_ID, TASTY_GOBLIN_WORM_NAME);
        register(WORM_STEW_ID, WORM_STEW_NAME);
    }

    public String get(String id) {
        return this.displayNames.getOrDefault(id, id);
    }

    public void clear() {
        this.displayNames.clear();
    }

    private void register(String id, String displayName) {
        this.displayNames.put(id, displayName);
    }
}