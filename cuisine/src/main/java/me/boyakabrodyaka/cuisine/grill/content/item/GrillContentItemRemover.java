package me.boyakabrodyaka.cuisine.grill.content.item;

import org.bukkit.entity.Item;

public class GrillContentItemRemover {

    public void remove(GrillContentItem contentItem) {
        if (contentItem == null) return;

        Item entity = contentItem.getEntity();
        if (entity == null) return;
        if (entity.isDead()) return;

        entity.remove();
    }
}