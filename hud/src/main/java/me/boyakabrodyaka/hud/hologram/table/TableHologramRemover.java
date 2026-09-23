package me.boyakabrodyaka.hud.hologram.table;

import org.bukkit.entity.ArmorStand;

public class TableHologramRemover {

    public void remove(ArmorStand stand) {
        if (stand == null) return;
        if (stand.isDead()) return;

        stand.remove();
    }

    public void remove(TableHologramHolder holder) {
        if (holder == null) return;
        for (ArmorStand stand : holder.getLines()) remove(stand);
        holder.clear();
    }
}