package sk.hotovo.cryptowallet.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.model.dto.BuyCurrencyDto;
import sk.hotovo.cryptowallet.model.dto.CurrencyPriceDto;
import sk.hotovo.cryptowallet.model.dto.WalletOutputDto;
import sk.hotovo.cryptowallet.model.response.Response;
import sk.hotovo.cryptowallet.model.response.ResponseCode;
import sk.hotovo.cryptowallet.service.ExchangeServiceImpl;
import sk.hotovo.cryptowallet.service.WalletServiceImpl;

@RestController
@RequestMapping("/market")
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

    @ApiOperation(value = "Gets a list of available currencies with their values in USD.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieving was successful.")
    })
    @GetMapping("/prices")
    public ResponseEntity getAllPrices(
            @ApiParam(value = "Number of page where to start gathering data.") @RequestParam(defaultValue = "0") Integer pageNumber,
            @ApiParam(value = "Number of results to be returned from given offset.") @RequestParam(defaultValue = "10") Integer pageSize) {

        ArrayList<CurrencyPriceDto> prices = exchangeService.getPrices(pageNumber, pageSize);

        return new ResponseEntity<>(
                new Response<>(ResponseCode.SUCCESSFUL, prices), HttpStatus.OK);

    }

    @ApiOperation(value = "Buys a specified currency using the specified amount of a specified currency."
            + " The conversion is obtained from the real exchange rate. Obtained currency will be automatically transferred to the wallet with same currency.")
    @ApiResponses(value = {

            @ApiResponse(code = 200, message = "Transaction was successful."),
            @ApiResponse(code = 400, message = "If there are insufficient funds or wallet does not exists."),
            @ApiResponse(code = 503, message = "Cannot obtain exchange rate."),

    })
    @PostMapping("/buy")
    public ResponseEntity buyCurrency(
            @ApiParam(value = "Body should contain source wallet currency, amount of source currency to exchange and destination currency to buy.") @RequestBody @Valid BuyCurrencyDto buyCurrencyDto) {
        Wallet sourceWallet = walletService.findByCurrency(buyCurrencyDto.getSourceCurrency());
        Wallet destinationWallet = walletService.findByCurrency(buyCurrencyDto.getDestinationCurrency());

        Double exchangeRate = exchangeService
                .getExchangeRate(buyCurrencyDto.getSourceCurrency(), buyCurrencyDto.getDestinationCurrency());

        if (exchangeRate == null) {
            return new ResponseEntity<>(new Response(ResponseCode.CANNOT_OBTAIN_EXCHANGE_RATE),
                    HttpStatus.SERVICE_UNAVAILABLE);
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
