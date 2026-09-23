package me.boyakabrodyaka.operation.dining;

public class DiningZone {

    private final DiningArea area;

    public DiningZone() {
        this.area = new DiningArea();
    }

    public DiningArea getArea() {
        return this.area;
    }

    public double getCenterX() {
        return this.area.getCenterX();
    }

    public double getCenterY() {
        return this.area.getCenterY();
    }

    public double getCenterZ() {
        return this.area.getCenterZ();
    }
}