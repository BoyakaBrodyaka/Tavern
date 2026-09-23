package me.boyakabrodyaka.operation.npc.registration.skin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NpcSkinRegistry {

    public NpcSkin getRandomSkin(List<NpcSkin> skins) {
        if (skins == null) return null;
        if (skins.isEmpty()) return null;

        return skins.get(ThreadLocalRandom.current().nextInt(skins.size()));
    }

    public NpcSkin create(String value, String signature) {
        return new NpcSkin(value, signature);
    }

    public List<NpcSkin> createList() {
        return new ArrayList<>();
    }
}