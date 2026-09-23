package me.boyakabrodyaka.operation.npc.registration.departure;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;
import org.bukkit.Location;

@RequiredArgsConstructor
public class NpcDepartureManager {

    private final NpcDepartureTimer timer;

    public void startQueueTracking(NpcRegistration npc) {
        this.timer.trackQueue(npc);
    }

    public void startFollowTracking(NpcRegistration npc) {
        this.timer.trackFollow(npc);
    }

    public void stopTracking(NpcRegistration npc) {
        this.timer.untrack(npc);
    }

    public boolean shouldLeave(NpcRegistration npc) {
        return this.timer.shouldLeave(npc);
    }

    public Location getDepartureLocation(NpcRegistration npc) {
        return new NpcDeparture(npc.getWorld()).toLocation();
    }

    public Location getDisappearLocation(NpcRegistration npc) {
        return new NpcDeparture(npc.getWorld()).disappearLocation(npc.getWorld());
    }

    public void clear() {
        this.timer.clear();
    }
}