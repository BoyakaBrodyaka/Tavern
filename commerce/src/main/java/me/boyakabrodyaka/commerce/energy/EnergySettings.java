package me.boyakabrodyaka.commerce.energy;

import lombok.Getter;

@Getter
public class EnergySettings {

    private final int max;
    private final int sprintCost;
    private final int regenAmount;

    public EnergySettings(int max, int sprintCost, int regenAmount) {
        this.max = Math.max(1, max);
        this.sprintCost = Math.max(0, sprintCost);
        this.regenAmount = Math.max(0, regenAmount);
    }
}