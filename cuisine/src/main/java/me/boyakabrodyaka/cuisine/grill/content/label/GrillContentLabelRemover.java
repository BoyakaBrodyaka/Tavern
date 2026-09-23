package me.boyakabrodyaka.cuisine.grill.content.label;

import org.bukkit.entity.ArmorStand;

public class GrillContentLabelRemover {

    public void remove(GrillContentLabel label) {
        if (label == null) return;
        if (!label.isValid()) return;

        ArmorStand stand = label.getStand();
        stand.remove();
    }
}