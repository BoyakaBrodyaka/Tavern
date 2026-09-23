package me.boyakabrodyaka.hud.scoreboard;

import me.boyakabrodyaka.commerce.Commerce;
import me.boyakabrodyaka.commerce.api.CommerceAPI;
import me.boyakabrodyaka.core.util.FormatNumber;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BoardLayout {

    private static final String PLUGIN_NAME = "TavernCommerceBP";
    private static final String TITLE = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Таверна" + ChatColor.DARK_GRAY + "]";
    private static final String SEPARATOR = ChatColor.GRAY.toString();

    public void apply(Board board, Player player) {
        board.setTitle(TITLE);

        Commerce commerce = (Commerce) Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
        if (commerce == null) return;

        CommerceAPI api = commerce.getApi();

        double money = api.getMoney(player);
        int energy = api.getEnergy(player);
        int max = api.getMaxEnergy(player);
        int day = api.getCompletedDay(player);

        board.setLine(0, SEPARATOR);
        board.setLine(1, ChatColor.GOLD + "День: " + ChatColor.YELLOW + day);
        board.setLine(2, SEPARATOR);
        board.setLine(3, ChatColor.GOLD + "Деньги: " + ChatColor.GREEN + "$" + FormatNumber.format(money));
        board.setLine(4, SEPARATOR);
        board.setLine(5, ChatColor.GOLD + "Налог: " + ChatColor.RED + "скоро");
        board.setLine(6, SEPARATOR);
        board.setLine(7, ChatColor.GOLD + "Энергия: " + ChatColor.YELLOW + energy + ChatColor.GRAY + "/" + ChatColor.YELLOW + max);
        board.setLine(8, SEPARATOR);
    }
}