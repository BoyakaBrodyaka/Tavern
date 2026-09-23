package me.boyakabrodyaka.cuisine.kitchen.recipe;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredientStackFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@RequiredArgsConstructor
public class KitchenRecipeResultGiver {

    private final KitchenIngredientStackFactory stackFactory;

    public boolean give(Player player, KitchenRecipe recipe) {
        if (player == null) return false;
        if (recipe == null) return false;

        KitchenRecipeResult result = recipe.getResult();
        if (result == null) return false;

        ItemStack item = this.stackFactory.create(result.getId(), result.getAmount());
        if (item == null) return false;

        PlayerInventory inventory = player.getInventory();
        inventory.addItem(item);
        player.updateInventory();

        return true;
    }
}