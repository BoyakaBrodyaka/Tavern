package me.boyakabrodyaka.operation.order;

import me.boyakabrodyaka.operation.npc.registration.NpcRegistration;

public class OrderTypeResolver {

    public OrderType resolve(NpcRegistration npc) {
        if (npc == null) return null;

        String type = npc.getType();
        if (type == null) return null;

        for (OrderType orderType : OrderType.values()) {
            if (!orderType.getKey().equalsIgnoreCase(type)) continue;
            return orderType;
        }

        return null;
    }
}