package org.example.finalproject.service.stock.strategy;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.service.stock.AddToStockService;
import org.example.finalproject.service.stock.GetStockService;
import org.example.finalproject.service.stock.SerializableSaveService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SavingNewAndExistingStockStrategy {
    void saveAll(StockSaveRequestDto stock, List<Stock> newStocks);
}
