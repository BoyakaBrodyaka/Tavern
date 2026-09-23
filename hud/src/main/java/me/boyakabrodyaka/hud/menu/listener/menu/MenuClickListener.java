package me.boyakabrodyaka.hud.menu.listener.menu;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.hud.menu.manager.MenuManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor
public class MenuClickListener implements Listener {

    private static final String TITLE = "§8Навигация";
    private static final String NUMBER_MARKER = "№";

    private static final short COLOR_EMPTY = 7;
    private static final short COLOR_OCCUPIED = 5;

    private final MenuManager menuManager;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getView() == null) return;
        if (!event.getView().getTitle().equals(TITLE)) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || clicked.getType() == Material.AIR) return;

        int number = resolveNumber(clicked);
        if (number == 0) return;

        if (isSlot(clicked, COLOR_EMPTY)) {
            this.menuManager.occupySave(player, number);
            return;
        }

        if (isSlot(clicked, COLOR_OCCUPIED)) handleOccupiedClick(event, player, number);
    }

    private void handleOccupiedClick(InventoryClickEvent event, Player player, int number) {
        if (event.isLeftClick()) {
            this.menuManager.teleportToSave(player, number);
            return;
        }

        if (event.isRightClick()) {
            player.closeInventory();
            this.menuManager.openConfirmMenu(player, number, event.getSlot());
        }
    }

    private boolean isSlot(ItemStack item, short color) { return item.getType() == Material.WOOL && item.getDurability() == color; }

    private int resolveNumber(ItemStack item) {
        if (!item.hasItemMeta()) return 0;

        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return 0;

        String name = meta.getDisplayName();
        if (!name.contains(NUMBER_MARKER)) return 0;

        String[] parts = name.split(NUMBER_MARKER);
        if (parts.length <= 1) return 0;

        try {
            return Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException exception) {
            return 0;
        }
    }
}