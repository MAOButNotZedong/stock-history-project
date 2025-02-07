package org.example.finalproject.service.stock.strategy;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dao.StockDao;
import org.example.finalproject.dto.MutatedList;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.service.stock.AddToStockService;
import org.example.finalproject.service.stock.GetStockService;
import org.example.finalproject.service.stock.SerializableSaveService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Order(2)
@Service
@RequiredArgsConstructor
public class SavingNewAndExistingStockStrategy {

    private final AddToStockService addToStockService;
    private final SerializableSaveService serializableSaveService;
    private final GetStockService getStockService;
    private final SavingExistingStockStrategy savingExistingStockStrategy;

    public void save(StockSaveRequestDto stock, List<Stock> existingStocks, List<Stock> newStocks) {
        List<Stock> stocks1 = addToStockService.addTickerToStocks(stock.getTickerSymbol(), newStocks);
        List<Stock> stocks2 = addToStockService.addUserToStocks(stocks1);
        boolean isChanged = serializableSaveService.excludeAndSaveAllStock(stock, stocks2);
        
        List<Stock> existingStocks1 = existingStocks;
        if (isChanged) {
            existingStocks1 = getStockService.getExistingStocks(stock);
        }
        if (existingStocks1 != null && existingStocks1.isEmpty()) {
            savingExistingStockStrategy.save(stock, existingStocks1);
        }
    }
}
