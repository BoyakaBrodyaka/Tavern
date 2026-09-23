package me.boyakabrodyaka.commerce.storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;

public class DatabaseManager {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017/Tavern?authSource=admin";
    private static final String DATABASE_NAME = "Tavern";
    private static final String COLLECTION_NAME = "commerce";

    private MongoClient client;
    private MongoDatabase database;

    @Getter
    private MongoCollection<Document> commerceCollection;

    public void connect() {
        try {
            this.client = MongoClients.create(CONNECTION_STRING);
            this.database = this.client.getDatabase(DATABASE_NAME);
            this.commerceCollection = this.database.getCollection(COLLECTION_NAME);
        } catch (Exception exception) {
            Bukkit.getLogger().severe("§cОшибка подключения к MongoDB: " + exception.getMessage());
        }
    }

    public void disconnect() {
        if (this.client == null) return;
        this.client.close();
    }
}