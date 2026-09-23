package me.boyakabrodyaka.hud.menu.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameMenuLayout {

    private final int size;
    private final int center;
    private final int spacing;

    public int[] computeSlots(int count) {
        if (count <= 0) return new int[0];

        int[] slots = new int[count];
        int half = count / 2;

        for (int i = 0; i < count; i++) slots[i] = this.center + (i - half) * this.spacing;

        return slots;
    }
}