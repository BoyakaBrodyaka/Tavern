package me.boyakabrodyaka.hud.menu.game.button.exit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.hud.menu.game.button.GameButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class CloseButton implements GameButton {

    private static final String DISPLAY_NAME = "§cВыход";
    private static final String LORE = "§7Закрыть меню";
    private static final String ID = "close";

    private final int slot;

    @Override
    public String getId() { return ID; }

    @Override
    public ItemStack getItem(Player player) {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(DISPLAY_NAME);
        meta.setLore(Collections.singletonList(LORE));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onClick(Player player) {
        player.closeInventory();
    }
}