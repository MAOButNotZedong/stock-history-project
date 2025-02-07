package org.example.finalproject.service.stock.strategy;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.service.stock.AddToStockService;
import org.example.finalproject.service.stock.GetStockService;
import org.example.finalproject.service.stock.SerializableSaveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingNewAndExistingStockStrategy {

    private final AddToStockService addToStockService;
    private final SerializableSaveService serializableSaveService;
    private final GetStockService getStockService;
    private final SavingExistingStockStrategy savingExistingStockStrategy;

    public void saveAll(StockSaveRequestDto stock, List<Stock> newStocks, List<Stock> existingStocks) {
        boolean isChanged = false;

        if(newStocks != null && !newStocks.isEmpty()) {
            List<Stock> newStocks1 = addToStockService.addTickerToStocks(stock.getTickerSymbol(), newStocks);
            List<Stock> newStocks2 = addToStockService.addUserToStocks(newStocks1);
            isChanged = serializableSaveService.excludeAndSaveAllStock(stock, newStocks2);
        }
        List<Stock> existingStocks1 = existingStocks;
        if (isChanged) {
            existingStocks1 = getStockService.getExistingStocks(stock);
        }
        savingExistingStockStrategy.saveAll(existingStocks1);
    }
}
