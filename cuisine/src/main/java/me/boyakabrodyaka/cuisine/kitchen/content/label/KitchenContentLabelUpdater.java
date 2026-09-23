package me.boyakabrodyaka.cuisine.kitchen.content.label;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContent;
import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContentFormatter;
import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContentHolder;
import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredient;
import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredientRegistry;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class KitchenContentLabelUpdater {

    private static final double LINE_HEIGHT = 0.25D;

    private final KitchenContentLabelSpawner spawner;
    private final KitchenContentFormatter formatter;
    private final KitchenIngredientRegistry ingredientRegistry;

    public void update(KitchenContentHolder holder, Location base, KitchenContent content) {
        removeLabels(holder);

        List<ArmorStand> labels = new ArrayList<>(content.getItems().size());

        int index = 0;

        for (Map.Entry<String, Integer> entry : content.getItems().entrySet()) {
            KitchenIngredient ingredient = this.ingredientRegistry.get(entry.getKey());

            String text = this.formatter.format(ingredient, entry.getValue());

            Location line = base.clone().add(0, LINE_HEIGHT * index, 0);
            ArmorStand stand = this.spawner.spawn(line, text);

            if (stand != null) labels.add(stand);
            index++;
        }

        holder.setLabels(labels);
    }

    private void removeLabels(KitchenContentHolder holder) {
        for (ArmorStand stand : holder.getLabels()) {
            if (stand == null) continue;
            if (stand.isDead()) continue;

            stand.remove();
        }

        holder.clear();
    }
}