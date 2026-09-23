package me.boyakabrodyaka.cuisine.kitchen.bridge;

import me.boyakabrodyaka.tvrn.api.ApiBridge;
import net.minecraft.server.v1_12_R1.ItemStack;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

public class KitchenModBridge {

    private final boolean available;

    public KitchenModBridge() {
        this.available = checkAvailable();
    }

    private boolean checkAvailable() {
        try {
            return ApiBridge.isAvailable();
        } catch (Throwable throwable) {
            return false;
        }
    }

    public boolean isAvailable() {
        return this.available;
    }

    public org.bukkit.inventory.ItemStack createItem(String id, int amount) {
        if (!this.available) return null;
        if (amount <= 0) return null;

        try {
            Object forgeStack = ApiBridge.forgeById(id);
            if (forgeStack == null) return null;
            if (!(forgeStack instanceof ItemStack)) return null;

            ItemStack nmsStack = (ItemStack) forgeStack;

            org.bukkit.inventory.ItemStack bukkitStack = CraftItemStack.asBukkitCopy(nmsStack);
            if (bukkitStack == null) return null;

            bukkitStack.setAmount(amount);
            return bukkitStack;
        } catch (Throwable throwable) {
            return null;
        }
    }
}