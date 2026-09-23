package me.boyakabrodyaka.operation.order.label;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.order.dish.DishDisplayRegistry;

@RequiredArgsConstructor
public class OrderLabelFormatter {

    private static final String FORMAT = "§e%s §7x%d";

    private final DishDisplayRegistry displayRegistry;

    public String formatItem(String dishId, int amount) {
        return String.format(FORMAT, this.displayRegistry.get(dishId), amount);
    }
}