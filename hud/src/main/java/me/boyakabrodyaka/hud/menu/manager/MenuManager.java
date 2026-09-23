package me.boyakabrodyaka.hud.menu.manager;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.Commerce;
import me.boyakabrodyaka.core.manager.WorldManager;
import me.boyakabrodyaka.hud.menu.ConfirmMenu;
import me.boyakabrodyaka.hud.menu.MainMenu;
import me.boyakabrodyaka.hud.menu.item.SaveItem;
import me.boyakabrodyaka.hud.storage.DatabaseManager;
import me.boyakabrodyaka.hud.storage.SaveData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class MenuManager {

    private static final int[] SAVE_SLOTS = {19, 21, 23, 25, 29, 31, 33};
    private static final double SPAWN_OFFSET = 0.5D;
    private static final String COMMERCE_PLUGIN_NAME = "TavernCommerceBP";
    private static final String WORLD_NOT_FOUND = "§cМир не найден, попробуйте пересоздать сохранение";

    private final DatabaseManager databaseManager;
    private final WorldManager worldManager;
    private final ConcurrentHashMap<String, MainMenu> menus = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConfirmMenu> confirmMenus = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> pendingDeletions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> pendingSlots = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer, SaveData>> saves = new ConcurrentHashMap<>();

    public void openMainMenu(Player player) {
        MainMenu menu = this.menus.computeIfAbsent(player.getName(), k -> new MainMenu(this, player));
        menu.refresh();
        menu.open(player);
    }

    public void refreshMainMenu(Player player) {
        MainMenu menu = this.menus.get(player.getName());
        if (menu == null) return;

        menu.refresh();
    }

    public void openConfirmMenu(Player player, int number, int slot) {
        String name = player.getName();

        this.pendingDeletions.put(name, number);
        this.pendingSlots.put(name, slot);

        ConfirmMenu menu = new ConfirmMenu(number);
        this.confirmMenus.put(name, menu);
        menu.open(player);
    }

    public void occupySave(Player player, int number) {
        String name = player.getName();
        String uuid = player.getUniqueId().toString();

        World world = this.worldManager.createPlayerMap(uuid, number);
        if (world == null) return;

        resetCommerceData(world.getName());

        Location location = world.getSpawnLocation().clone();
        location.add(SPAWN_OFFSET, 0, SPAWN_OFFSET);
        player.teleport(location);

        SaveData data = new SaveData(true, world.getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        this.saves.computeIfAbsent(name, k -> new ConcurrentHashMap<>()).put(number, data);
        this.databaseManager.saveSave(name, number, world.getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        MainMenu menu = this.menus.get(name);
        if (menu == null) return;

        Inventory inventory = menu.getInventory();

        for (int i = 0; i < SAVE_SLOTS.length; i++) {
            if (i + 1 != number) continue;

            inventory.setItem(SAVE_SLOTS[i], new SaveItem(number, data).createOccupied());
            break;
        }
    }

    public void teleportToSave(Player player, int number) {
        String name = player.getName();
        ConcurrentHashMap<Integer, SaveData> playerSaves = this.saves.get(name);
        if (playerSaves == null) return;

        SaveData data = playerSaves.get(number);
        if (data == null) return;
        if (!data.isOccupied()) return;

        World world = Bukkit.getWorld(data.getWorld());
        if (world == null) {
            player.sendMessage(WORLD_NOT_FOUND);
            return;
        }

        Location location = new Location(world, data.getX(), data.getY(), data.getZ(), data.getYaw(), data.getPitch());
        player.teleport(location);
        player.closeInventory();
    }

    public void deleteSave(Player player, int number) {
        String name = player.getName();
        String uuid = player.getUniqueId().toString();

        ConcurrentHashMap<Integer, SaveData> playerSaves = this.saves.get(name);
        if (playerSaves != null) {
            SaveData data = playerSaves.get(number);

            if (data != null) {
                this.worldManager.deletePlayerMap(uuid, number);
                deleteCommerceData(data.getWorld());
            }

            playerSaves.remove(number);
        }

        this.databaseManager.deleteSave(name, number);

        Integer slot = this.pendingSlots.get(name);
        if (slot != null) {
            MainMenu menu = this.menus.get(name);

            if (menu != null) {
                Inventory inventory = menu.getInventory();
                inventory.setItem(slot, new SaveItem(number, null).create());
            }
        }

        this.pendingDeletions.remove(name);
        this.pendingSlots.remove(name);
    }

    public SaveData getSaveData(String playerName, int number) {
        ConcurrentHashMap<Integer, SaveData> playerSaves = this.saves.get(playerName);
        if (playerSaves == null) return null;

        return playerSaves.get(number);
    }

    public void setSaveData(String playerName, int number, String world, double x, double y, double z, float yaw, float pitch, long createdAt) {
        SaveData data = new SaveData(true, world, x, y, z, yaw, pitch, createdAt);
        this.saves.computeIfAbsent(playerName, k -> new ConcurrentHashMap<>()).put(number, data);
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<Integer, SaveData>> getAllSaves() {
        return this.saves;
    }

    public int getPendingNumber(String playerName) {
        return this.pendingDeletions.getOrDefault(playerName, 0);
    }

    public void clearPending(String playerName) {
        this.pendingDeletions.remove(playerName);
        this.pendingSlots.remove(playerName);
    }

    public void clear() {
        this.menus.clear();
        this.confirmMenus.clear();
        this.pendingDeletions.clear();
        this.pendingSlots.clear();
    }

    private void resetCommerceData(String world) {
        Commerce commerce = resolveCommerce();
        if (commerce == null) return;

        commerce.getApi().reset(world);
    }

    private void deleteCommerceData(String world) {
        Commerce commerce = resolveCommerce();
        if (commerce == null) return;

        commerce.getApi().delete(world);
    }

    private Commerce resolveCommerce() {
        return (Commerce) Bukkit.getPluginManager().getPlugin(COMMERCE_PLUGIN_NAME);
    }
}