package me.boyakabrodyaka.commerce.storage;

import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.account.Account;
import me.boyakabrodyaka.commerce.account.AccountManager;
import me.boyakabrodyaka.commerce.energy.Energy;
import me.boyakabrodyaka.commerce.energy.EnergyManager;
import org.bson.Document;
import org.bukkit.Bukkit;

@RequiredArgsConstructor
public class StorageLoader {

    private static final double DEFAULT_BALANCE = 100.0D;

    private static final String KEY_FIELD = "key";
    private static final String MONEY_FIELD = "money";
    private static final String ENERGY_FIELD = "energy";
    private static final String MAX_ENERGY_FIELD = "maxEnergy";

    private final StorageManager storage;

    public void loadAll(AccountManager accountManager, EnergyManager energyManager) {
        MongoCollection<Document> collection = this.storage.getCollection();
        if (collection == null) return;

        for (Document document : collection.find()) {
            String key = document.getString(KEY_FIELD);
            if (key == null) continue;
            applyDocument(document, key, accountManager, energyManager);
        }
    }

    public void load(String key, AccountManager accountManager, EnergyManager energyManager) {
        Document document = this.storage.load(key);
        if (document == null) return;
        applyDocument(document, key, accountManager, energyManager);
    }

    public void save(String key, AccountManager accountManager, EnergyManager energyManager) {
        Account account = accountManager.get(key);
        Energy energy = energyManager.get(key);

        if (account == null || energy == null) return;

        this.storage.save(key, account.getMoney(), energy.getValue(), energy.getMax());
    }

    public void delete(String key, AccountManager accountManager, EnergyManager energyManager) {
        accountManager.remove(key);
        energyManager.remove(key);
        this.storage.delete(key);
    }

    public void reset(String key, AccountManager accountManager, EnergyManager energyManager) {
        Account account = accountManager.get(key);
        account.setMoney(DEFAULT_BALANCE);

        Energy energy = energyManager.getOrCreate(key);
        energy.setValue(energy.getMax());

        this.storage.save(key, DEFAULT_BALANCE, energy.getMax(), energy.getMax());
    }

    public void saveDay(String key, int day) { this.storage.saveDay(key, day); }

    public int loadDay(String key) { return this.storage.loadDay(key); }

    private void applyDocument(Document document, String key, AccountManager accountManager, EnergyManager energyManager) {
        Account account = accountManager.get(key);
        Energy energy = energyManager.getOrCreate(key);

        if (document.containsKey(MONEY_FIELD)) account.setMoney(document.getDouble(MONEY_FIELD));
        if (document.containsKey(ENERGY_FIELD)) energy.setValue(document.getInteger(ENERGY_FIELD));
        if (document.containsKey(MAX_ENERGY_FIELD)) energy.setMax(document.getInteger(MAX_ENERGY_FIELD));
    }
}