package org.example.finalproject.service.stock.strategy;

import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;

import java.util.List;

public interface SavingNewAndExistingStockStrategy {
    void saveAll(StockSaveRequestDto stock, List<Stock> newStocks);
}
