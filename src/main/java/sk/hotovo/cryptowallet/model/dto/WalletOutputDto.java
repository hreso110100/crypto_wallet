package sk.hotovo.cryptowallet.model.dto;

import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

public class WalletOutputDto {

    private String name;
    private CurrencyEnum currency;
    private double balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
