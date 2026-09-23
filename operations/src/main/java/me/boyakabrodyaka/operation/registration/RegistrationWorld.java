package me.boyakabrodyaka.operation.registration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegistrationWorld {

    MAP("_map");

    private final String key;

    public boolean matches(String worldName) {
        if (worldName == null) return false;

        return worldName.contains(this.key);
    }
}