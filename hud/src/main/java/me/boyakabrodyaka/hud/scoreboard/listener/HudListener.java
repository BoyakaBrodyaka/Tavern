package me.boyakabrodyaka.hud.scoreboard.listener;

import me.boyakabrodyaka.hud.scoreboard.BoardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HudListener implements Listener {

    private final BoardManager boardManager;

    public HudListener(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        this.boardManager.update(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        this.boardManager.update(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.boardManager.remove(event.getPlayer());
    }
}