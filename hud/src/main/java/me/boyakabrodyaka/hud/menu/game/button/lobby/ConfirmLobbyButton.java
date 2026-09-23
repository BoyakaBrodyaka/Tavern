package me.boyakabrodyaka.hud.menu.game.button.lobby;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.hud.menu.game.GameMenuManager;
import me.boyakabrodyaka.hud.menu.game.button.GameButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class ConfirmLobbyButton implements GameButton {

    private static final String ID = "confirm_lobby";
    private static final String DISPLAY_NAME = "§aПодтвердить";
    private static final String LORE = "§7Выйти в лобби и сбросить день";

    private static final int GLASS_COLOR = 5;
    private static final int ITEM_AMOUNT = 1;

    private final int slot;
    private final GameMenuManager gameMenuManager;
    private final LobbyButton lobbyButton;

    @Override
    public String getId() { return ID; }

    @Override
    public ItemStack getItem(Player player) {
        ItemStack item = new ItemStack(Material.STAINED_GLASS, ITEM_AMOUNT, (short) GLASS_COLOR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(DISPLAY_NAME);
        meta.setLore(Collections.singletonList(LORE));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onClick(Player player) {
        this.gameMenuManager.closeConfirm(player);
        this.lobbyButton.teleport(player);
    }
}