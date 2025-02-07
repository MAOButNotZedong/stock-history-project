package org.example.finalproject.service;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dao.TickerDao;
import org.example.finalproject.model.entity.Ticker;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TickerService {

    private final TickerDao tickerDao;

    public Optional<Ticker> getTicker(String ticker) {
        return tickerDao.findByStockTicker(ticker);
    }

    public Ticker getStockHistory(String ticker) {
        return tickerDao.getStockHistoryByTickerAndUserId(ticker, 1);
    }

    public void save(Ticker ticker) {
        tickerDao.save(ticker);
    }
}
