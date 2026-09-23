package me.boyakabrodyaka.cuisine.grill.content.label;

import org.bukkit.Location;

public class GrillContentLabelUpdater {

    public void update(GrillContentLabel label, String text) {
        if (label == null) return;
        if (!label.isValid()) return;

        label.getStand().setCustomName(text);
    }

    public void move(GrillContentLabel label, Location location) {
        if (label == null) return;
        if (!label.isValid()) return;

        label.getStand().teleport(location);
    }
}