package sk.hotovo.cryptowallet.service;

import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

public interface ExchangeService {

    Double getExchangeRate(CurrencyEnum source, CurrencyEnum destination);

    boolean transferCurrency(Wallet source, Wallet destination, Double amount, Double exchangeRate);

}
