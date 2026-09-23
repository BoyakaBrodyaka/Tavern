package me.boyakabrodyaka.operation.npc.registration.name;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NpcNameRegistry {

    private static final String DEFAULT_NAME = "§eПосетитель";

    public String getRandomName(List<String> names) {
        if (names == null) return DEFAULT_NAME;
        if (names.isEmpty()) return DEFAULT_NAME;

        return names.get(ThreadLocalRandom.current().nextInt(names.size()));
    }
}