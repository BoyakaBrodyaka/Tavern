package me.boyakabrodyaka.hud.menu.game.button;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface GameButton {

    String getId();

    int getSlot();

    ItemStack getItem(Player player);

    void onClick(Player player);
}