package sk.hotovo.cryptowallet.service;

import org.springframework.stereotype.Service;
import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.repository.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public boolean save(Wallet wallet) {
        if (wallet.getBalance() == null) {
            wallet.setBalance(0d);
        }

        return walletRepository.save(wallet);
    }

    @Override
    public void update(Wallet wallet) {
        walletRepository.update(wallet);
    }

    @Override
    public Wallet findByName(String name) {
        return walletRepository.findByName(name);
    }

    @Override
    public Wallet findByCurrency(String currency) {
        return walletRepository.findByCurrency(currency);
    }

    @Override
    public boolean delete(String currency) {
       return walletRepository.delete(currency);
    }
}
