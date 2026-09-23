package me.boyakabrodyaka.operation.order.label;

import org.bukkit.entity.ArmorStand;

public class OrderLabelRemover {

    public void remove(ArmorStand stand) {
        if (stand == null) return;
        if (stand.isDead()) return;
        stand.remove();
    }

    public void remove(OrderLabel label) {
        if (label == null) return;
        for (ArmorStand stand : label.getItemStands().values()) remove(stand);
        label.getItemStands().clear();
    }
}