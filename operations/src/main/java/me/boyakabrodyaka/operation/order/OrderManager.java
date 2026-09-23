package me.boyakabrodyaka.operation.order;

import lombok.Getter;
import me.boyakabrodyaka.core.util.Color;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistrationManager;
import me.boyakabrodyaka.operation.npc.registration.departure.NpcDepartureManager;
import me.boyakabrodyaka.operation.order.dish.Dish;
import me.boyakabrodyaka.operation.order.dish.DishDisplayRegistry;
import me.boyakabrodyaka.operation.order.dish.DishRegistry;
import me.boyakabrodyaka.operation.order.label.OrderLabel;
import me.boyakabrodyaka.operation.order.label.OrderLabelFormatter;
import me.boyakabrodyaka.operation.order.label.OrderLabelRemover;
import me.boyakabrodyaka.operation.order.label.OrderLabelSpawner;
import me.boyakabrodyaka.operation.order.label.OrderLabelUpdater;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OrderManager {

    private static final int MAX_ATTEMPTS = 20;
    private static final int AMOUNT_ROLL_BOUND = 100;
    private static final int AMOUNT_ROLL_1 = 70;
    private static final int AMOUNT_ROLL_2 = 95;

    @Getter
    private final OrderRegistry registry;

    @Getter
    private final OrderLabelRemover labelRemover;

    private final OrderTypeResolver resolver;
    private final DishRegistry dishRegistry;
    private final OrderLabelUpdater labelUpdater;

    private NpcDepartureManager departureManager;
    private NpcRegistrationManager npcManager;

    public OrderManager(OrderRegistry registry, OrderTypeResolver resolver, DishRegistry dishRegistry) {
        this.registry = registry;
        this.resolver = resolver;
        this.dishRegistry = dishRegistry;

        OrderLabelSpawner labelSpawner = new OrderLabelSpawner();
        this.labelRemover = new OrderLabelRemover();
        DishDisplayRegistry displayRegistry = new DishDisplayRegistry();
        OrderLabelFormatter formatter = new OrderLabelFormatter(displayRegistry);
        this.labelUpdater = new OrderLabelUpdater(labelSpawner, this.labelRemover, formatter);
    }

    public void request(NpcRegistration npc, Player player) {
        if (this.registry.isCompleted(npc.getKey())) return;
        if (this.registry.has(npc.getKey())) return;

        OrderType type = this.resolver.resolve(npc);
        if (type == null) return;

        LinkedHashMap<String, Integer> items = createItems(type);
        if (items.isEmpty()) return;

        OrderRequest request = new OrderRequest(npc, items, System.currentTimeMillis());
        this.registry.register(npc.getKey(), request);
        this.registry.markCompleted(npc.getKey());

        player.sendMessage(Color.color("&e" + npc.getName() + " &eхочет сделать заказ"));
    }

    public void showLabel(NpcRegistration npc) {
        OrderRequest request = this.registry.get(npc.getKey());
        if (request == null) return;

        Location base = npc.getCurrent().clone();
        base.add(0, OrderCoordinate.LABEL_OFFSET_Y.getValue(), 0);

        OrderLabel label = new OrderLabel();
        this.labelUpdater.create(label, base, new LinkedHashMap<>(request.getItems()));

        this.registry.registerLabel(npc.getKey(), label);
        this.registry.markTaken(npc.getKey());
        this.registry.remove(npc.getKey());
    }

    public boolean hasRequest(NpcRegistration npc) {
        return this.registry.has(npc.getKey());
    }

    public boolean hasLabel(NpcRegistration npc) {
        return this.registry.getLabel(npc.getKey()) != null;
    }

    public boolean hasLabelItem(NpcRegistration npc, String dishName) {
        OrderLabel label = this.registry.getLabel(npc.getKey());
        if (label == null) return false;

        return label.getItemStand(dishName) != null;
    }

    public boolean isTaken(NpcRegistration npc) {
        return this.registry.isTaken(npc.getKey());
    }

    public boolean isExpired(NpcRegistration npc) {
        OrderRequest request = this.registry.get(npc.getKey());
        if (request == null) return false;

        return request.isExpired();
    }

    public boolean isLabelExpired(NpcRegistration npc) {
        return this.registry.isLabelExpired(npc.getKey());
    }

    public boolean deliver(NpcRegistration npc, Player player, String dishName) {
        OrderLabel label = this.registry.getLabel(npc.getKey());
        if (label == null) return false;

        ArmorStand stand = label.getItemStand(dishName);
        if (stand == null) return false;

        int amount = parseAmount(stand);
        if (amount <= 0) return false;

        if (amount <= 1) this.labelUpdater.removeItem(label, dishName);
        else this.labelUpdater.updateItem(label, npc.getCurrent().clone(), dishName, amount - 1);

        if (!label.isEmpty()) return true;

        completeOrder(npc);
        player.sendMessage(Color.color("&aЗаказ выполнен!"));

        return true;
    }

    public void cancel(NpcRegistration npc) {
        OrderLabel label = this.registry.getLabel(npc.getKey());

        if (label != null) {
            this.labelRemover.remove(label);
            this.registry.removeLabel(npc.getKey());
        }

        this.registry.remove(npc.getKey());
        this.registry.markCompleted(npc.getKey());

        finishOrder(npc);
    }

    public void remove(NpcRegistration npc) {
        this.registry.remove(npc.getKey());
    }

    public void clear() {
        for (OrderLabel label : this.registry.getAllLabels()) this.labelRemover.remove(label);

        this.registry.clear();
    }

    public void setDepartureManager(NpcDepartureManager departureManager) {
        this.departureManager = departureManager;
    }

    public void setNpcManager(NpcRegistrationManager npcManager) {
        this.npcManager = npcManager;
    }

    private LinkedHashMap<String, Integer> createItems(OrderType type) {
        LinkedHashMap<String, Integer> items = new LinkedHashMap<>();

        int minItems = OrderCoordinate.MIN_ITEMS.getIntValue();
        int maxItems = OrderCoordinate.MAX_ITEMS.getIntValue();
        int count = minItems + ThreadLocalRandom.current().nextInt(maxItems - minItems + 1);

        List<Dish> pool = this.dishRegistry.getPool(type.getKey());
        if (pool.isEmpty()) return items;

        int attempts = 0;

        while (items.size() < count && attempts < MAX_ATTEMPTS) {
            attempts++;

            Dish dish = pool.get(ThreadLocalRandom.current().nextInt(pool.size()));
            if (items.containsKey(dish.getId())) continue;

            items.put(dish.getId(), randomAmount());
        }

        return items;
    }

    private int randomAmount() {
        int roll = ThreadLocalRandom.current().nextInt(AMOUNT_ROLL_BOUND);

        if (roll < AMOUNT_ROLL_1) return 1;
        if (roll < AMOUNT_ROLL_2) return 2;
        return 3;
    }

    private void completeOrder(NpcRegistration npc) {
        this.labelRemover.remove(this.registry.getLabel(npc.getKey()));
        this.registry.removeLabel(npc.getKey());
        this.registry.remove(npc.getKey());
        this.registry.markCompleted(npc.getKey());

        finishOrder(npc);
    }

    private void finishOrder(NpcRegistration npc) {
        if (npc.isSitting() && npc.getSitting() != null) {
            npc.getSitting().clearOccupant();
        }

        npc.stopSitting();
        npc.stopFollowing();
        npc.clearTargetChair();

        startDeparture(npc);
    }

    private void startDeparture(NpcRegistration npc) {
        if (this.departureManager == null) return;

        this.departureManager.stopTracking(npc);

        Location departure = this.departureManager.getDepartureLocation(npc);
        if (departure == null) return;

        npc.setDepartureLocation(departure);
        npc.setDeparting(true);

        if (this.npcManager != null) this.npcManager.reorder(npc.getWorld());
    }

    private int parseAmount(ArmorStand stand) {
        String name = stand.getCustomName();
        if (name == null) return 0;

        int index = name.lastIndexOf('x');
        if (index == -1) return 0;

        try {
            return Integer.parseInt(name.substring(index + 1).trim());
        } catch (NumberFormatException exception) {
            return 0;
        }
    }
}