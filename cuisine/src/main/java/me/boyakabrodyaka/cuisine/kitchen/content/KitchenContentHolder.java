package me.boyakabrodyaka.cuisine.kitchen.content;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class KitchenContentHolder {

    private List<ArmorStand> labels = new ArrayList<>();

    public void clear() {
        this.labels.clear();
    }
}