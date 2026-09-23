package me.boyakabrodyaka.hud.menu.listener.menu;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.hud.menu.manager.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

@RequiredArgsConstructor
public class MenuWorldListener implements Listener {

    private final MenuManager menuManager;

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        this.menuManager.refreshMainMenu(player);
    }
}