package org.example.finalproject.service.polygon;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.stock.DateRangeDto;
import org.example.finalproject.dto.stock.PolygonResultDto;
import org.example.finalproject.exception.TickerNotFoundException;
import org.example.finalproject.model.entity.Stock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class IntegrationService {
    @Value("${polygon.token}")
    private String polygonToken;
    private static final String URL_OPEN_CLOSE = "https://api.polygon.io/v1/open-close/%s/%s?adjusted=true";
    private static final String URL_TICKER_DETAILS = "https://api.polygon.io/v3/reference/tickers/%s";
    private static final String URL_TICKER_AGGREGATOR = "https://api.polygon.io/v2/aggs/ticker/%s/range/1/day/%s/%s?adjusted=true&sort=asc";
    private final RestTemplate restTemplate;

    public ResponseEntity<Stock> getStockByDate(String tickerSymbol, String date) {
        String requestURL = String.format(URL_OPEN_CLOSE, tickerSymbol, date);
        return restTemplate.exchange(requestURL, HttpMethod.GET, createPolygonHttpHeader(), Stock.class);
    }

    public boolean isAvailableTicker(String tickerSymbol) {
        String requestURL = String.format(URL_TICKER_DETAILS, tickerSymbol);
        try {
            ResponseEntity<Void> exchange = restTemplate.exchange(requestURL, HttpMethod.GET, createPolygonHttpHeader(), Void.class);
            if (exchange.getStatusCode().is2xxSuccessful()) {
                return true;
            }
        }
        catch (HttpClientErrorException e) {
            throw new TickerNotFoundException(tickerSymbol);
        }

        return false;
    }

    public PolygonResultDto getStockByDateRange(String tickerSymbol, DateRangeDto dateRange) {
        String requestURL = String.format(URL_TICKER_AGGREGATOR, tickerSymbol, dateRange.getStartDate(), dateRange.getEndDate());
        ResponseEntity<PolygonResultDto> responseEntity = restTemplate.exchange(requestURL, HttpMethod.GET, createPolygonHttpHeader(), PolygonResultDto.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        };
        return null;
    }

    public HttpEntity<String> createPolygonHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + polygonToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return httpEntity;
    }

}
