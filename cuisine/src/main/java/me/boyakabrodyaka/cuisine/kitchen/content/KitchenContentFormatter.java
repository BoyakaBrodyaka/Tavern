package me.boyakabrodyaka.cuisine.kitchen.content;

import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredient;

public class KitchenContentFormatter {

    private static final String UNKNOWN_FORMAT = "§7? x%d";
    private static final String FORMAT = "%s §7x%d";

    public String format(KitchenIngredient ingredient, int amount) {
        if (ingredient == null) return String.format(UNKNOWN_FORMAT, amount);

        return String.format(FORMAT, ingredient.getDisplayName(), amount);
    }
}