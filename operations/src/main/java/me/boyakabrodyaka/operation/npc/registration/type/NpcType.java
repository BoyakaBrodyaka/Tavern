package me.boyakabrodyaka.operation.npc.registration.type;

import lombok.Getter;
import me.boyakabrodyaka.operation.npc.registration.skin.NpcSkin;

import java.util.Collections;
import java.util.List;

@Getter
public class NpcType {

    private final String key;
    private final List<NpcSkin> skins;
    private final List<String> names;

    public NpcType(String key, List<NpcSkin> skins, List<String> names) {
        this.key = key;
        this.skins = Collections.unmodifiableList(skins);
        this.names = Collections.unmodifiableList(names);
    }
}