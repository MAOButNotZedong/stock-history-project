package org.example.finalproject.service.stock.strategy;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dao.StockNativeQueryRepository;
import org.example.finalproject.dto.NewAndExistingStocksPair;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.service.stock.AddToStockService;
import org.example.finalproject.service.stock.ExcludeService;
import org.example.finalproject.service.stock.GetStockService;
import org.example.finalproject.service.stock.SavingExistingStockService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class ConcurrentSavingNewAndExistingStockStrategy implements SavingNewAndExistingStockStrategy {

    private final AddToStockService addToStockService;
    private final StockNativeQueryRepository stockNativeQueryRepository;
    private final ExcludeService excludeService;
    private final GetStockService getStockService;
    private final SavingExistingStockService savingExistingStockService;

    public void saveAll(StockSaveRequestDto stock, List<Stock> newStocks) {
        List<Stock> newStocks1 = addToStockService.addTickerToStocks(stock.getTickerSymbol(), newStocks);
        NewAndExistingStocksPair<List<Stock>, List<Stock>> stocksPair = excludeService.excludeExistingStocks(stock, newStocks1);
        List<Stock> newStocks2 = stocksPair.newStocks();
        addToStockService.addUserToStocks(newStocks2);
        stockNativeQueryRepository.saveAll(newStocks2);
        List<Stock> existingStocks = getStockService.getExistingStocks(stock);
        savingExistingStockService.saveAll(existingStocks);
    }
}
