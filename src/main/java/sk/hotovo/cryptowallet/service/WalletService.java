package sk.hotovo.cryptowallet.service;

import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

public interface WalletService {

    boolean save(Wallet wallet);

    void update(Wallet wallet);

    Wallet findByName(String name);

    Wallet findByCurrency(CurrencyEnum currency);

    boolean delete(CurrencyEnum currency);
}
