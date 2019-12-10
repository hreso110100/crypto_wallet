package sk.hotovo.cryptowallet.model.dto;

import javax.validation.constraints.DecimalMin;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

public class BuyCurrencyDto {

    private CurrencyEnum sourceCurrency;

    @DecimalMin("0.1")
    private Double sourceAmount;

    private CurrencyEnum destinationCurrency;

    public CurrencyEnum getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(CurrencyEnum sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public Double getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(Double sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public CurrencyEnum getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(CurrencyEnum destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }
}
