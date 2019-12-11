package sk.hotovo.cryptowallet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sk.hotovo.cryptowallet.model.dao.Wallet;
import sk.hotovo.cryptowallet.model.dto.CryptoCurrencyPriceDto;
import sk.hotovo.cryptowallet.model.enums.CurrencyEnum;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Value("${cryptocompare.url}")
    private String cryptoCompareURL;

    @Value("${cryptocompare.key}")
    private String cryptoCompareKey;

    @Value("${coinapi.url}")
    private String coinapiURL;

    @Value("${coinapi.key}")
    private String coinapiKey;

    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ExchangeServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
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

    @Override
    public ArrayList<CryptoCurrencyPriceDto> getPrices(Integer pageNumber, Integer pageSize) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-CoinAPI-Key", cryptoCompareKey);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<String> jsonResponse = restTemplate
                    .exchange(coinapiURL, HttpMethod.GET, entity, String.class);

            if (jsonResponse.getBody() != null) {

                ArrayList<CryptoCurrencyPriceDto> prices = new ArrayList<>(
                        Arrays.asList(objectMapper.readValue(jsonResponse.getBody(), CryptoCurrencyPriceDto[].class)));

                return doPagination(pageNumber, pageSize, prices);
            } else {
                return null;
            }
        } catch (RestClientException | JsonProcessingException e) {
            return null;
        }
    }

    /**
     * This method returns paginated version of original list of data.
     *
     * @param pageNumber Specific number of page where pagination will start.
     * @param pageSize Number of data to be returned
     * @param data List of data to be paginated
     * @return Paginated list of original data
     */
    private <T> ArrayList<T> doPagination(Integer pageNumber, Integer pageSize, ArrayList<T> data) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);

        int start = Math.toIntExact(paging.getOffset());
        int end = Math.min((start + paging.getPageSize()), data.size());

        try {
            return new ArrayList<>(data.subList(start, end));
        } catch (IllegalArgumentException e) {
            return null;
        }

    }
}
