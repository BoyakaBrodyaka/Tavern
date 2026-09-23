package me.boyakabrodyaka.hud.menu.listener.menu;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.hud.menu.manager.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class ConfirmMenuListener implements Listener {

    private static final String TITLE = "§8Подтверждение удаления";
    private static final String CONFIRM_NAME = "§aПодтвердить";
    private static final String CANCEL_NAME = "§cОтменить";
    private static final String PLUGIN_NAME = "TavernHUDBP";

    private static final String DELETE_MESSAGE = "§cСохранение №%d удалено!";
    private static final long REOPEN_DELAY = 1L;

    private final MenuManager menuManager;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getView() == null) return;
        if (!event.getView().getTitle().equals(TITLE)) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String name = resolveDisplayName(event.getCurrentItem());
        if (name == null) return;

        if (name.equals(CONFIRM_NAME)) {
            handleConfirm(player);
            return;
        }

        if (name.equals(CANCEL_NAME)) handleCancel(player);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        if (event.getView() == null) return;
        if (!event.getView().getTitle().equals(TITLE)) return;

        Player player = (Player) event.getPlayer();
        this.menuManager.clearPending(player.getName());
    }

    private void handleConfirm(Player player) {
        int number = this.menuManager.getPendingNumber(player.getName());

        if (number != 0) {
            this.menuManager.deleteSave(player, number);
            player.sendMessage(String.format(DELETE_MESSAGE, number));
        }

        this.menuManager.clearPending(player.getName());
        player.closeInventory();

        reopenMainMenu(player);
    }

    private void handleCancel(Player player) {
        this.menuManager.clearPending(player.getName());
        player.closeInventory();

        reopenMainMenu(player);
    }

    private void reopenMainMenu(Player player) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);

        Bukkit.getScheduler().runTaskLater(plugin, () -> this.menuManager.openMainMenu(player), REOPEN_DELAY);
    }

    private String resolveDisplayName(ItemStack item) {
        if (item == null) return null;
        if (!item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return null;

        return meta.getDisplayName();
    }
}