package org.example.finalproject.service.stock;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.NewAndExistingStocksPair;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcludeService {
    private final GetStockService getStockService;

    public NewAndExistingStocksPair <List<Stock>, List<Stock>> excludeExistingStocks(StockSaveRequestDto stock, @NotNull List<Stock> stocks) {
        List<Stock> existingStocks = getStockService.getExistingStocks(stock);
        if (existingStocks != null && !existingStocks.isEmpty()) {
            stocks.removeAll(existingStocks);
        }
        return new NewAndExistingStocksPair<>(stocks, existingStocks);
    }
}
