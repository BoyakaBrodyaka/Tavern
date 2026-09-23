package me.boyakabrodyaka.commerce.account;

public class AccountFactory {

    private final double defaultBalance;

    public AccountFactory(double defaultBalance) { this.defaultBalance = Math.max(0.0D, defaultBalance); }

    public Account create() { return new Account(this.defaultBalance); }
}