package sk.hotovo.cryptowallet.model.dao;

import java.time.LocalDateTime;
import java.util.UUID;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

public class Wallet {

    private String id;
    private String name;
    private LocalDateTime createdAt;
    private CurrencyEnum currency;
    private Double balance;

    public Wallet() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    public Wallet(String name, CurrencyEnum currency, Double balance) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.name = name;
        this.currency = currency;
        this.balance = balance;
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

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + "\nBALANCE: " + getBalance() + "\nNAME: " + getName() + "\nCURRENCY: "
                + getCurrency() + "\nDATE: " + getCreatedAt();
    }

}
