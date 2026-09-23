package me.boyakabrodyaka.operation.dining;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiningWorld {

    MAP("_map");

    private final String key;

    public boolean matches(String worldName) {
        return worldName != null && worldName.contains(this.key);
    }
}