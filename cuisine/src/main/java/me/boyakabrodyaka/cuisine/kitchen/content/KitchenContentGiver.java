package me.boyakabrodyaka.cuisine.kitchen.content;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredientStackFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

@RequiredArgsConstructor
public class KitchenContentGiver {

    private final KitchenIngredientStackFactory stackFactory;

    public void giveAll(Player player, KitchenContent content) {
        if (player == null || content == null) return;
        if (content.isEmpty()) return;

        PlayerInventory inventory = player.getInventory();

        for (Map.Entry<String, Integer> entry : content.getItems().entrySet()) {
            ItemStack item = this.stackFactory.create(entry.getKey(), entry.getValue());
            if (item == null) continue;

            inventory.addItem(item);
        }

        content.removeAll();
        player.updateInventory();
    }
}