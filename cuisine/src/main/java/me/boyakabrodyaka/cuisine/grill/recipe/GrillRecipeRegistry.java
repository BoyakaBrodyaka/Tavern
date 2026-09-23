package me.boyakabrodyaka.cuisine.grill.recipe;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class GrillRecipeRegistry {

    private static final String GOBLIN_WORM_RAW = "goblin_worm";
    private static final String GOBLIN_WORM_COOKED = "tasty_goblin_worm";
    private static final String GOBLIN_WORM_BURNT = "minecraft:rotten_flesh";

    private final ConcurrentHashMap<String, GrillRecipe> recipes = new ConcurrentHashMap<>();

    public GrillRecipeRegistry() { register(GOBLIN_WORM_RAW, GOBLIN_WORM_COOKED, GOBLIN_WORM_BURNT); }

    private void register(String rawId, String cookedId, String burntId) { this.recipes.put(rawId, new GrillRecipe(rawId, cookedId, burntId)); }

    public GrillRecipe get(String rawId) {
        return this.recipes.get(rawId);
    }
    public Collection<GrillRecipe> getAll() {
        return this.recipes.values();
    }
    public void clear() {
        this.recipes.clear();
    }
}