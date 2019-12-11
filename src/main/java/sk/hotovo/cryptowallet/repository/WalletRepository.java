package sk.hotovo.cryptowallet.repository;

import org.springframework.stereotype.Repository;
import sk.hotovo.cryptowallet.model.dao.Portfolio;
import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

@Repository
public class WalletRepository {

    private Portfolio portfolio;

    public WalletRepository() {
        portfolio = new Portfolio("David");
        mockPortfolio();
    }

    /**
     * This method adds some wallets at start of the app.
     */
    private void mockPortfolio() {
        Wallet wallet1 = new Wallet("test1", CurrencyEnum.EUR, 1_000d);
        Wallet wallet2 = new Wallet("test2", CurrencyEnum.USD, 100_000d);
        Wallet wallet3 = new Wallet("test3", CurrencyEnum.BTC, 0d);

        portfolio.getWallets().put(wallet1.getCurrency(), wallet1);
        portfolio.getWallets().put(wallet2.getCurrency(), wallet2);
        portfolio.getWallets().put(wallet3.getCurrency(), wallet3);
    }

    /**
     * This method saves wallet to portfolio. Save will be successful only if portfolio does not contains wallet with
     * given currency.
     *
     * @param wallet Wallet object to save
     * @return true if save was successful, false otherwise
     */
    public boolean save(Wallet wallet) {
        if (!portfolio.getWallets().containsKey(wallet.getCurrency())) {
            portfolio.getWallets().put(wallet.getCurrency(), wallet);

            return true;
        }
        return false;
    }

    public void update(Wallet wallet) {
        portfolio.getWallets().put(wallet.getCurrency(), wallet);
    }

    /**
     * This method finds wallet by its name.
     *
     * @param name Name of the wallet
     * @return Wallet object if exists, null otherwise
     */
    public Wallet findByName(String name) {
        for (Wallet storedWallet : portfolio.getWallets().values()) {
            if (storedWallet.getName().equals(name)) {
                return storedWallet;
            }
        }
        return null;
    }

    /**
     * This method finds wallet by its currency.
     *
     * @param currency Currency of the wallet
     * @return Wallet object if exists, null otherwise
     */
    public Wallet findByCurrency(CurrencyEnum currency) {
        if (portfolio.getWallets().containsKey(currency)) {
            return portfolio.getWallets().get(currency);
        }
        return null;
    }

    /**
     * This method deletes wallet only if it is empty and was not already deleted.
     *
     * @param currency Currency of the wallet
     * @return true if deleted, false otherwise
     */
    public boolean delete(CurrencyEnum currency) {
        Wallet wallet = findByCurrency(currency);

        if (wallet != null && wallet.getBalance() == 0) {
            portfolio.getWallets().remove(currency);

            return true;
        }
        return false;
    }

}
