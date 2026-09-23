package me.boyakabrodyaka.operation.npc.registration.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistrationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

@RequiredArgsConstructor
public class NpcRegistrationWorldListener implements Listener {

    private static final String WORLD_SUFFIX = "_map";

    private final NpcRegistrationManager manager;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String fromWorld = event.getFrom().getName();

        if (!fromWorld.contains(WORLD_SUFFIX)) return;

        this.manager.despawnFor(player, fromWorld);
    }
}