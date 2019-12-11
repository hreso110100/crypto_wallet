package sk.hotovo.cryptowallet.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CryptoCurrencyPriceDto {

    private String name;

    @JsonAlias("price_usd")
    private Double priceUsd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(Double priceUsd) {
        this.priceUsd = priceUsd;
    }
}
