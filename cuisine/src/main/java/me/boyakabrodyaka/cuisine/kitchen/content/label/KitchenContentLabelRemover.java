package me.boyakabrodyaka.cuisine.kitchen.content.label;

import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContentHolder;
import org.bukkit.entity.ArmorStand;

public class KitchenContentLabelRemover {

    public void remove(ArmorStand stand) {
        if (stand == null) return;
        if (stand.isDead()) return;
        stand.remove();
    }

    public void remove(KitchenContentHolder holder) {
        if (holder == null) return;
        for (ArmorStand stand : holder.getLabels()) remove(stand);
        holder.clear();
    }
}