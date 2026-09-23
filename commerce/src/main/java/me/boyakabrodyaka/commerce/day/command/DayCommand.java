package me.boyakabrodyaka.commerce.day.command;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.day.DayManager;
import me.boyakabrodyaka.commerce.storage.StorageLoader;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class DayCommand implements CommandExecutor {

    private static final int MIN_DAY = 1;

    private final DayManager dayManager;
    private final StorageLoader storageLoader;
    private final String worldSuffix;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        World world = player.getWorld();
        if (world == null) return true;

        String worldName = world.getName();
        if (!worldName.contains(this.worldSuffix)) return true;

        if (this.dayManager.getRegistry().isStarted(player.getUniqueId())) return true;

        int current = Math.max(this.storageLoader.loadDay(worldName), MIN_DAY);

        this.dayManager.setCurrentDay(player.getUniqueId(), current);
        this.dayManager.start(player.getUniqueId());

        return true;
    }
}