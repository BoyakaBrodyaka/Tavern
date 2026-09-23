package me.boyakabrodyaka.hud.day;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.Commerce;
import me.boyakabrodyaka.commerce.day.DayManager;
import me.boyakabrodyaka.hud.menu.game.button.lobby.DayActiveCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class HudDayActiveCheck implements DayActiveCheck {

    private final String pluginName;

    @Override
    public boolean isActive(Player player) {
        if (player == null) return false;

        Plugin plugin = Bukkit.getPluginManager().getPlugin(this.pluginName);
        if (!(plugin instanceof Commerce)) return false;

        Commerce commerce = (Commerce) plugin;
        DayManager dayManager = commerce.getDayManager();
        if (dayManager == null) return false;

        return dayManager.isActive(player.getUniqueId());
    }
}