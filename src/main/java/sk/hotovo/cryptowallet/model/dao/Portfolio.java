package sk.hotovo.cryptowallet.model.dao;

import java.util.HashMap;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

public class Portfolio {

    private String owner;
    private HashMap<CurrencyEnum, Wallet> wallets;

    public Portfolio(String owner) {
        this.owner = owner;
        this.wallets = new HashMap<>();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public HashMap<CurrencyEnum, Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(
            HashMap<CurrencyEnum, Wallet> wallets) {
        this.wallets = wallets;
    }
}
