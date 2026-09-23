package me.boyakabrodyaka.operation.npc.registration.departure;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;

@RequiredArgsConstructor
public class NpcDepartureTimer {

    private final NpcDepartureRegistry registry;

    public void trackQueue(NpcRegistration npc) {
        this.registry.startQueue(npc.getKey());
    }

    public void trackFollow(NpcRegistration npc) {
        this.registry.startFollow(npc.getKey());
    }

    public void untrack(NpcRegistration npc) {
        this.registry.reset(npc.getKey());
    }

    public boolean shouldLeave(NpcRegistration npc) {
        if (npc.isSitting()) return false;
        if (npc.isFollowing()) return this.registry.expiredFollow(npc.getKey(), NpcDepartureType.FOLLOW.getTimeout());

        return this.registry.expiredQueue(npc.getKey(), NpcDepartureType.QUEUE.getTimeout());
    }

    public void clear() {
        this.registry.clear();
    }
}