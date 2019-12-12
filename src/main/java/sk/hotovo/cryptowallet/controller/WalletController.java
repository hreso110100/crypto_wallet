package sk.hotovo.cryptowallet.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Creates wallet of given currency. Starting amount is not required.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Creation of wallet was successful."),
            @ApiResponse(code = 400, message = "Wallet with entered currency already exists."),
    })
    @PostMapping
    public ResponseEntity createWallet(@ApiParam(value = "Wallet to be created.") @RequestBody @Valid WalletCreateDto walletCreateDto) {
        Wallet wallet = modelMapper.map(walletCreateDto, Wallet.class);

        if (walletService.save(wallet)) {
            return new ResponseEntity<>(new Response(ResponseCode.SUCCESSFUL), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new Response(ResponseCode.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Gets detail of specific wallet.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieving detail of specified wallet."),
            @ApiResponse(code = 404, message = "Wallet does not exists."),
    })
    @GetMapping
    public ResponseEntity findWalletByName(
            @ApiParam(value = "Name of wallet.") @RequestParam("name") String walletName) {
        Wallet wallet = walletService.findByName(walletName);

        if (wallet == null) {
            return new ResponseEntity<>(new Response(ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                new Response<>(ResponseCode.SUCCESSFUL, modelMapper.map(wallet, WalletOutputDto.class)),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Updates name of wallet.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful update of wallet's name."),
            @ApiResponse(code = 404, message = "Wallet does not exists."),
    })
    @PatchMapping("/{walletCurrency}")
    public ResponseEntity updateWallet(@ApiParam(value = "Currency of wallet.") @PathVariable String walletCurrency,
            @ApiParam(value = "New name of the wallet. Allowed JSON key is: name") @RequestBody HashMap<String, String> payload) {
        Wallet wallet = walletService.findByCurrency(walletCurrency);

        if (wallet == null) {
            return new ResponseEntity<>(new Response(ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        wallet.setName(payload.get("name"));
        walletService.update(wallet);

        return new ResponseEntity<>(new Response<>(ResponseCode.SUCCESSFUL), HttpStatus.OK);
    }

    @ApiOperation(value = "Top up balance of wallet with given amount.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful update of balance."),
            @ApiResponse(code = 404, message = "Wallet does not exists."),
    })
    @PatchMapping("/topup/{walletCurrency}")
    public ResponseEntity topUpWallet(@ApiParam(value = "Currency of wallet.") @PathVariable String walletCurrency,
            @ApiParam(value = "Amount of money which will be added to the wallet. Allowed JSON key is: amount") @RequestBody HashMap<String, Double> payload) {

        Wallet wallet = walletService.findByCurrency(walletCurrency);

        if (wallet == null) {
            return new ResponseEntity<>(new Response(ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        wallet.setBalance(wallet.getBalance() + payload.get("amount"));
        walletService.update(wallet);

        return new ResponseEntity<>(new Response<>(ResponseCode.SUCCESSFUL), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes specific wallet.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful deletion of wallet."),
            @ApiResponse(code = 400, message = "Wallet does not exists or is not empty."),
    })
    @DeleteMapping("/{walletCurrency}")
    public ResponseEntity deleteWallet(@ApiParam(value = "Currency of wallet.") @PathVariable String walletCurrency) {

        if (walletService.delete(walletCurrency)) {
            return new ResponseEntity<>(new Response<>(ResponseCode.SUCCESSFUL), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response<>(ResponseCode.ERROR), HttpStatus.BAD_REQUEST);
    }

}
