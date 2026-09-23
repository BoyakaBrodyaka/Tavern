package me.boyakabrodyaka.operation.registration;

import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class RegistrationRegistry {

    private final ConcurrentHashMap<String, Boolean> inside = new ConcurrentHashMap<>();

    public boolean isInside(Player player) {
        return this.inside.getOrDefault(player.getName(), false);
    }

    public void setInside(Player player, boolean value) {
        if (value) {
            this.inside.put(player.getName(), true);
            return;
        }

        this.inside.remove(player.getName());
    }

    public void remove(Player player) {
        this.inside.remove(player.getName());
    }

    public void clear() {
        this.inside.clear();
    }
}