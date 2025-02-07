package org.example.finalproject.service;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.stock.StockDateRangeDto;
import org.example.finalproject.dto.stock.StockInDatesDto;
import org.example.finalproject.model.entity.Stock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IntegrationService {
//    https://api.polygon.io/v1/open-close/AAPL/2025-01-09?adjusted=true&apiKey=ke7_ZhBrTEjPRNSJJ_8rRGBNOypJNrvk
//    @Value()
    private String token = "ke7_ZhBrTEjPRNSJJ_8rRGBNOypJNrvk";
    private static final String URL_OPEN_CLOSE = "https://api.polygon.io/v1/open-close";
    private static final String URL_TICKER_DETAILS = "https://api.polygon.io/v3/reference/tickers";

    private final RestTemplate restTemplate;

    public ResponseEntity<Stock> getStockByDate(String ticker, String date) {
        String requestURL = String.format(URL_OPEN_CLOSE + "/%s/%s?adjusted=true", ticker, date);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
       return restTemplate.exchange(requestURL, HttpMethod.GET, httpEntity, Stock.class);
    }

    public Boolean isAvailableTicker(String ticker) {
        String requestURL = String.format(URL_TICKER_DETAILS + "/%s?date=%s", ticker, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> exchange = restTemplate.exchange(requestURL, HttpMethod.GET, httpEntity, Void.class);
        if (exchange.getStatusCode().is2xxSuccessful()) {
            return true;
        }
        return false;
    }

    public List<Stock> getStockForDaysRange(StockDateRangeDto savingDto) {
        List<Stock> stockDateHistories = new ArrayList<>();
        for (LocalDate date = savingDto.getStartDate(); date.isBefore(savingDto.getEndDate().plusDays(1)); date = date.plusDays(1)) {
            ResponseEntity<Stock> stockByDate = getStockByDate(savingDto.getTicker(), date.format(DateTimeFormatter.ISO_LOCAL_DATE));
            if (stockByDate.getStatusCode().is2xxSuccessful()) {
                stockDateHistories.add(stockByDate.getBody());
            }
        }
        return stockDateHistories;
    }

    public List<Stock> getStockInDates(StockInDatesDto stockInDatesDto) {
        List<Stock> stockDateHistories = new ArrayList<>();
        for (LocalDate date : stockInDatesDto.getDates()) {
            ResponseEntity<Stock> stockByDate = getStockByDate(stockInDatesDto.getTicker(), date.format(DateTimeFormatter.ISO_LOCAL_DATE));
            if (stockByDate.getStatusCode().is2xxSuccessful()) {
                stockDateHistories.add(stockByDate.getBody());
            }
        }
        return stockDateHistories;
    }

}
