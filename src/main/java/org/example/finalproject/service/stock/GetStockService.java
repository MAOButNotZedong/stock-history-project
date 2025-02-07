package org.example.finalproject.service.stock;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dao.StockRepository;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.model.entity.Ticker;
import org.example.finalproject.model.entity.User;
import org.example.finalproject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetStockService {
    public final TickerService tickerService;
    public final UserService userService;
    public final StockRepository stockRepository;

    public List<Stock> getExistingStocks(StockSaveRequestDto stock) {
        String tickerSymbol = stock.getTickerSymbol();
        List<Stock> existingStocks = new ArrayList<>();
        Optional<Ticker> existingTicker = tickerService.getTicker(tickerSymbol);
        if (existingTicker.isPresent()) {
            existingStocks = stockRepository.findStockDates(tickerSymbol, stock.getStartDate(), stock.getEndDate());
        }
        return existingStocks;
    }

    public List<Stock> getStockHistoryForCurrentUser(String tickerSymbol) {
        User currentUser = userService.getCurrentUser();
        List<Stock> stockHistory = stockRepository.getStockHistory(tickerSymbol, currentUser.getId());
        return stockHistory;
    }

}
