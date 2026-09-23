package me.boyakabrodyaka.cuisine.grill.recipe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GrillRecipe {

    private final String rawId;
    private final String cookedId;
    private final String burntId;
}