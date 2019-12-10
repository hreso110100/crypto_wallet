package sk.hotovo.cryptowallet.model.dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

public class WalletCreateDto {

    @NotBlank
    private String name;

    private CurrencyEnum currency;

    @Range
    private Double balance;

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
