package me.boyakabrodyaka.operation.npc.registration;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.npc.registration.name.NpcNameRegistry;
import me.boyakabrodyaka.operation.npc.registration.skin.NpcSkin;
import me.boyakabrodyaka.operation.npc.registration.skin.NpcSkinRegistry;
import me.boyakabrodyaka.operation.npc.registration.type.NpcType;
import me.boyakabrodyaka.operation.npc.registration.type.NpcTypeRegistry;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class NpcRegistrationFactory {

    private static final int INITIAL_ENTITY_ID = 400000;

    private final AtomicInteger counter = new AtomicInteger(INITIAL_ENTITY_ID);
    private final NpcTypeRegistry typeRegistry;
    private final NpcNameRegistry nameRegistry;
    private final NpcSkinRegistry skinRegistry;

    public NpcRegistration create(String key, World world, Location target) {
        int entityId = this.counter.getAndIncrement();
        UUID uuid = UUID.randomUUID();

        NpcType type = this.typeRegistry.getRandomType();
        String name = this.nameRegistry.getRandomName(type.getNames());
        NpcSkin skin = this.skinRegistry.getRandomSkin(type.getSkins());

        Location spawn = new Location(
                world,
                NpcRegistrationCoordinate.SPAWN_X.getValue(),
                NpcRegistrationCoordinate.SPAWN_Y.getValue(),
                NpcRegistrationCoordinate.SPAWN_Z.getValue()
        );

        NpcRegistration npc = new NpcRegistration(key, name, uuid, entityId, world, spawn, target, skin);
        npc.setType(type.getKey());

        return npc;
    }
}