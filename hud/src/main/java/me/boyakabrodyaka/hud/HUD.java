package me.boyakabrodyaka.hud;

import me.boyakabrodyaka.core.manager.WorldManager;
import me.boyakabrodyaka.hud.day.HudDayActiveCheck;
import me.boyakabrodyaka.hud.hologram.HologramCleanup;
import me.boyakabrodyaka.hud.hologram.registration.RegistrationHologramManager;
import me.boyakabrodyaka.hud.hologram.registration.listener.RegistrationHologramListener;
import me.boyakabrodyaka.hud.hologram.table.TableHologramManager;
import me.boyakabrodyaka.hud.hologram.table.listener.TableHologramListener;
import me.boyakabrodyaka.hud.menu.game.GameMenuManager;
import me.boyakabrodyaka.hud.menu.game.command.GameMenuCommand;
import me.boyakabrodyaka.hud.menu.game.listener.GameMenuListener;
import me.boyakabrodyaka.hud.menu.listener.compass.CompassListener;
import me.boyakabrodyaka.hud.menu.listener.menu.ConfirmMenuListener;
import me.boyakabrodyaka.hud.menu.listener.menu.MenuClickListener;
import me.boyakabrodyaka.hud.menu.listener.menu.MenuWorldListener;
import me.boyakabrodyaka.hud.menu.manager.MenuManager;
import me.boyakabrodyaka.hud.scoreboard.BoardManager;
import me.boyakabrodyaka.hud.scoreboard.BoardUpdater;
import me.boyakabrodyaka.hud.scoreboard.listener.HudListener;
import me.boyakabrodyaka.hud.storage.DatabaseManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class HUD extends JavaPlugin {

    private static final String COMMERCE_PLUGIN_NAME = "TavernCommerceBP";
    private static final String MENU_COMMAND = "menu";

    private BoardManager boardManager;
    private BoardUpdater boardUpdater;
    private MenuManager menuManager;
    private GameMenuManager gameMenuManager;
    private DatabaseManager databaseManager;
    private WorldManager worldManager;
    private RegistrationHologramManager registrationHologramManager;
    private TableHologramManager tableHologramManager;
    private HologramCleanup hologramCleanup;

    @Override
    public void onEnable() {
        initializeManagers();
        initializeHolograms();
        registerListeners();
        registerCommands();

        this.boardUpdater.start();
        this.databaseManager.loadAllSaves(this.menuManager);
    }

    @Override
    public void onDisable() {
        if (this.hologramCleanup != null) this.hologramCleanup.cleanupAll();

        if (this.databaseManager != null) {
            this.databaseManager.saveAllSaves(this.menuManager);
            this.databaseManager.disconnect();
        }

        if (this.boardUpdater != null) this.boardUpdater.cancel();
        if (this.boardManager != null) this.boardManager.clear();
        if (this.menuManager != null) this.menuManager.clear();
        if (this.gameMenuManager != null) this.gameMenuManager.clear();
        if (this.registrationHologramManager != null) this.registrationHologramManager.clear();
        if (this.tableHologramManager != null) this.tableHologramManager.clear();
    }

    private void initializeManagers() {
        this.databaseManager = new DatabaseManager();
        this.databaseManager.connect();

        this.worldManager = new WorldManager(this);

        this.boardManager = new BoardManager();
        this.boardUpdater = new BoardUpdater(this.boardManager, this);
        this.menuManager = new MenuManager(this.databaseManager, this.worldManager);
        this.gameMenuManager = new GameMenuManager(new HudDayActiveCheck(COMMERCE_PLUGIN_NAME));
    }

    private void initializeHolograms() {
        this.registrationHologramManager = new RegistrationHologramManager();
        this.tableHologramManager = new TableHologramManager();
        this.hologramCleanup = new HologramCleanup(
                this.registrationHologramManager.getLineTop(),
                this.registrationHologramManager.getLineBottom(),
                this.tableHologramManager.getLineTop(),
                this.tableHologramManager.getLineBottom()
        );

        this.hologramCleanup.cleanupAll();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new HudListener(this.boardManager), this);
        getServer().getPluginManager().registerEvents(new CompassListener(this.menuManager), this);
        getServer().getPluginManager().registerEvents(new MenuClickListener(this.menuManager), this);
        getServer().getPluginManager().registerEvents(new ConfirmMenuListener(this.menuManager), this);
        getServer().getPluginManager().registerEvents(new MenuWorldListener(this.menuManager), this);
        getServer().getPluginManager().registerEvents(new GameMenuListener(this.gameMenuManager), this);
        getServer().getPluginManager().registerEvents(new RegistrationHologramListener(this.registrationHologramManager), this);
        getServer().getPluginManager().registerEvents(new TableHologramListener(this.tableHologramManager), this);
    }

    private void registerCommands() {
        PluginCommand command = getCommand(MENU_COMMAND);
        if (command == null) return;

        command.setExecutor(new GameMenuCommand(this.gameMenuManager));
    }
}