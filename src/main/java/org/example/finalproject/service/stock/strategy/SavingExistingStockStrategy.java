package org.example.finalproject.service.stock.strategy;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dao.StockDao;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.service.stock.AddToStockService;
import org.example.finalproject.service.stock.SerializableSaveService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Order(1)
@Service
@RequiredArgsConstructor
public class SavingExistingStockStrategy {

    private final StockDao stockDao;
    private final AddToStockService addToStockService;
    
    public void save(StockSaveRequestDto stock, List<Stock> existingStocks) {
        List<Stock> stocks1 = addToStockService.addUserToStocks(existingStocks);
        stockDao.saveAll(stocks1);
    }
}
