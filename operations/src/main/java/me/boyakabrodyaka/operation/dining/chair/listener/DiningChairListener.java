package me.boyakabrodyaka.operation.dining.chair.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.dining.DiningManager;
import me.boyakabrodyaka.operation.dining.chair.DiningChair;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistrationManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@RequiredArgsConstructor
public class DiningChairListener implements Listener {

    private static final double CHAIR_SEARCH_RADIUS = 3.0D;

    private final DiningManager diningManager;
    private final NpcRegistrationManager npcManager;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.RIGHT_CLICK_AIR) return;

        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world == null) return;

        Location searchLocation = resolveSearchLocation(event, player);

        DiningChair chair = this.diningManager.findNearby(searchLocation, CHAIR_SEARCH_RADIUS);
        if (chair == null) return;
        if (chair.isOccupied()) return;

        NpcRegistration follower = this.npcManager.findFollower(player);
        if (follower == null) return;

        event.setCancelled(true);

        this.npcManager.sitOnChair(
                follower,
                chair,
                this.diningManager.getCenterX(),
                this.diningManager.getCenterY(),
                this.diningManager.getCenterZ()
        );
    }

    private Location resolveSearchLocation(PlayerInteractEvent event, Player player) {
        Block block = event.getClickedBlock();
        if (block == null) return player.getLocation();

        return block.getLocation();
    }
}