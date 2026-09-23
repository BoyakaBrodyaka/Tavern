package me.boyakabrodyaka.commerce.account;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class AccountManager {

    private final AccountFactory factory;
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    public Account get(String key) { return this.accounts.computeIfAbsent(key, k -> this.factory.create()); }
    public boolean contains(String key) { return this.accounts.containsKey(key); }
    public void remove(String key) {
        this.accounts.remove(key);
    }
    public void clear() {
        this.accounts.clear();
    }
}