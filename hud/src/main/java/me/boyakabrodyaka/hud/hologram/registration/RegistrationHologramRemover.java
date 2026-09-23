package me.boyakabrodyaka.hud.hologram.registration;

import org.bukkit.entity.ArmorStand;

public class RegistrationHologramRemover {

    public void remove(ArmorStand stand) {
        if (stand == null) return;
        if (stand.isDead()) return;

        stand.remove();
    }

    public void remove(RegistrationHologramHolder holder) {
        if (holder == null) return;
        for (ArmorStand stand : holder.getLines()) remove(stand);
        holder.clear();
    }
}