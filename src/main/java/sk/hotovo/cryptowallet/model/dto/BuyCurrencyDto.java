package sk.hotovo.cryptowallet.model.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class BuyCurrencyDto {

    @NotBlank
    private String sourceCurrency;

    @DecimalMin("0.1")
    private Double sourceAmount;

    @NotBlank
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
