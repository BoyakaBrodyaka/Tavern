package me.boyakabrodyaka.core.slot;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SlotManager {

    private static final List<Integer> ALLOWED_SLOTS = Collections.unmodifiableList(Arrays.asList(3, 4, 5));

    private static final int HOTBAR_MIN = 0;
    private static final int HOTBAR_MAX = 8;

    private static final int INVENTORY_MIN = 9;
    private static final int INVENTORY_MAX = 35;

    private static final int BARRIER_AMOUNT = 1;
    private static final String BARRIER_NAME = "§c§lНедоступно";
    private static final String BARRIER_LORE = "§7Этот слот недоступен";

    private final SlotBlocker blocker;

    public SlotManager() { this.blocker = new SlotBlocker(BARRIER_NAME); }

    public void applyLockedAll(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack barrier = createBarrier();

        for (int slot = HOTBAR_MIN; slot <= HOTBAR_MAX; slot++) {
            if (isAllowed(slot)) continue;
            inventory.setItem(slot, barrier.clone());
        }
        for (int slot = INVENTORY_MIN; slot <= INVENTORY_MAX; slot++) inventory.setItem(slot, barrier.clone());

        player.updateInventory();
    }

    public void clearLocked(Player player) {
        PlayerInventory inventory = player.getInventory();

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (!this.blocker.isBarrier(item)) continue;

            inventory.setItem(slot, null);
        }

        player.updateInventory();
    }

    public boolean isAllowed(int slot) { return ALLOWED_SLOTS.contains(slot); }

    public boolean isHotbar(int slot) { return slot >= HOTBAR_MIN && slot <= HOTBAR_MAX; }

    public boolean isInventory(int slot) { return slot >= INVENTORY_MIN && slot <= INVENTORY_MAX; }

    public boolean isBarrier(ItemStack item) { return this.blocker.isBarrier(item);}

    private ItemStack createBarrier() {
        ItemStack item = new ItemStack(Material.BARRIER, BARRIER_AMOUNT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(BARRIER_NAME);
        meta.setLore(Collections.singletonList(BARRIER_LORE));
        item.setItemMeta(meta);
        return item;
    }
}