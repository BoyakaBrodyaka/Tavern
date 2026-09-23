package me.boyakabrodyaka.operation.dining;

import lombok.Getter;

@Getter
public class DiningArea {

    private static final double CENTER_DIVISOR = 2.0D;

    private final double minX;
    private final double minY;
    private final double minZ;
    private final double maxX;
    private final double maxY;
    private final double maxZ;

    public DiningArea() {
        this.minX = Math.min(DiningCoordinate.TABLE_MIN_X.getValue(), DiningCoordinate.TABLE_MAX_X.getValue());
        this.minY = Math.min(DiningCoordinate.TABLE_MIN_Y.getValue(), DiningCoordinate.TABLE_MAX_Y.getValue());
        this.minZ = Math.min(DiningCoordinate.TABLE_MIN_Z.getValue(), DiningCoordinate.TABLE_MAX_Z.getValue());
        this.maxX = Math.max(DiningCoordinate.TABLE_MIN_X.getValue(), DiningCoordinate.TABLE_MAX_X.getValue());
        this.maxY = Math.max(DiningCoordinate.TABLE_MIN_Y.getValue(), DiningCoordinate.TABLE_MAX_Y.getValue());
        this.maxZ = Math.max(DiningCoordinate.TABLE_MIN_Z.getValue(), DiningCoordinate.TABLE_MAX_Z.getValue());
    }

    public double getCenterX() {
        return (this.minX + this.maxX) / CENTER_DIVISOR;
    }

    public double getCenterY() {
        return (this.minY + this.maxY) / CENTER_DIVISOR;
    }

    public double getCenterZ() {
        return (this.minZ + this.maxZ) / CENTER_DIVISOR;
    }
}