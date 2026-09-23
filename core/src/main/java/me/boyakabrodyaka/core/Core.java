package me.boyakabrodyaka.core;

import me.boyakabrodyaka.core.command.WorldCommand;
import me.boyakabrodyaka.core.listener.player.PlayerListener;
import me.boyakabrodyaka.core.listener.world.WorldProtectionListener;
import me.boyakabrodyaka.core.manager.TeleportManager;
import me.boyakabrodyaka.core.manager.WorldManager;
import me.boyakabrodyaka.core.slot.SlotManager;
import me.boyakabrodyaka.core.slot.listener.SlotListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Core extends JavaPlugin {

    private static final String LOBBY_WORLD = "tvrn_lobby";
    private static final String ADMIN_NAME = "b4bk1";
    private static final String WORLD_COMMAND = "world";

    private static final List<String> AVAILABLE_WORLDS = Collections.unmodifiableList(Arrays.asList("tvrn_lobby", "tvrn_mapbuild"));

    private WorldManager worldManager;
    private TeleportManager teleportManager;
    private SlotManager slotManager;

    @Override
    public void onEnable() {
        initializeManagers();

        this.worldManager.initializeWorlds();

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        if (this.worldManager != null) this.worldManager.cleanup();
        if (this.teleportManager != null) this.teleportManager.clear();
    }

    private void initializeManagers() {
        this.worldManager = new WorldManager(this);
        this.teleportManager = new TeleportManager(this.worldManager);
        this.slotManager = new SlotManager();
    }

    private void registerCommands() {
        WorldCommand worldCommand = new WorldCommand(this.teleportManager, this.worldManager, AVAILABLE_WORLDS);

        PluginCommand command = getCommand(WORLD_COMMAND);
        if (command == null) return;

        command.setExecutor(worldCommand);
        command.setTabCompleter(worldCommand);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this.teleportManager, this.worldManager), this);
        getServer().getPluginManager().registerEvents(new WorldProtectionListener(LOBBY_WORLD, ADMIN_NAME), this);
        getServer().getPluginManager().registerEvents(new SlotListener(this.slotManager), this);
    }
}