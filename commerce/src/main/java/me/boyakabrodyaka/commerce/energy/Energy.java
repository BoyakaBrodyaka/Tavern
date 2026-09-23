package me.boyakabrodyaka.commerce.energy;

import lombok.Getter;

@Getter
public class Energy {

    private static final int SPRINT_THRESHOLD = 10;

    private final int sprintCost;
    private final int regenAmount;

    private int max;
    private int value;

    public Energy(int max, int sprintCost, int regenAmount) {
        this.max = Math.max(1, max);
        this.sprintCost = Math.max(0, sprintCost);
        this.regenAmount = Math.max(0, regenAmount);
        this.value = this.max;
    }

    public void setValue(int value) { this.value = clamp(value); }

    public void setMax(int max) {
        this.max = Math.max(1, max);
        this.value = clamp(this.value);
    }

    public void consume(int amount) {
        if (amount <= 0) return;
        setValue(this.value - amount);
    }

    public void restore(int amount) {
        if (amount <= 0) return;
        setValue(this.value + amount);
    }

    public boolean canSprint() { return this.value >= SPRINT_THRESHOLD; }

    public boolean isEmpty() { return this.value <= 0; }

    public boolean isFull() { return this.value >= this.max; }

    private int clamp(int raw) {
        if (raw < 0) return 0;
        if (raw > this.max) return this.max;
        return raw;
    }
}