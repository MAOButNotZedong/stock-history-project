package org.example.finalproject.service.stock;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.model.entity.Ticker;
import org.example.finalproject.model.entity.User;
import org.example.finalproject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddToStockService {
    private final UserService userService;
    private final TickerService tickerService;

    public List<Stock> addUserToStocks (List<Stock> stocks) {
        User currentUser = userService.getCurrentUser();
        if (stocks != null) {
            for (Stock savingStock : stocks) {
                savingStock.addUser(currentUser);
            }
        }
        return stocks;
    }

    public List<Stock> addTickerToStocks(String tickerSymbol, List<Stock> stocks) {
        Ticker ticker = tickerService.saveAndGet(tickerSymbol);
        if (stocks != null) {
            for (Stock stock : stocks) {
                stock.setTicker(ticker);
            }
        }
        return stocks;
    }
}
