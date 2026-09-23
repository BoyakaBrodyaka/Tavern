package me.boyakabrodyaka.core.command;

import me.boyakabrodyaka.core.manager.TeleportManager;
import me.boyakabrodyaka.core.manager.WorldManager;
import me.boyakabrodyaka.core.util.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabCompleter {

    private static final String PLAYER_ONLY = "&cТолько для игроков";
    private static final String WORLD_NOT_FOUND = "&cМир не найден: ";
    private static final String AVAILABLE_WORLDS = "&7Доступные миры: ";
    private static final String USAGE_PREFIX = "&cИспользование: /world <";
    private static final String USAGE_SUFFIX = ">";
    private static final String USAGE_SEPARATOR = "|";
    private static final String LIST_SEPARATOR = ", ";

    private final TeleportManager teleportManager;
    private final WorldManager worldManager;
    private final List<String> availableWorlds;
    private final String usageMessage;

    public WorldCommand(TeleportManager teleportManager, WorldManager worldManager, List<String> availableWorlds) {
        this.teleportManager = teleportManager;
        this.worldManager = worldManager;
        this.availableWorlds = Collections.unmodifiableList(new ArrayList<>(availableWorlds));
        this.usageMessage = USAGE_PREFIX + String.join(USAGE_SEPARATOR, availableWorlds) + USAGE_SUFFIX;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.color(PLAYER_ONLY));
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(Color.color(this.usageMessage));
            return true;
        }

        String target = args[0].toLowerCase();

        if (!this.worldManager.worldExists(target)) {
            player.sendMessage(Color.color(WORLD_NOT_FOUND + target));
            player.sendMessage(Color.color(AVAILABLE_WORLDS + String.join(LIST_SEPARATOR, this.availableWorlds)));
            return true;
        }

        this.teleportManager.teleportToWorld(player, target);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) return Collections.emptyList();

        String partial = args[0].toLowerCase();
        List<String> completions = new ArrayList<>();

        for (String world : this.availableWorlds) {
            if (!world.startsWith(partial)) continue;

            completions.add(world);
        }

        return completions;
    }
}