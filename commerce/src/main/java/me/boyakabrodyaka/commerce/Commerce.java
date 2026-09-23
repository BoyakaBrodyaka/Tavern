package me.boyakabrodyaka.commerce;

import me.boyakabrodyaka.commerce.account.AccountFactory;
import me.boyakabrodyaka.commerce.account.AccountManager;
import me.boyakabrodyaka.commerce.account.AccountSettings;
import me.boyakabrodyaka.commerce.api.CommerceAPI;
import me.boyakabrodyaka.commerce.command.MoneyCommand;
import me.boyakabrodyaka.commerce.day.DayCoordinate;
import me.boyakabrodyaka.commerce.day.DayManager;
import me.boyakabrodyaka.commerce.day.DayModule;
import me.boyakabrodyaka.commerce.day.command.DayCommand;
import me.boyakabrodyaka.commerce.day.command.SkipDayCommand;
import me.boyakabrodyaka.commerce.energy.EnergyFactory;
import me.boyakabrodyaka.commerce.energy.EnergyManager;
import me.boyakabrodyaka.commerce.energy.EnergyModule;
import me.boyakabrodyaka.commerce.energy.listener.EnergyListener;
import me.boyakabrodyaka.commerce.storage.DatabaseManager;
import me.boyakabrodyaka.commerce.storage.StorageLoader;
import me.boyakabrodyaka.commerce.storage.StorageManager;
import me.boyakabrodyaka.commerce.storage.listener.StorageListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Commerce extends JavaPlugin {

    private DatabaseManager databaseManager;
    private StorageLoader storageLoader;
    private AccountManager accountManager;
    private EnergyModule energyModule;
    private DayModule dayModule;
    private CommerceAPI api;

    @Override
    public void onEnable() {
        initializeStorage();
        initializeManagers();
        this.storageLoader.loadAll(this.accountManager, this.energyModule.getEnergyManager());
        registerListeners();
        registerCommands();

        this.energyModule.startTask(this);
        this.dayModule.start(this);
    }

    @Override
    public void onDisable() {
        if (this.accountManager != null) this.accountManager.clear();
        if (this.energyModule != null) this.energyModule.disable();
        if (this.dayModule != null) this.dayModule.disable();
        if (this.databaseManager != null) this.databaseManager.disconnect();
    }

    private void initializeStorage() {
        this.databaseManager = new DatabaseManager();
        this.databaseManager.connect();

        StorageManager storageManager = new StorageManager(this.databaseManager.getCommerceCollection());
        this.storageLoader = new StorageLoader(storageManager);
    }

    private void initializeManagers() {
        this.accountManager = new AccountManager(new AccountFactory(AccountSettings.DEFAULT_BALANCE.getValue()));
        this.energyModule = new EnergyFactory().createDefault();
        this.dayModule = new DayModule(this.storageLoader);
        this.api = new CommerceAPI(this.accountManager, this.energyModule.getEnergyManager(), this.storageLoader);
    }

    private void registerListeners() {
        EnergyManager energyManager = this.energyModule.getEnergyManager();

        getServer().getPluginManager().registerEvents(new EnergyListener(energyManager), this);
        getServer().getPluginManager().registerEvents(new StorageListener(this.storageLoader, this.accountManager, energyManager), this);
    }

    private void registerCommands() {
        getCommand("money").setExecutor(new MoneyCommand(this.accountManager));
        getCommand("day").setExecutor(new DayCommand(this.dayModule.getDayManager(), this.storageLoader, DayCoordinate.WORLD_SUFFIX.getKey()));
        getCommand("skipday").setExecutor(new SkipDayCommand(this.dayModule.getDayFinisher()));
    }

    public CommerceAPI getApi() { return this.api; }

    public DayManager getDayManager() { return this.dayModule.getDayManager(); }
}