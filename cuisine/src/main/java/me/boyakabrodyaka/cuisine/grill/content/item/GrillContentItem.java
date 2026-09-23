package me.boyakabrodyaka.cuisine.grill.content.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Item;

@Getter
@RequiredArgsConstructor
public class GrillContentItem {

    private final String id;
    private final Item entity;
}