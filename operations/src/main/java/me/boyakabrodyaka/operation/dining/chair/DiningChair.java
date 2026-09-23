package me.boyakabrodyaka.operation.dining.chair;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@Setter
public class DiningChair {

    private final String key;
    private final int seat;
    private final double x;
    private final double y;
    private final double z;

    private String occupantUuid;

    public DiningChair(String key, int seat, double x, double y, double z) {
        this.key = key;
        this.seat = seat;
        this.x = x;
        this.y = y;
        this.z = z;
        this.occupantUuid = null;
    }

    public Location toLocation(World world) {
        return new Location(world, this.x, this.y, this.z);
    }

    public boolean isOccupied() {
        return this.occupantUuid != null;
    }

    public void clearOccupant() {
        this.occupantUuid = null;
    }
}