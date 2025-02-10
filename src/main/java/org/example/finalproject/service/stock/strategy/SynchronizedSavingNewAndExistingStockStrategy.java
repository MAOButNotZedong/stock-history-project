package org.example.finalproject.service.stock.strategy;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.NewAndExistingStocksPair;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.service.stock.AddToStockService;
import org.example.finalproject.service.stock.GetStockService;
import org.example.finalproject.service.stock.SavingExistingStockService;
import org.example.finalproject.service.stock.SerializableSaveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SynchronizedSavingNewAndExistingStockStrategy implements SavingNewAndExistingStockStrategy {

    private final AddToStockService addToStockService;
    private final SerializableSaveService serializableSaveService;
    private final SavingExistingStockService savingExistingStockService;

    public void saveAll(StockSaveRequestDto stock, List<Stock> newStocks) {
        NewAndExistingStocksPair<List<Stock>, List<Stock>> stocksPair = null;

        if(newStocks != null && !newStocks.isEmpty()) {
            List<Stock> newStocks1 = addToStockService.addTickerToStocks(stock.getTickerSymbol(), newStocks);
            List<Stock> newStocks2 = addToStockService.addUserToStocks(newStocks1);
            stocksPair = serializableSaveService.excludeAndSaveAllStock(stock, newStocks2);
        }
        if (stocksPair != null && !stocksPair.existingStocks().isEmpty()) {
            savingExistingStockService.saveAll(stocksPair.existingStocks());
        }
    }
}
