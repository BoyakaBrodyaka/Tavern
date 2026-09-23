package me.boyakabrodyaka.hud.menu.game.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.hud.menu.game.GameMenuManager;
import me.boyakabrodyaka.hud.menu.game.button.GameButton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class GameMenuListener implements Listener {

    private static final String MENU_TITLE = "§8Меню";
    private static final String CONFIRM_TITLE = "§8Подтверждение выхода";

    private final GameMenuManager gameMenuManager;

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getView() == null) return;

        String title = event.getView().getTitle();
        Player player = (Player) event.getWhoClicked();

        if (title.equals(MENU_TITLE)) {
            handle(event, player, this.gameMenuManager.getButton(event.getSlot()));
            return;
        }

        if (title.equals(CONFIRM_TITLE)) handle(event, player, this.gameMenuManager.getConfirmButton(event.getSlot()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) { this.gameMenuManager.remove(event.getPlayer()); }

    private void handle(InventoryClickEvent event, Player player, GameButton button) {
        event.setCancelled(true);
        if (button == null) return;

        button.onClick(player);
    }
}