package me.boyakabrodyaka.cuisine.grill.recipe;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GrillRecipeMatcher {

    private final GrillRecipeRegistry registry;

    public GrillRecipe find(String rawId) {
        return this.registry.get(rawId);
    }
}