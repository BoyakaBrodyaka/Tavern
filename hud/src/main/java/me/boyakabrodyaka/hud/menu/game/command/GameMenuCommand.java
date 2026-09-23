package me.boyakabrodyaka.hud.menu.game.command;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.hud.menu.game.GameMenuManager;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GameMenuCommand implements CommandExecutor {

    private static final String WORLD_SUFFIX = "_map";

    private final GameMenuManager gameMenuManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        World world = player.getWorld();
        if (world == null) return true;
        if (!world.getName().contains(WORLD_SUFFIX)) return true;

        this.gameMenuManager.open(player);

        return true;
    }
}