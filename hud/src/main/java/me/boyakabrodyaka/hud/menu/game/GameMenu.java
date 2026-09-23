package me.boyakabrodyaka.hud.menu.game;

import me.boyakabrodyaka.hud.menu.game.button.GameButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GameMenu implements InventoryHolder {

    private static final int SIZE = 54;

    private final Inventory inventory;

    public GameMenu() { this.inventory = Bukkit.createInventory(this, SIZE, GameMenuTitles.MENU); }

    public void render(Player player, GameButton[] buttons) {
        this.inventory.clear();
        for (GameButton button : buttons) this.inventory.setItem(button.getSlot(), button.getItem(player));
    }

    public void open(Player player) { player.openInventory(this.inventory); }

    @Override
    public Inventory getInventory() { return this.inventory; }
}