package me.boyakabrodyaka.cuisine.kitchen.ingredient;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.kitchen.bridge.KitchenModBridge;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class KitchenIngredientStackFactory {

    private static final String MINECRAFT_PREFIX = "minecraft:";

    private final KitchenModBridge modBridge;

    public ItemStack create(String id, int amount) {
        if (id == null) return null;
        if (amount <= 0) return null;
        if (id.startsWith(MINECRAFT_PREFIX)) return createMinecraft(id, amount);

        return createMod(id, amount);
    }

    private ItemStack createMinecraft(String id, int amount) {
        String materialName = id.substring(MINECRAFT_PREFIX.length()).toUpperCase();
        Material material = Material.getMaterial(materialName);
        if (material == null) return null;

        return new ItemStack(material, amount);
    }

    private ItemStack createMod(String id, int amount) {
        if (this.modBridge == null) return null;
        if (!this.modBridge.isAvailable()) return null;

        return this.modBridge.createItem(id, amount);
    }
}