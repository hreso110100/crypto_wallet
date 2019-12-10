package sk.hotovo.cryptowallet.controller;

import java.util.HashMap;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.model.dto.WalletCreateDto;
import sk.hotovo.cryptowallet.model.dto.WalletOutputDto;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;
import sk.hotovo.cryptowallet.model.response.Response;
import sk.hotovo.cryptowallet.model.response.ResponseCode;
import sk.hotovo.cryptowallet.service.WalletServiceImpl;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private WalletServiceImpl walletService;
    private ModelMapper modelMapper;

    public WalletController(WalletServiceImpl walletService, ModelMapper objectMapper) {
        this.walletService = walletService;
        this.modelMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity createWallet(@RequestBody @Valid WalletCreateDto walletCreateDto) {
        Wallet wallet = modelMapper.map(walletCreateDto, Wallet.class);

        if (walletService.save(wallet)) {
            return new ResponseEntity<>(new Response(ResponseCode.SUCCESSFUL), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new Response(ResponseCode.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity findWalletByName(@RequestParam("name") String walletName) {
        Wallet wallet = walletService.findByName(walletName);

        if (wallet == null) {
            return new ResponseEntity<>(new Response(ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                new Response<>(ResponseCode.SUCCESSFUL, modelMapper.map(wallet, WalletOutputDto.class)),
                HttpStatus.OK);
    }

    @PatchMapping("/{walletCurrency}")
    public ResponseEntity updateWallet(@PathVariable CurrencyEnum walletCurrency,
            @RequestBody HashMap<String, String> payload) {
        Wallet wallet = walletService.findByCurrency(walletCurrency);

        if (wallet == null) {
            return new ResponseEntity<>(new Response(ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        wallet.setName(payload.get("name"));
        walletService.update(wallet);

        return new ResponseEntity<>(new Response<>(ResponseCode.SUCCESSFUL), HttpStatus.OK);
    }

    @PatchMapping("/topup/{walletCurrency}")
    public ResponseEntity topUpWallet(@PathVariable CurrencyEnum walletCurrency,
            @RequestBody HashMap<String, Double> payload) {

        Wallet wallet = walletService.findByCurrency(walletCurrency);

        if (wallet == null) {
            return new ResponseEntity<>(new Response(ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        wallet.setBalance(wallet.getBalance() + payload.get("amount"));
        walletService.update(wallet);

        return new ResponseEntity<>(new Response<>(ResponseCode.SUCCESSFUL), HttpStatus.OK);
    }

    @DeleteMapping("/{walletCurrency}")
    public ResponseEntity deleteWallet(@PathVariable CurrencyEnum walletCurrency) {

        if (walletService.delete(walletCurrency)) {
            return new ResponseEntity<>(new Response<>(ResponseCode.SUCCESSFUL), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response<>(ResponseCode.ERROR), HttpStatus.BAD_REQUEST);
    }

}
