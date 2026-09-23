package me.boyakabrodyaka.hud.menu.game.button;

import java.util.ArrayList;
import java.util.List;

public class GameButtonRegistry {

    private final List<GameButton> buttons = new ArrayList<>();

    public void register(GameButton button) {
        if (button == null) return;
        this.buttons.add(button);
    }

    public GameButton getBySlot(int slot) {
        for (GameButton button : this.buttons) if (button.getSlot() == slot) return button;

        return null;
    }

    public List<GameButton> getAll() {
        return this.buttons;
    }

    public void clear() {
        this.buttons.clear();
    }
}