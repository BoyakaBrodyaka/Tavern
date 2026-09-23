package me.boyakabrodyaka.cuisine.kitchen;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.kitchen.bridge.KitchenModBridge;
import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContent;
import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContentFormatter;
import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContentGiver;
import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContentHolder;
import me.boyakabrodyaka.cuisine.kitchen.content.KitchenContentRegistry;
import me.boyakabrodyaka.cuisine.kitchen.content.label.KitchenContentLabelRemover;
import me.boyakabrodyaka.cuisine.kitchen.content.label.KitchenContentLabelSpawner;
import me.boyakabrodyaka.cuisine.kitchen.content.label.KitchenContentLabelUpdater;
import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredientRegistry;
import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredientStackFactory;
import me.boyakabrodyaka.cuisine.kitchen.recipe.KitchenRecipe;
import me.boyakabrodyaka.cuisine.kitchen.recipe.KitchenRecipeChecker;
import me.boyakabrodyaka.cuisine.kitchen.recipe.KitchenRecipeMatcher;
import me.boyakabrodyaka.cuisine.kitchen.recipe.KitchenRecipeRegistry;
import me.boyakabrodyaka.cuisine.kitchen.recipe.KitchenRecipeResultGiver;
import me.boyakabrodyaka.cuisine.kitchen.station.KitchenStation;
import me.boyakabrodyaka.cuisine.kitchen.station.KitchenStationCoordinate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Getter
public class Kitchen {

    private static final int INGREDIENT_AMOUNT = 1;

    private final KitchenRegistry registry;
    private final KitchenContentRegistry contentRegistry;
    private final KitchenContentGiver contentGiver;
    private final KitchenContentLabelRemover labelRemover;
    private final KitchenContentLabelUpdater labelUpdater;
    private final KitchenRecipeRegistry recipeRegistry;
    private final KitchenRecipeMatcher recipeMatcher;
    private final KitchenRecipeResultGiver recipeGiver;

    public Kitchen(KitchenRegistry registry, KitchenIngredientRegistry ingredientRegistry, KitchenModBridge modBridge) {
        this.registry = registry;
        this.contentRegistry = new KitchenContentRegistry();
        this.labelRemover = new KitchenContentLabelRemover();

        KitchenIngredientStackFactory stackFactory = new KitchenIngredientStackFactory(modBridge);
        this.contentGiver = new KitchenContentGiver(stackFactory);

        KitchenContentLabelSpawner labelSpawner = new KitchenContentLabelSpawner();
        KitchenContentFormatter formatter = new KitchenContentFormatter();
        this.labelUpdater = new KitchenContentLabelUpdater(labelSpawner, formatter, ingredientRegistry);

        this.recipeRegistry = new KitchenRecipeRegistry();
        KitchenRecipeChecker recipeChecker = new KitchenRecipeChecker();
        this.recipeMatcher = new KitchenRecipeMatcher(this.recipeRegistry, recipeChecker);
        this.recipeGiver = new KitchenRecipeResultGiver(stackFactory);
    }

    public void add(Player player, KitchenStation station, ItemStack item, String id) {
        KitchenContent content = this.contentRegistry.get(station.getKey());
        content.add(id, INGREDIENT_AMOUNT);

        consume(player, item);
        updateLabels(player, station, content);

        KitchenRecipe recipe = this.recipeMatcher.find(content);
        if (recipe == null) return;

        this.recipeGiver.give(player, recipe);
        content.removeAll();

        KitchenContentHolder holder = this.contentRegistry.getHolder(station.getKey());
        this.labelRemover.remove(holder);
    }

    public void giveBack(Player player, KitchenStation station) {
        KitchenContent content = this.contentRegistry.get(station.getKey());
        if (content.isEmpty()) return;

        this.contentGiver.giveAll(player, content);

        KitchenContentHolder holder = this.contentRegistry.getHolder(station.getKey());
        this.labelRemover.remove(holder);
    }

    public void clear() {
        for (KitchenContentHolder holder : this.contentRegistry.getAllHolders()) this.labelRemover.remove(holder);

        this.contentRegistry.clear();
        this.recipeRegistry.clear();
    }

    private void consume(Player player, ItemStack item) {
        PlayerInventory inventory = player.getInventory();

        int newAmount = item.getAmount() - 1;
        if (newAmount <= 0) inventory.setItemInMainHand(null);
        else item.setAmount(newAmount);

        player.updateInventory();
    }

    private void updateLabels(Player player, KitchenStation station, KitchenContent content) {
        World world = player.getWorld();
        if (world == null) return;

        Location base = station.toLocation(world);
        base.add(0, KitchenStationCoordinate.LABEL_OFFSET_Y.getValue(), 0);
        base.add(
                KitchenStationCoordinate.LABEL_CENTER_OFFSET.getValue(),
                0,
                KitchenStationCoordinate.LABEL_CENTER_OFFSET.getValue()
        );

        KitchenContentHolder holder = this.contentRegistry.getHolder(station.getKey());
        this.labelUpdater.update(holder, base, content);
    }
}