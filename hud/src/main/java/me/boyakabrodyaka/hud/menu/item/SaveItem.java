package me.boyakabrodyaka.hud.menu.item;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.Commerce;
import me.boyakabrodyaka.commerce.api.CommerceAPI;
import me.boyakabrodyaka.core.util.FormatNumber;
import me.boyakabrodyaka.hud.storage.SaveData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class SaveItem {

    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String DATE_UNKNOWN = "§7неизвестно";

    private static final String DISPLAY_NAME_FORMAT = "§aСохранение №%d";
    private static final String EMPTY_LORE = "§7Нажмите чтобы загрузить";

    private static final short COLOR_EMPTY = 7;
    private static final short COLOR_OCCUPIED = 5;
    private static final int AMOUNT = 1;

    private static final String PLUGIN_NAME = "TavernCommerceBP";
    private static final String UNKNOWN_VALUE = "0";
    private static final String UNKNOWN_MAX_ENERGY = "100";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    private final int number;
    private final SaveData data;

    public ItemStack create() {
        ItemStack item = new ItemStack(Material.WOOL, AMOUNT, COLOR_EMPTY);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(String.format(DISPLAY_NAME_FORMAT, this.number));
        meta.setLore(Collections.singletonList(EMPTY_LORE));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createOccupied() {
        ItemStack item = new ItemStack(Material.WOOL, AMOUNT, COLOR_OCCUPIED);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(String.format(DISPLAY_NAME_FORMAT, this.number));
        meta.setLore(buildLore());
        item.setItemMeta(meta);
        return item;
    }

    private List<String> buildLore() {
        List<String> lore = new ArrayList<>();
        lore.add("§7Дата создания: §f" + getDate());
        lore.add("");
        lore.add("§7День: §e" + getDay());
        lore.add("");
        lore.add("§7Баланс: §a$" + getMoney());
        lore.add("§7Энергия: §e" + getEnergy() + "§7/§e" + getMaxEnergy());
        lore.add("");
        lore.add("§eЛКМ - телепортироваться");
        lore.add("§cПКМ - удалить сохранение");
        return lore;
    }

    private String getDate() {
        if (this.data == null) return DATE_UNKNOWN;
        if (this.data.getCreatedAt() == 0L) return DATE_UNKNOWN;

        return this.dateFormat.format(new Date(this.data.getCreatedAt()));
    }

    private String getDay() {
        if (this.data == null) return UNKNOWN_VALUE;

        int day = new CommerceResolver().getCompletedDayByKey(this.data.getWorld());
        return String.valueOf(day);
    }

    private String getMoney() {
        if (this.data == null) return UNKNOWN_VALUE;

        double money = new CommerceResolver().getMoneyByKey(this.data.getWorld());
        return FormatNumber.format(money);
    }

    private String getEnergy() {
        if (this.data == null) return UNKNOWN_VALUE;

        return String.valueOf(new CommerceResolver().getEnergyByKey(this.data.getWorld()));
    }

    private String getMaxEnergy() {
        if (this.data == null) return UNKNOWN_MAX_ENERGY;

        return String.valueOf(new CommerceResolver().getMaxEnergyByKey(this.data.getWorld()));
    }

    private static class CommerceResolver {

        private final CommerceAPI api;

        private CommerceResolver() {
            Commerce commerce = (Commerce) Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
            this.api = commerce == null ? null : commerce.getApi();
        }

        private int getCompletedDayByKey(String key) {
            if (this.api == null) return 0;
            return this.api.getCompletedDayByKey(key);
        }

        private double getMoneyByKey(String key) {
            if (this.api == null) return 0.0D;
            return this.api.getMoneyByKey(key);
        }

        private int getEnergyByKey(String key) {
            if (this.api == null) return 0;
            return this.api.getEnergyByKey(key);
        }

        private int getMaxEnergyByKey(String key) {
            if (this.api == null) return 100;
            return this.api.getMaxEnergyByKey(key);
        }
    }
}