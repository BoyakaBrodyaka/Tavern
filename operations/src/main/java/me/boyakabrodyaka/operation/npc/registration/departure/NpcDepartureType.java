package me.boyakabrodyaka.operation.npc.registration.departure;

public enum NpcDepartureType {

    QUEUE,
    FOLLOW;

    public long getTimeout() {
        switch (this) {
            case QUEUE: return NpcDepartureCoordinate.QUEUE_TIMEOUT_MS.getLongValue();
            case FOLLOW: return NpcDepartureCoordinate.FOLLOW_TIMEOUT_MS.getLongValue();
            default: return 0L;
        }
    }
}