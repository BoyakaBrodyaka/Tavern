package me.boyakabrodyaka.operation.order.dish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DishRegistry {

    private static final String WITCH_TYPE = "witch";
    private static final String GOBLIN_TYPE = "goblin";
    private static final String ORC_TYPE = "orc";

    private static final String WORM_STEW_ID = "tavernmod:worm_stew";
    private static final String TASTY_GOBLIN_WORM_ID = "tavernmod:tasty_goblin_worm";

    private final List<Dish> witchDishes;
    private final List<Dish> goblinDishes;
    private final List<Dish> orcDishes;

    public DishRegistry() {
        this.witchDishes = Arrays.asList(
                new Dish(WORM_STEW_ID),
                new Dish(TASTY_GOBLIN_WORM_ID)
        );

        this.goblinDishes = Arrays.asList(
                new Dish(WORM_STEW_ID),
                new Dish(TASTY_GOBLIN_WORM_ID)
        );

        this.orcDishes = Arrays.asList(
                new Dish(WORM_STEW_ID),
                new Dish(TASTY_GOBLIN_WORM_ID)
        );
    }

    public Dish getRandomFor(String typeKey) {
        List<Dish> pool = getPool(typeKey);
        if (pool.isEmpty()) return null;

        return pool.get(ThreadLocalRandom.current().nextInt(pool.size()));
    }

    public List<Dish> getPool(String typeKey) {
        if (typeKey == null) return this.witchDishes;
        if (typeKey.equalsIgnoreCase(GOBLIN_TYPE)) return this.goblinDishes;
        if (typeKey.equalsIgnoreCase(ORC_TYPE)) return this.orcDishes;

        return this.witchDishes;
    }
}