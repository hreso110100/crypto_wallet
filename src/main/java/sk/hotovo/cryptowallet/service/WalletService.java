package sk.hotovo.cryptowallet.service;

import sk.hotovo.cryptowallet.model.dao.Wallet;

public interface WalletService {

    void save(Wallet wallet);

    Wallet findById(String id);

    void delete(String id);
}
