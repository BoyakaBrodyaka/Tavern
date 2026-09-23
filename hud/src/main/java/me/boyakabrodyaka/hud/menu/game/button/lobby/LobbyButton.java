package me.boyakabrodyaka.hud.menu.game.button.lobby;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.hud.menu.game.GameMenuManager;
import me.boyakabrodyaka.hud.menu.game.button.GameButton;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class LobbyButton implements GameButton {

    private static final String ID = "lobby";
    private static final String DISPLAY_NAME = "§aВыход в лобби";
    private static final String LORE_DEFAULT = "§7Телепортироваться в лобби";
    private static final String LORE_DAY_ACTIVE = "§cПри выходе в лобби день будет сброшен";

    private static final double SPAWN_OFFSET = 0.5D;

    private final int slot;
    private final String worldName;
    private final DayActiveCheck dayActiveCheck;
    private final GameMenuManager gameMenuManager;

    @Override
    public String getId() { return ID; }

    @Override
    public ItemStack getItem(Player player) {
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(DISPLAY_NAME);

        if (player != null && this.dayActiveCheck.isActive(player)) meta.setLore(Arrays.asList(LORE_DEFAULT, "", LORE_DAY_ACTIVE));
        else meta.setLore(Collections.singletonList(LORE_DEFAULT));

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onClick(Player player) {
        if (this.dayActiveCheck.isActive(player)) {
            this.gameMenuManager.openConfirm(player);
            return;
        }

        teleport(player);
    }

    public void teleport(Player player) {
        World world = Bukkit.getWorld(this.worldName);
        if (world == null) return;

        Location location = world.getSpawnLocation().clone();
        location.add(SPAWN_OFFSET, 0, SPAWN_OFFSET);

        player.closeInventory();
        player.teleport(location);
    }
}