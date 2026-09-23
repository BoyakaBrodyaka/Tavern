package me.boyakabrodyaka.cuisine.grill.listener;

import me.boyakabrodyaka.cuisine.grill.Grill;
import me.boyakabrodyaka.cuisine.grill.GrillRegistry;
import me.boyakabrodyaka.cuisine.grill.ingredient.GrillIngredient;
import me.boyakabrodyaka.cuisine.grill.ingredient.GrillIngredientMatcher;
import me.boyakabrodyaka.cuisine.grill.ingredient.GrillIngredientRegistry;
import me.boyakabrodyaka.cuisine.grill.ingredient.GrillIngredientResolver;
import me.boyakabrodyaka.cuisine.grill.station.GrillStation;
import me.boyakabrodyaka.cuisine.grill.station.GrillStationBlockChecker;
import me.boyakabrodyaka.cuisine.grill.station.GrillStationFactory;
import me.boyakabrodyaka.cuisine.grill.station.GrillStationMatcher;
import me.boyakabrodyaka.cuisine.grill.station.GrillStationSpawner;
import me.boyakabrodyaka.cuisine.kitchen.bridge.KitchenModBridge;
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

public class GrillListener implements Listener {

    private static final String WORLD_SUFFIX = "_map";
    private static final String GRILL_KEY_SUFFIX = "_grill";

    private final GrillRegistry registry;
    private final GrillStationSpawner spawner;
    private final GrillStationMatcher matcher;
    private final GrillStationBlockChecker blockChecker;
    private final Grill grill;
    private final GrillIngredientRegistry ingredientRegistry;
    private final GrillIngredientMatcher ingredientMatcher;

    public GrillListener(JavaPlugin plugin, KitchenModBridge modBridge) {
        this.registry = new GrillRegistry();
        this.spawner = new GrillStationSpawner(this.registry, new GrillStationFactory());
        this.matcher = new GrillStationMatcher(this.registry);
        this.blockChecker = new GrillStationBlockChecker();

        this.ingredientRegistry = new GrillIngredientRegistry();
        GrillIngredientResolver ingredientResolver = new GrillIngredientResolver();
        this.ingredientMatcher = new GrillIngredientMatcher(this.ingredientRegistry, ingredientResolver);

        this.grill = new Grill(plugin, this.registry, modBridge);

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

        if (!this.blockChecker.isGrillBlock(block.getLocation())) return;

        GrillStation station = this.matcher.match(block.getLocation());
        if (station == null) return;

        event.setCancelled(true);

        if (player.isSneaking()) {
            this.grill.giveBack(player, station);
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR) return;

        GrillIngredient ingredient = this.ingredientMatcher.match(item);
        if (ingredient == null) return;

        this.grill.add(player, station, item, ingredient.getId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        World world = event.getPlayer().getWorld();
        if (world == null) return;
        if (!world.getName().contains(WORLD_SUFFIX)) return;

        if (this.registry.has(world.getName() + GRILL_KEY_SUFFIX)) return;

        this.spawner.spawn(world);
    }

    public void clear() {
        this.grill.clear();
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