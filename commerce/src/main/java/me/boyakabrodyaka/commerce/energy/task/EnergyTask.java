package me.boyakabrodyaka.commerce.energy.task;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.energy.Energy;
import me.boyakabrodyaka.commerce.energy.EnergyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class EnergyTask extends BukkitRunnable {

    private static final String WORLD_SUFFIX = "_map";

    private static final int FOOD_LEVEL_HIGH = 20;
    private static final int FOOD_LEVEL_LOW = 5;
    private static final int LOW_THRESHOLD = 10;

    private final EnergyManager energyManager;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getWorld().getName().contains(WORLD_SUFFIX)) continue;

            Energy energy = this.energyManager.get(player);

            if (player.isSprinting() && energy.canSprint()) energy.consume(energy.getSprintCost());
            else if (!player.isSprinting() && !energy.isFull()) energy.restore(energy.getRegenAmount());

            int value = energy.getValue();

            player.setFoodLevel(value >= LOW_THRESHOLD ? FOOD_LEVEL_HIGH : FOOD_LEVEL_LOW);
            player.setSaturation(0.0F);
            player.setExhaustion(0.0F);
        }
    }
}