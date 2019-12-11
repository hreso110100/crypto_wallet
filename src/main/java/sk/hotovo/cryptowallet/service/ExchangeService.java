package sk.hotovo.cryptowallet.service;

import java.util.ArrayList;
import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.model.dto.CryptoCurrencyPriceDto;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

public interface ExchangeService {

    /**
     * This method gets exchange rate between two currencies from CryptoCompare web.
     *
     * @param source Source currency
     * @param destination Destination currency
     * @return Exchange rate between specified currencies
     */
    Double getExchangeRate(CurrencyEnum source, CurrencyEnum destination);

    /**
     * This method transfers amount of money between two wallets.
     *
     * @param source Source wallet
     * @param destination Destination wallet
     * @param amount Amount of source currency which will be transferred
     * @param exchangeRate Exchange rate between specified currencies
     * @return true if operation succeeded, false otherwise
     */
    boolean transferCurrency(Wallet source, Wallet destination, Double amount, Double exchangeRate);

    /**
     *
     * @return List of currencies and its prices in USD.
     */
    ArrayList<CryptoCurrencyPriceDto> getPrices(Integer pageNumber, Integer pageSize);

}
