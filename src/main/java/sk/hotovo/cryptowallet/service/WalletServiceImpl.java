package sk.hotovo.cryptowallet.service;

import org.springframework.stereotype.Service;
import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.repository.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void save(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public Wallet findById(String id) {
        return walletRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        walletRepository.delete(id);
    }
}
