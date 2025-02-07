package org.example.finalproject.service.stock.strategy;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dao.StockRepository;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.service.stock.AddToStockService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingExistingStockStrategy {

    private final StockRepository stockRepository;
    private final AddToStockService addToStockService;
    
    public void saveAll(List<Stock> existingStocks) {
        if (existingStocks != null && !existingStocks.isEmpty()) {
            List<Stock> stocks1 = addToStockService.addUserToStocks(existingStocks);
            stockRepository.saveAll(stocks1);
        }
    }
}
