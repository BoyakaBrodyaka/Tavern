package me.boyakabrodyaka.operation.order.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistrationManager;
import me.boyakabrodyaka.operation.order.OrderManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

@RequiredArgsConstructor
public class OrderListener implements Listener {

    private static final String DISH_PREFIX = "tavernmod_";
    private static final String DISH_NAMESPACE = "tavernmod:";

    private static final double SEARCH_RADIUS = 5.0D;
    private static final double LOOK_THRESHOLD = 0.7D;
    private static final double NPC_EYE_HEIGHT = 1.6D;

    private final NpcRegistrationManager npcManager;
    private final OrderManager orderManager;

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world == null) return;

        NpcRegistration npc = this.npcManager.findNearby(player, SEARCH_RADIUS);
        if (npc == null) return;
        if (!npc.isSitting()) return;
        if (!isLookingAt(player, npc)) return;

        event.setCancelled(true);

        ItemStack item = event.getItem();

        if (item == null || item.getType() == Material.AIR) {
            if (this.orderManager.hasRequest(npc)) {
                this.orderManager.showLabel(npc);
            }
            return;
        }

        String dishId = resolveDishId(item);
        if (dishId == null) return;

        if (!this.orderManager.deliver(npc, player, dishId)) return;

        consume(player, item);
    }

    private String resolveDishId(ItemStack item) {
        String materialName = item.getType().name().toLowerCase();
        if (!materialName.startsWith(DISH_PREFIX)) return null;

        return DISH_NAMESPACE + materialName.substring(DISH_PREFIX.length());
    }

    private boolean isLookingAt(Player player, NpcRegistration npc) {
        Location eye = player.getEyeLocation();
        Vector direction = eye.getDirection().normalize();

        Location npcLocation = npc.getCurrent().clone();
        npcLocation.add(0, NPC_EYE_HEIGHT, 0);

        Vector toNpc = npcLocation.toVector().subtract(eye.toVector()).normalize();

        return direction.dot(toNpc) > LOOK_THRESHOLD;
    }

    private void consume(Player player, ItemStack item) {
        PlayerInventory inventory = player.getInventory();

        int newAmount = item.getAmount() - 1;
        if (newAmount <= 0) inventory.setItemInMainHand(null);
        else item.setAmount(newAmount);

        player.updateInventory();
    }
}