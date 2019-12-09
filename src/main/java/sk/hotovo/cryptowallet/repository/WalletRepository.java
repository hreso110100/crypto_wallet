package sk.hotovo.cryptowallet.repository;

import java.util.HashMap;
import org.springframework.stereotype.Repository;
import sk.hotovo.cryptowallet.model.dao.Wallet;

@Repository
public class WalletRepository {

    private HashMap<String, Wallet> wallets;

    public WalletRepository() {
        wallets = new HashMap<>();
    }

    public void save(Wallet wallet) {
        wallets.put(wallet.getId(), wallet);
    }

    public Wallet findById(String id) {
        return wallets.get(id);
    }

    public void delete(String id) {
        wallets.remove(id);
    }

}
