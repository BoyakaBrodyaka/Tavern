package me.boyakabrodyaka.commerce.account;

import lombok.Getter;

@Getter
public class Account {

    private double money;

    public Account(double initialBalance) { this.money = Math.max(0.0D, initialBalance); }

    public void setMoney(double value) { this.money = Math.max(0.0D, value); }

    public void addMoney(double value) {
        if (value <= 0.0D) return;
        this.money += value;
    }

    public boolean withdraw(double value) {
        if (value <= 0.0D) return false;
        if (this.money < value) return false;

        this.money -= value;
        return true;
    }
}