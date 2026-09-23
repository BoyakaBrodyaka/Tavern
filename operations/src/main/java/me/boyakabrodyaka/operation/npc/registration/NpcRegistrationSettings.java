package me.boyakabrodyaka.operation.npc.registration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NpcRegistrationSettings {

    private final double walkSpeed;
    private final double reachDistance;
    private final double followDistance;
    private final double followSpeed;
}