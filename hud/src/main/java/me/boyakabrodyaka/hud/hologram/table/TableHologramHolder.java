package me.boyakabrodyaka.hud.hologram.table;

import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableHologramHolder {

    private final List<ArmorStand> lines = new ArrayList<>();

    public void add(ArmorStand stand) {
        if (stand == null) return;
        this.lines.add(stand);
    }

    public List<ArmorStand> getLines() { return Collections.unmodifiableList(this.lines); }

    public void clear() {
        this.lines.clear();
    }
}