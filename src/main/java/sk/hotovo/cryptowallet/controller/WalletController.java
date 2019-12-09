package sk.hotovo.cryptowallet.controller;

import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.model.dto.WalletDto;
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
    public ResponseEntity createWallet(@RequestBody @Valid WalletDto walletDto) {

        Wallet wallet = modelMapper.map(walletDto, Wallet.class);
        walletService.save(wallet);

        System.out.println(wallet);

        return new ResponseEntity<>(new Response(ResponseCode.SUCCESSFUL), HttpStatus.CREATED);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity findWalletById(@PathVariable String walletId) {
        Wallet wallet = walletService.findById(walletId);

        if (wallet == null) {
            return new ResponseEntity<>(new Response(ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Response<>(ResponseCode.SUCCESSFUL, modelMapper.map(wallet, WalletDto.class)),
                HttpStatus.OK);
    }

}
