package me.boyakabrodyaka.hud.menu.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GameConfirmMenu implements InventoryHolder {

    private final Inventory inventory;

    public GameConfirmMenu() {
        this.inventory = Bukkit.createInventory(this, 27, GameMenuTitles.CONFIRM);
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}