package sk.hotovo.cryptowallet.controller;

import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.model.dto.BuyCurrencyDto;
import sk.hotovo.cryptowallet.model.dto.WalletOutputDto;
import sk.hotovo.cryptowallet.model.response.Response;
import sk.hotovo.cryptowallet.model.response.ResponseCode;
import sk.hotovo.cryptowallet.service.ExchangeServiceImpl;
import sk.hotovo.cryptowallet.service.WalletServiceImpl;

@RestController("/exchange")
public class ExchangeController {

    private WalletServiceImpl walletService;
    private ExchangeServiceImpl exchangeService;
    private final ModelMapper modelMapper;

    public ExchangeController(WalletServiceImpl walletService, ExchangeServiceImpl exchangeService,
            ModelMapper modelMapper) {
        this.walletService = walletService;
        this.exchangeService = exchangeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity buyCurrency(@RequestBody @Valid BuyCurrencyDto buyCurrencyDto) {
        Wallet sourceWallet = walletService.findByCurrency(buyCurrencyDto.getSourceCurrency());
        Wallet destinationWallet = walletService.findByCurrency(buyCurrencyDto.getDestinationCurrency());

        Double exchangeRate = exchangeService
                .getExchangeRate(buyCurrencyDto.getSourceCurrency(), buyCurrencyDto.getDestinationCurrency());

        if (exchangeRate == null) {
            return new ResponseEntity<>(new Response(ResponseCode.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (exchangeService
                .transferCurrency(sourceWallet, destinationWallet, buyCurrencyDto.getSourceAmount(), exchangeRate)) {
            return new ResponseEntity<>(
                    new Response<>(ResponseCode.SUCCESSFUL, modelMapper.map(sourceWallet, WalletOutputDto.class)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(ResponseCode.ERROR), HttpStatus.BAD_REQUEST);
        }
    }


}
