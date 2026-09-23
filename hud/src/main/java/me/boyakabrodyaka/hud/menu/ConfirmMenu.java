package me.boyakabrodyaka.hud.menu;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
public class ConfirmMenu implements InventoryHolder {

    private static final String TITLE = "§8Подтверждение удаления";

    private static final int SIZE = 54;
    private static final int AMOUNT = 1;

    private static final int SLOT_CONFIRM = 20;
    private static final int SLOT_DISPLAY = 22;
    private static final int SLOT_CANCEL = 24;

    private static final short COLOR_CONFIRM = 5;
    private static final short COLOR_CANCEL = 14;

    private static final String CONFIRM_NAME = "§aПодтвердить";
    private static final String CANCEL_NAME = "§cОтменить";

    private static final String SAVE_DISPLAY_FORMAT = "§aСохранение №%d";
    private static final String CONFIRM_LORE_FORMAT = "§7Удалить сохранение №%d";

    private final int saveNumber;

    private final Inventory inventory;

    public ConfirmMenu(int saveNumber) {
        this.saveNumber = saveNumber;
        this.inventory = Bukkit.createInventory(this, SIZE, TITLE);
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
        this.inventory.setItem(SLOT_CONFIRM, createConfirmItem());
        this.inventory.setItem(SLOT_DISPLAY, createSaveDisplay());
        this.inventory.setItem(SLOT_CANCEL, createCancelItem());
    }

    private ItemStack createConfirmItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS, AMOUNT, COLOR_CONFIRM);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CONFIRM_NAME);
        meta.setLore(Collections.singletonList(String.format(CONFIRM_LORE_FORMAT, this.saveNumber)));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createCancelItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS, AMOUNT, COLOR_CANCEL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CANCEL_NAME);
        meta.setLore(Collections.singletonList("§7Вернуться назад"));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createSaveDisplay() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(String.format(SAVE_DISPLAY_FORMAT, this.saveNumber));
        meta.setLore(Arrays.asList("§aЗанято", "§7Прогресс:", "§7 Скоро..."));
        item.setItemMeta(meta);
        return item;
    }
}