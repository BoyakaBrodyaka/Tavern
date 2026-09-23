package me.boyakabrodyaka.cuisine.kitchen.recipe;

import lombok.Getter;

@Getter
public class KitchenRecipeResult {

    private final String id;
    private final int amount;

    public KitchenRecipeResult(String id, int amount) {
        this.id = id;
        this.amount = Math.max(1, amount);
    }
}