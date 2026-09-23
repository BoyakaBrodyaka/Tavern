package me.boyakabrodyaka.operation.npc.registration.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistrationManager;
import me.boyakabrodyaka.operation.registration.RegistrationManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

@RequiredArgsConstructor
public class NpcRegistrationInteractListener implements Listener {

    private static final double LOOK_THRESHOLD = 0.7D;
    private static final double NPC_EYE_HEIGHT = 1.6D;

    private final NpcRegistrationManager npcManager;
    private final RegistrationManager registrationManager;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world == null) return;

        if (!this.registrationManager.isPlayerInZone(player)) return;

        NpcRegistration npc = this.npcManager.findFirstInZone(player);
        if (npc == null) return;
        if (!isLookingAt(player, npc)) return;

        event.setCancelled(true);
        this.npcManager.follow(npc, player);
    }

    private boolean isLookingAt(Player player, NpcRegistration npc) {
        Location eye = player.getEyeLocation();
        Vector direction = eye.getDirection().normalize();

        Location npcLocation = npc.getCurrent().clone();
        npcLocation.add(0, NPC_EYE_HEIGHT, 0);

        Vector toNpc = npcLocation.toVector().subtract(eye.toVector()).normalize();

        return direction.dot(toNpc) > LOOK_THRESHOLD;
    }
}