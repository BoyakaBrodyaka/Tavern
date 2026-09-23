package me.boyakabrodyaka.cuisine.grill.content.label;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.ArmorStand;

@Getter
@RequiredArgsConstructor
public class GrillContentLabel {

    private final ArmorStand stand;

    public boolean isValid() {
        if (this.stand == null) return false;
        return !this.stand.isDead();
    }
}