package me.boyakabrodyaka.operation;

import me.boyakabrodyaka.operation.dining.DiningManager;
import me.boyakabrodyaka.operation.dining.chair.listener.DiningChairListener;
import me.boyakabrodyaka.operation.dining.listener.DiningListener;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistrationManager;
import me.boyakabrodyaka.operation.npc.registration.departure.NpcDepartureManager;
import me.boyakabrodyaka.operation.npc.registration.departure.NpcDepartureRegistry;
import me.boyakabrodyaka.operation.npc.registration.departure.NpcDepartureTimer;
import me.boyakabrodyaka.operation.npc.registration.departure.task.NpcDepartureTask;
import me.boyakabrodyaka.operation.npc.registration.listener.NpcRegistrationInteractListener;
import me.boyakabrodyaka.operation.npc.registration.listener.NpcRegistrationWorldListener;
import me.boyakabrodyaka.operation.npc.registration.name.NpcNameRegistry;
import me.boyakabrodyaka.operation.npc.registration.skin.NpcSkinRegistry;
import me.boyakabrodyaka.operation.npc.registration.task.NpcRegistrationTask;
import me.boyakabrodyaka.operation.npc.registration.type.NpcTypeRegistry;
import me.boyakabrodyaka.operation.order.OrderManager;
import me.boyakabrodyaka.operation.order.OrderRegistry;
import me.boyakabrodyaka.operation.order.OrderTypeResolver;
import me.boyakabrodyaka.operation.order.dish.DishRegistry;
import me.boyakabrodyaka.operation.order.listener.OrderListener;
import me.boyakabrodyaka.operation.order.task.OrderTask;
import me.boyakabrodyaka.operation.registration.RegistrationManager;
import me.boyakabrodyaka.operation.registration.listener.RegistrationListener;
import me.boyakabrodyaka.operation.registration.task.RegistrationTask;
import org.bukkit.plugin.java.JavaPlugin;

public class Operation extends JavaPlugin {

    private static final String COMMERCE_PLUGIN_NAME = "TavernCommerceBP";

    private NpcRegistrationManager npcManager;
    private NpcRegistrationTask npcTask;
    private RegistrationManager registrationManager;
    private RegistrationTask registrationTask;
    private DiningManager diningManager;
    private NpcDepartureManager departureManager;
    private NpcDepartureTask departureTask;
    private OrderManager orderManager;
    private OrderTask orderTask;

    @Override
    public void onEnable() {
        initializeManagers();
        registerListeners();
        startTasks();
    }

    @Override
    public void onDisable() {
        cancelTasks();
        clearManagers();
    }

    private void initializeManagers() {
        NpcNameRegistry nameRegistry = new NpcNameRegistry();
        NpcSkinRegistry skinRegistry = new NpcSkinRegistry();
        NpcTypeRegistry typeRegistry = new NpcTypeRegistry();

        NpcDepartureRegistry departureRegistry = new NpcDepartureRegistry();
        NpcDepartureTimer departureTimer = new NpcDepartureTimer(departureRegistry);
        this.departureManager = new NpcDepartureManager(departureTimer);

        this.npcManager = new NpcRegistrationManager(this, typeRegistry, nameRegistry, skinRegistry, this.departureManager);
        this.npcTask = new NpcRegistrationTask(this.npcManager, COMMERCE_PLUGIN_NAME);
        this.departureTask = new NpcDepartureTask(this.npcManager, this.departureManager);

        this.registrationManager = new RegistrationManager();
        this.registrationTask = new RegistrationTask(this.registrationManager);

        this.diningManager = new DiningManager();

        OrderRegistry orderRegistry = new OrderRegistry();
        OrderTypeResolver orderResolver = new OrderTypeResolver();
        DishRegistry dishRegistry = new DishRegistry();
        this.orderManager = new OrderManager(orderRegistry, orderResolver, dishRegistry);
        this.orderManager.setDepartureManager(this.departureManager);
        this.orderManager.setNpcManager(this.npcManager);
        this.orderTask = new OrderTask(this.npcManager, this.orderManager);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new NpcRegistrationInteractListener(this.npcManager, this.registrationManager), this);
        getServer().getPluginManager().registerEvents(new NpcRegistrationWorldListener(this.npcManager), this);
        getServer().getPluginManager().registerEvents(new RegistrationListener(this.registrationManager), this);
        getServer().getPluginManager().registerEvents(new DiningListener(this.diningManager), this);
        getServer().getPluginManager().registerEvents(new DiningChairListener(this.diningManager, this.npcManager), this);
        getServer().getPluginManager().registerEvents(new OrderListener(this.npcManager, this.orderManager), this);
    }

    private void startTasks() {
        this.npcTask.start(this);
        this.registrationTask.start(this);
        this.departureTask.start(this);
        this.orderTask.start(this);
    }

    private void cancelTasks() {
        if (this.npcTask != null) this.npcTask.cancel();
        if (this.registrationTask != null) this.registrationTask.cancel();
        if (this.departureTask != null) this.departureTask.cancel();
        if (this.orderTask != null) this.orderTask.cancel();
    }

    private void clearManagers() {
        if (this.npcManager != null) this.npcManager.clear();
        if (this.registrationManager != null) this.registrationManager.clear();
        if (this.diningManager != null) this.diningManager.clear();
        if (this.departureManager != null) this.departureManager.clear();
        if (this.orderManager != null) this.orderManager.clear();
    }
}