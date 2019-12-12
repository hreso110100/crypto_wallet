package sk.hotovo.cryptowallet.service;

import sk.hotovo.cryptowallet.model.dao.Wallet;

public interface WalletService {

    boolean save(Wallet wallet);

    void update(Wallet wallet);

    Wallet findByName(String name);

    Wallet findByCurrency(String currency);

    boolean delete(String currency);
}
