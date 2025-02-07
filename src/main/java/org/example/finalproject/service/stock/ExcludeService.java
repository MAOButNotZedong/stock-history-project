package org.example.finalproject.service.stock;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.MutatedList;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcludeService {
    private final GetStockService getStockService;

    public MutatedList<List<Stock>, Boolean> excludeExistingStocks(StockSaveRequestDto stock, @NotNull List<Stock> stocks) {
        List<Stock> existingStocks = getStockService.getExistingStocks(stock);
        boolean isChanged = false;
        if (existingStocks != null && !existingStocks.isEmpty()) {
            isChanged = stocks.removeAll(existingStocks);
        }
        return new MutatedList<>(stocks, isChanged);
    }
}
