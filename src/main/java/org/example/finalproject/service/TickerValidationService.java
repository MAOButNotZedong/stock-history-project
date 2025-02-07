package org.example.finalproject.service.validation;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.exception.TickerNotFoundException;
import org.example.finalproject.service.polygon.IntegrationService;
import org.example.finalproject.service.stock.TickerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TickerValidationService {

    private final TickerService tickerService;
    private final IntegrationService integrationService;

    public void validate(String ticker) {
        if (!(tickerService.getTicker(ticker).isPresent() || integrationService.isAvailableTicker(ticker))) {
            throw new TickerNotFoundException(ticker);
        }
    }
}
