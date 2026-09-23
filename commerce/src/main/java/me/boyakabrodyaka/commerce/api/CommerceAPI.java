package me.boyakabrodyaka.commerce.api;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.account.AccountManager;
import me.boyakabrodyaka.commerce.energy.EnergyManager;
import me.boyakabrodyaka.commerce.storage.StorageLoader;
import org.bukkit.World;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CommerceAPI {

    private static final int MIN_COMPLETED_DAY = 0;

    private final AccountManager accountManager;
    private final EnergyManager energyManager;
    private final StorageLoader storageLoader;

    public double getMoney(Player player) { return getMoneyByKey(worldKey(player)); }
    public double getMoneyByKey(String key) { return this.accountManager.get(key).getMoney(); }

    public int getEnergy(Player player) { return getEnergyByKey(worldKey(player)); }

    public int getEnergyByKey(String key) { return this.energyManager.getOrCreate(key).getValue(); }

    public int getMaxEnergy(Player player) { return getMaxEnergyByKey(worldKey(player)); }

    public int getMaxEnergyByKey(String key) { return this.energyManager.getOrCreate(key).getMax(); }

    public int getCompletedDay(Player player) { return getCompletedDayByKey(worldKey(player)); }

    public int getCompletedDayByKey(String key) { return Math.max(this.storageLoader.loadDay(key) - 1, MIN_COMPLETED_DAY); }

    public void delete(String key) { this.storageLoader.delete(key, this.accountManager, this.energyManager); }

    public void reset(String key) { this.storageLoader.reset(key, this.accountManager, this.energyManager); }

    private String worldKey(Player player) {
        World world = player.getWorld();
        if (world == null) return "";
        return world.getName();
    }
}