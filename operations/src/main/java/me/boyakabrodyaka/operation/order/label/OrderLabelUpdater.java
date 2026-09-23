package me.boyakabrodyaka.operation.order.label;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.Map;

@RequiredArgsConstructor
public class OrderLabelUpdater {

    private static final double LINE_HEIGHT = 0.25D;

    private final OrderLabelSpawner spawner;
    private final OrderLabelRemover remover;
    private final OrderLabelFormatter formatter;

    public void create(OrderLabel label, Location base, Map<String, Integer> items) {
        this.remover.remove(label);

        int index = 1;

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            Location line = base.clone().add(0, LINE_HEIGHT * index, 0);
            ArmorStand stand = this.spawner.spawn(line, this.formatter.formatItem(entry.getKey(), entry.getValue()));

            if (stand != null) label.putItemStand(entry.getKey(), stand);

            index++;
        }
    }

    public void updateItem(OrderLabel label, Location base, String dishName, int amount) {
        ArmorStand stand = label.getItemStand(dishName);
        if (stand == null) return;

        stand.setCustomName(this.formatter.formatItem(dishName, amount));
    }

    public void removeItem(OrderLabel label, String dishName) {
        ArmorStand stand = label.getItemStand(dishName);
        if (stand == null) return;

        this.remover.remove(stand);
        label.removeItemStand(dishName);
    }
}