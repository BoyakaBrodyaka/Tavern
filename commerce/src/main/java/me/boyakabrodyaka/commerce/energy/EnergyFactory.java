package me.boyakabrodyaka.commerce.energy;

public class EnergyFactory {

    private static final int DEFAULT_MAX = 100;
    private static final int DEFAULT_SPRINT_COST = 10;
    private static final int DEFAULT_REGEN_AMOUNT = 5;

    public EnergyModule createDefault() {
        EnergySettings settings = new EnergySettings(DEFAULT_MAX, DEFAULT_SPRINT_COST, DEFAULT_REGEN_AMOUNT);
        return new EnergyModule(settings);
    }

    public EnergyModule create(EnergySettings settings) {
        return new EnergyModule(settings);
    }
}