package org.example.finalproject.service.stock;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dao.TickerRepository;
import org.example.finalproject.model.entity.Ticker;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TickerService {

    private final TickerRepository tickerRepository;

    public Optional<Ticker> getTicker(String ticker) {
        return tickerRepository.findByTickerSymbol(ticker);
    }

    public void save(Ticker ticker) {
        tickerRepository.save(ticker);
    }

    public Ticker saveAndGet(String tickerSymbol) {
        Optional<Ticker> tickerFromDb = getTicker(tickerSymbol);
        if (tickerFromDb.isPresent()) {
            return tickerFromDb.get();
        } else {
            Ticker newTicker = new Ticker(tickerSymbol);
            save(newTicker);
            return newTicker;
        }
    }
}
