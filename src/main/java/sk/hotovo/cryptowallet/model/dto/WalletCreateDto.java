package sk.hotovo.cryptowallet.model.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class WalletCreateDto {

    @NotBlank
    @ApiModelProperty(example = "myWallet")
    private String name;

    @NotBlank
    @ApiModelProperty(example = "ETH")
    private String currency;

    @Range
    @ApiModelProperty(example = "52000")
    private Double balance;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
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
