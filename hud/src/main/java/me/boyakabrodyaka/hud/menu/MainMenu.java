package me.boyakabrodyaka.hud.menu;

import me.boyakabrodyaka.hud.menu.item.SaveItem;
import me.boyakabrodyaka.hud.menu.manager.MenuManager;
import me.boyakabrodyaka.hud.storage.SaveData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MainMenu implements InventoryHolder {

    private static final String TITLE = "§8Навигация";
    private static final int SIZE = 54;
    private static final int[] SAVE_SLOTS = {19, 21, 23, 25, 29, 31, 33};

    private final Inventory inventory;
    private final MenuManager menuManager;
    private final Player player;

    public MainMenu(MenuManager menuManager, Player player) {
        this.menuManager = menuManager;
        this.player = player;
        this.inventory = Bukkit.createInventory(this, SIZE, TITLE);
        fillItems();
    }

    public void refresh() {
        this.inventory.clear();
        fillItems();
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    private void fillItems() {
        for (int i = 0; i < SAVE_SLOTS.length; i++) {
            int number = i + 1;
            SaveData data = this.menuManager.getSaveData(this.player.getName(), number);

            if (data != null && data.isOccupied()) {
                this.inventory.setItem(SAVE_SLOTS[i], new SaveItem(number, data).createOccupied());
                continue;
            }

            this.inventory.setItem(SAVE_SLOTS[i], new SaveItem(number, null).create());
        }
    }
}