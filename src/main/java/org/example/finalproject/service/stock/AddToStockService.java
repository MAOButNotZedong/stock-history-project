package org.example.finalproject.service.stock;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.finalproject.dao.StockDao;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.model.entity.Ticker;
import org.example.finalproject.model.entity.User;
import org.example.finalproject.service.user.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final static int NUM_OF_RETRIES = 5;

    private final UserService userService;
    private final StockDao stockDao;
    private final TickerService tickerService;
    private final SerializableSaveService serializableSaveService;

    public List<Stock> excludeExistingStocks(StockSaveRequestDto stock, @NotNull List<Stock> stocks) {
        List<Stock> existingStocks = getExistingStocks(stock);
        if (existingStocks != null && !existingStocks.isEmpty()) {
            stocks.removeAll(existingStocks);
        }
        return stocks;
    }

    public void saveStocks(StockSaveRequestDto stock, List<Stock> stocks, boolean isExcluded) {
        User currentUser = userService.getCurrentUser();
        List<Stock> stocks1 = stocks;
        if (!isExcluded) {
            stocks1 = excludeExistingStocks(stock, stocks);
        }
        if (stocks != null && !stocks.isEmpty()) {
            for (Stock savingStock : stocks) {
                savingStock.addUser(currentUser);
            }
            if (isExcluded) {
                stockDao.saveAll(stocks1);
            } else {
                serializableSaveService.saveAllStock(stocks1);
            }
        }
    }

    public List<Stock> addTickerToStocks(String tickerSymbol, List<Stock> stocks) {
        Ticker ticker = tickerService.saveAndGet(tickerSymbol);
        if (stocks != null && !stocks.isEmpty()) {
            for (Stock stock : stocks) {
                stock.setTicker(ticker);
            }
        }
        return stocks;
    }

    public List<Stock> getExistingStocks(StockSaveRequestDto stock) {
        String tickerSymbol = stock.getTickerSymbol();
        List<Stock> existingStocks = new ArrayList<>();
        Optional<Ticker> existingTicker = tickerService.getTicker(tickerSymbol);
        if (existingTicker.isPresent()) {
            existingStocks = stockDao.findStockDates(tickerSymbol, stock.getStartDate(), stock.getEndDate());
        }
        return existingStocks;
    }

    public List<Stock> getStockHistoryForCurrentUser(String tickerSymbol) {
        User currentUser = userService.getCurrentUser();
        List<Stock> stockHistory = stockDao.getStockHistory(tickerSymbol, currentUser.getId());
        return stockHistory;
    }


}
