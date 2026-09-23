package me.boyakabrodyaka.commerce.storage;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

@Getter
@RequiredArgsConstructor
public class StorageManager {

    private static final int DEFAULT_DAY = 1;

    private static final String KEY_FIELD = "key";
    private static final String MONEY_FIELD = "money";
    private static final String ENERGY_FIELD = "energy";
    private static final String MAX_ENERGY_FIELD = "maxEnergy";
    private static final String TIMESTAMP_FIELD = "timestamp";
    private static final String DAY_FIELD = "day";
    private static final String DAY_TIMESTAMP_FIELD = "dayTimestamp";

    private final MongoCollection<Document> collection;

    public void save(String key, double money, int energy, int maxEnergy) {
        if (this.collection == null) return;

        Document filter = new Document(KEY_FIELD, key);
        Document update = new Document("$set", new Document(KEY_FIELD, key)
                .append(MONEY_FIELD, money)
                .append(ENERGY_FIELD, energy)
                .append(MAX_ENERGY_FIELD, maxEnergy)
                .append(TIMESTAMP_FIELD, System.currentTimeMillis()));

        this.collection.updateOne(filter, update, new UpdateOptions().upsert(true));
    }

    public void saveDay(String key, int day) {
        if (this.collection == null) return;

        Document filter = new Document(KEY_FIELD, key);
        Document update = new Document("$set", new Document(KEY_FIELD, key)
                .append(DAY_FIELD, day)
                .append(DAY_TIMESTAMP_FIELD, System.currentTimeMillis()));

        this.collection.updateOne(filter, update, new UpdateOptions().upsert(true));
    }

    public int loadDay(String key) {
        if (this.collection == null) return DEFAULT_DAY;

        Document document = this.collection.find(new Document(KEY_FIELD, key)).first();
        if (document == null) return DEFAULT_DAY;

        Integer day = document.getInteger(DAY_FIELD);
        return day == null ? DEFAULT_DAY : day;
    }

    public Document load(String key) {
        if (this.collection == null) return null;
        return this.collection.find(new Document(KEY_FIELD, key)).first();
    }

    public void delete(String key) {
        if (this.collection == null) return;
        this.collection.deleteOne(new Document(KEY_FIELD, key));
    }
}