package me.boyakabrodyaka.hud.storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import me.boyakabrodyaka.hud.menu.manager.MenuManager;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.concurrent.ConcurrentHashMap;

public class DatabaseManager {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017/Tavern?authSource=admin";
    private static final String DATABASE_NAME = "Tavern";
    private static final String COLLECTION_NAME = "saves";

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> savesCollection;

    public void connect() {
        try {
            this.client = MongoClients.create(CONNECTION_STRING);
            this.database = this.client.getDatabase(DATABASE_NAME);
            this.savesCollection = this.database.getCollection(COLLECTION_NAME);
        } catch (Exception exception) {
            Bukkit.getLogger().severe("§c[HUD] Ошибка MongoDB: " + exception.getMessage());
        }
    }

    public void disconnect() {
        if (this.client == null) return;
        this.client.close();
    }

    public void saveSave(String playerName, int saveNumber, String worldName,
                         double x, double y, double z, float yaw, float pitch) {
        if (this.savesCollection == null) return;

        Document filter = new Document("player", playerName).append("saveNumber", saveNumber);

        long timestamp = System.currentTimeMillis();

        Document update = new Document("$set", new Document("player", playerName)
                .append("saveNumber", saveNumber)
                .append("world", worldName)
                .append("x", x)
                .append("y", y)
                .append("z", z)
                .append("yaw", yaw)
                .append("pitch", pitch)
                .append("createdAt", timestamp)
                .append("timestamp", timestamp));

        this.savesCollection.updateOne(filter, update, new UpdateOptions().upsert(true));
    }

    public void deleteSave(String playerName, int saveNumber) {
        if (this.savesCollection == null) return;

        Document filter = new Document("player", playerName).append("saveNumber", saveNumber);
        this.savesCollection.deleteOne(filter);
    }

    public void loadAllSaves(MenuManager menuManager) {
        if (this.savesCollection == null) return;

        for (Document document : this.savesCollection.find()) {
            String player = document.getString("player");
            Integer saveNumber = document.getInteger("saveNumber");
            String world = document.getString("world");

            if (player == null || saveNumber == null || world == null) continue;

            double x = getDouble(document, "x");
            double y = getDouble(document, "y");
            double z = getDouble(document, "z");
            float yaw = (float) getDouble(document, "yaw");
            float pitch = (float) getDouble(document, "pitch");
            long createdAt = getLong(document, "createdAt", System.currentTimeMillis());

            menuManager.setSaveData(player, saveNumber, world, x, y, z, yaw, pitch, createdAt);
        }
    }

    public void saveAllSaves(MenuManager menuManager) {
        if (this.savesCollection == null) return;

        ConcurrentHashMap<String, ConcurrentHashMap<Integer, SaveData>> allSaves = menuManager.getAllSaves();

        for (ConcurrentHashMap.Entry<String, ConcurrentHashMap<Integer, SaveData>> playerEntry : allSaves.entrySet()) {
            String player = playerEntry.getKey();
            ConcurrentHashMap<Integer, SaveData> playerSaves = playerEntry.getValue();

            for (ConcurrentHashMap.Entry<Integer, SaveData> saveEntry : playerSaves.entrySet()) {
                SaveData data = saveEntry.getValue();
                if (data == null) continue;
                if (!data.isOccupied()) continue;

                saveSave(player, saveEntry.getKey(), data.getWorld(), data.getX(), data.getY(),
                        data.getZ(), data.getYaw(), data.getPitch());
            }
        }
    }

    public MongoCollection<Document> getSavesCollection() {
        return this.savesCollection;
    }

    private double getDouble(Document document, String key) {
        Double value = document.getDouble(key);
        if (value != null) return value;

        Integer intValue = document.getInteger(key);
        return intValue == null ? 0.0D : intValue.doubleValue();
    }

    private long getLong(Document document, String key, long fallback) {
        Long value = document.getLong(key);
        return value == null ? fallback : value;
    }
}