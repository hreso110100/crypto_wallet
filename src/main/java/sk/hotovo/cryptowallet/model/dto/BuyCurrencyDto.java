package sk.hotovo.cryptowallet.model.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class BuyCurrencyDto {

    @NotBlank
    @ApiModelProperty(example = "USD")
    private String sourceCurrency;

    @DecimalMin("0.1")
    @ApiModelProperty(example = "1000")
    private Double sourceAmount;

    @NotBlank
    @ApiModelProperty(example = "BTC")
    private String destinationCurrency;

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public Double getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(Double sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(String destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }
}
