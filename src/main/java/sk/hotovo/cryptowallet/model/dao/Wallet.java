package sk.hotovo.cryptowallet.model.dao;

import java.util.UUID;

public class Wallet {

    private String id;
    private String name;
    private Double balance;

    public Wallet() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + "\nBALANCE: " + getBalance() + "\nNAME: " + getName();
    }
}
