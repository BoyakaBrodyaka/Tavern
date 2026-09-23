package me.boyakabrodyaka.core.slot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
@RequiredArgsConstructor
public class SlotBlocker {

    private final String barrierName;

    public boolean isBarrier(ItemStack item) {
        if (item == null) return false;
        if (item.getType() != Material.BARRIER) return false;
        if (!item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return false;

        return meta.getDisplayName().equals(this.barrierName);
    }
}