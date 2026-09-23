package me.boyakabrodyaka.cuisine.kitchen.recipe;

import lombok.Getter;

@Getter
public class KitchenRecipeIngredient {

    private final String id;
    private final int amount;

    public KitchenRecipeIngredient(String id, int amount) {
        this.id = id;
        this.amount = Math.max(1, amount);
    }
}