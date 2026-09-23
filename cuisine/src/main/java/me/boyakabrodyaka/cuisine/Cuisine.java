package me.boyakabrodyaka.cuisine;

import me.boyakabrodyaka.cuisine.grill.listener.GrillListener;
import me.boyakabrodyaka.cuisine.kitchen.bridge.KitchenModBridge;
import me.boyakabrodyaka.cuisine.kitchen.listener.KitchenListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Cuisine extends JavaPlugin {

    private KitchenListener kitchenListener;
    private GrillListener grillListener;

    @Override
    public void onEnable() {
        KitchenModBridge modBridge = new KitchenModBridge();

        this.kitchenListener = new KitchenListener(this);
        this.grillListener = new GrillListener(this, modBridge);

        registerListeners();
    }

    @Override
    public void onDisable() {
        if (this.kitchenListener != null) this.kitchenListener.clear();
        if (this.grillListener != null) this.grillListener.clear();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(this.kitchenListener, this);
        getServer().getPluginManager().registerEvents(this.grillListener, this);
    }
}