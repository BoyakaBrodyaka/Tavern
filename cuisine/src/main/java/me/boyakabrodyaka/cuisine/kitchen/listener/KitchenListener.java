package me.boyakabrodyaka.cuisine.kitchen.listener;

import me.boyakabrodyaka.cuisine.kitchen.Kitchen;
import me.boyakabrodyaka.cuisine.kitchen.KitchenRegistry;
import me.boyakabrodyaka.cuisine.kitchen.bridge.KitchenModBridge;
import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredient;
import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredientMatcher;
import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredientRegistry;
import me.boyakabrodyaka.cuisine.kitchen.ingredient.KitchenIngredientResolver;
import me.boyakabrodyaka.cuisine.kitchen.station.KitchenStation;
import me.boyakabrodyaka.cuisine.kitchen.station.KitchenStationBlockChecker;
import me.boyakabrodyaka.cuisine.kitchen.station.KitchenStationFactory;
import me.boyakabrodyaka.cuisine.kitchen.station.KitchenStationMatcher;
import me.boyakabrodyaka.cuisine.kitchen.station.KitchenStationSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class KitchenListener implements Listener {

    private static final String WORLD_SUFFIX = "_map";
    private static final String KITCHEN_KEY_SUFFIX = "_kitchen";

    private final KitchenRegistry registry;
    private final KitchenStationSpawner spawner;
    private final KitchenStationMatcher matcher;
    private final KitchenStationBlockChecker blockChecker;
    private final Kitchen kitchen;
    private final KitchenIngredientRegistry ingredientRegistry;
    private final KitchenIngredientMatcher ingredientMatcher;

    public KitchenListener(JavaPlugin plugin) {
        KitchenModBridge modBridge = new KitchenModBridge();

        this.registry = new KitchenRegistry();
        this.spawner = new KitchenStationSpawner(this.registry, new KitchenStationFactory());
        this.matcher = new KitchenStationMatcher(this.registry);
        this.blockChecker = new KitchenStationBlockChecker();

        this.ingredientRegistry = new KitchenIngredientRegistry();
        KitchenIngredientResolver ingredientResolver = new KitchenIngredientResolver(modBridge);
        this.ingredientMatcher = new KitchenIngredientMatcher(this.ingredientRegistry, ingredientResolver);

        this.kitchen = new Kitchen(this.registry, this.ingredientRegistry, modBridge);

        spawnForAllWorlds();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world == null) return;

        if (!this.blockChecker.isStationBlock(block.getLocation())) return;

        KitchenStation station = this.matcher.match(block.getLocation());
        if (station == null) return;

        event.setCancelled(true);

        if (player.isSneaking()) {
            this.kitchen.giveBack(player, station);
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR) return;

        KitchenIngredient ingredient = this.ingredientMatcher.match(item);
        if (ingredient == null) return;

        this.kitchen.add(player, station, item, ingredient.getId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        World world = event.getPlayer().getWorld();
        if (world == null) return;
        if (!world.getName().contains(WORLD_SUFFIX)) return;

        if (this.registry.has(world.getName() + KITCHEN_KEY_SUFFIX)) return;

        this.spawner.spawn(world);
    }

    public void clear() {
        this.kitchen.clear();
        this.registry.clear();
        this.ingredientRegistry.clear();
    }

    private void spawnForAllWorlds() {
        for (World world : Bukkit.getWorlds()) {
            if (!world.getName().contains(WORLD_SUFFIX)) continue;

            this.spawner.spawn(world);
        }
    }
}