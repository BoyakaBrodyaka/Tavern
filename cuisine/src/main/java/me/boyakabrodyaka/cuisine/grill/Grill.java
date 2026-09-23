package me.boyakabrodyaka.cuisine.grill;

import lombok.Getter;
import me.boyakabrodyaka.cuisine.grill.content.GrillContent;
import me.boyakabrodyaka.cuisine.grill.content.GrillContentHolder;
import me.boyakabrodyaka.cuisine.grill.content.GrillContentRegistry;
import me.boyakabrodyaka.cuisine.grill.content.item.GrillContentItem;
import me.boyakabrodyaka.cuisine.grill.content.item.GrillContentItemRemover;
import me.boyakabrodyaka.cuisine.grill.content.item.GrillContentItemSpawner;
import me.boyakabrodyaka.cuisine.grill.content.label.GrillContentLabel;
import me.boyakabrodyaka.cuisine.grill.content.label.GrillContentLabelRemover;
import me.boyakabrodyaka.cuisine.grill.content.label.GrillContentLabelSpawner;
import me.boyakabrodyaka.cuisine.grill.content.progress.GrillContentProgress;
import me.boyakabrodyaka.cuisine.grill.content.state.GrillContentTiming;
import me.boyakabrodyaka.cuisine.grill.ingredient.GrillIngredientStackFactory;
import me.boyakabrodyaka.cuisine.grill.recipe.GrillRecipe;
import me.boyakabrodyaka.cuisine.grill.recipe.GrillRecipeMatcher;
import me.boyakabrodyaka.cuisine.grill.recipe.GrillRecipeRegistry;
import me.boyakabrodyaka.cuisine.grill.station.GrillStation;
import me.boyakabrodyaka.cuisine.grill.station.GrillStationCoordinate;
import me.boyakabrodyaka.cuisine.grill.task.GrillTask;
import me.boyakabrodyaka.cuisine.kitchen.bridge.KitchenModBridge;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Grill {

    private static final long RAW_DURATION_MS = 10_000L;
    private static final long COOKED_DURATION_MS = 10_000L;

    private static final double LABEL_OFFSET_Y = 0.6D;
    private static final double LABEL_CENTER_OFFSET = 0.5D;

    private static final int SPAWN_AMOUNT = 1;

    private final GrillRegistry registry;
    private final GrillContentRegistry contentRegistry;
    private final GrillContentItemSpawner itemSpawner;
    private final GrillContentItemRemover itemRemover;
    private final GrillContentLabelSpawner labelSpawner;
    private final GrillContentLabelRemover labelRemover;
    private final GrillIngredientStackFactory stackFactory;
    private final GrillRecipeMatcher recipeMatcher;
    private final GrillTask task;

    public Grill(JavaPlugin plugin, GrillRegistry registry, KitchenModBridge modBridge) {
        this.registry = registry;
        this.contentRegistry = new GrillContentRegistry();
        this.stackFactory = new GrillIngredientStackFactory(modBridge);
        this.itemSpawner = new GrillContentItemSpawner(this.stackFactory);
        this.itemRemover = new GrillContentItemRemover();
        this.labelSpawner = new GrillContentLabelSpawner();
        this.labelRemover = new GrillContentLabelRemover();

        GrillRecipeRegistry recipeRegistry = new GrillRecipeRegistry();
        this.recipeMatcher = new GrillRecipeMatcher(recipeRegistry);

        GrillContentTiming timing = new GrillContentTiming(RAW_DURATION_MS, COOKED_DURATION_MS);
        GrillContentProgress progress = new GrillContentProgress(timing);
        this.task = new GrillTask(plugin, this, this.contentRegistry, this.itemSpawner, timing, progress, this.labelSpawner, this.labelRemover);
        this.task.start();
    }

    public void add(Player player, GrillStation station, ItemStack item, String id) {
        GrillContent content = this.contentRegistry.get(station.getKey());
        if (!content.isEmpty()) return;

        GrillRecipe recipe = this.recipeMatcher.find(id);
        if (recipe == null) return;

        content.set(recipe.getRawId(), recipe.getCookedId(), recipe.getBurntId());

        consume(player, item);

        World world = player.getWorld();
        Location base = station.toLocation(world);
        base.add(0, GrillStationCoordinate.LABEL_OFFSET_Y.getValue(), 0);

        GrillContentHolder holder = this.contentRegistry.getHolder(station.getKey());

        GrillContentItem contentItem = this.itemSpawner.spawn(base, content.getCurrentId(), SPAWN_AMOUNT);
        holder.setContentItem(contentItem);

        Location labelLocation = base.clone().add(LABEL_CENTER_OFFSET, LABEL_OFFSET_Y, LABEL_CENTER_OFFSET);
        GrillContentLabel label = this.labelSpawner.spawn(labelLocation, "");
        holder.setLabel(label);
    }

    public void giveBack(Player player, GrillStation station) {
        GrillContent content = this.contentRegistry.get(station.getKey());
        if (content.isEmpty()) return;

        ItemStack item = this.stackFactory.create(content.getCurrentId(), SPAWN_AMOUNT);

        if (item != null) {
            player.getInventory().addItem(item);
            player.updateInventory();
        }

        content.removeAll();

        GrillContentHolder holder = this.contentRegistry.getHolder(station.getKey());

        if (holder.hasContentItem()) {
            this.itemRemover.remove(holder.getContentItem());
            holder.clearItem();
        }

        if (holder.hasLabel()) {
            this.labelRemover.remove(holder.getLabel());
            holder.clearLabel();
        }
    }

    public void clear() {
        for (GrillContentHolder holder : this.contentRegistry.getAllHolders()) {
            if (holder.hasContentItem()) {
                this.itemRemover.remove(holder.getContentItem());
            }
            if (holder.hasLabel()) {
                this.labelRemover.remove(holder.getLabel());
            }
        }

        this.contentRegistry.clear();

        if (this.task != null) this.task.cancel();
    }

    private void consume(Player player, ItemStack item) {
        PlayerInventory inventory = player.getInventory();

        int newAmount = item.getAmount() - 1;
        if (newAmount <= 0) {
            inventory.setItemInMainHand(null);
        } else {
            item.setAmount(newAmount);
        }

        player.updateInventory();
    }
}