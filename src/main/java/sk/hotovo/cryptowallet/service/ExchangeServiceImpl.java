package sk.hotovo.cryptowallet.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Value("${cryptocompare.url}")
    private String cryptoCompareURL;

    @Value("${cryptocompare.key}")
    private String cryptoCompareKey;

    private RestTemplate restTemplate;

    public ExchangeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Double getExchangeRate(CurrencyEnum source, CurrencyEnum destination) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("authorization", "Apikey " + cryptoCompareKey);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        String URL = cryptoCompareURL + "?fsyms=" + source + "&tsyms=" + destination;

        try {
            ResponseEntity<String> jsonResponse = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

            return Double.parseDouble(
                    jsonObject.getJSONObject(String.valueOf(source)).get(String.valueOf(destination)).toString());
        } catch (RestClientException e) {
            return null;
        }
    }

    @Override
    public boolean transferCurrency(Wallet source, Wallet destination, Double amount, Double exchangeRate) {

        if (source == null || destination == null || amount > source.getBalance()) {
            return false;
        }

        source.setBalance(source.getBalance() - amount);
        Double sumToTransfer = amount * exchangeRate;

        destination.setBalance(destination.getBalance() + sumToTransfer);

        return true;
    }
}
