package me.boyakabrodyaka.commerce.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountSettings {

    DEFAULT_BALANCE(100.0D);

    private final double value;
}