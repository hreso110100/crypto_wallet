package sk.hotovo.cryptowallet.model.dao;

import java.util.HashMap;

public class Portfolio {

    private String owner;
    private HashMap<String, Wallet> wallets;

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

    public HashMap<String, Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(
            HashMap<String, Wallet> wallets) {
        this.wallets = wallets;
    }
}
