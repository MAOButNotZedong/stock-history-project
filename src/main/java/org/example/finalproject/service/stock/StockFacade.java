package org.example.finalproject.service.stock;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.stock.HistoryResponseDto;
import org.example.finalproject.dto.stock.PolygonResultDto;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.service.DatePreparationService;
import org.example.finalproject.service.TickerValidationService;
import org.example.finalproject.service.polygon.getstrategy.StockGettingStrategy;
import org.example.finalproject.service.polygon.getstrategy.StockGettingStrategySelector;
import org.example.finalproject.service.stock.strategy.SavingNewAndExistingStockStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockFacade {

    private final TickerValidationService tickerValidationService;
    private final StockGettingStrategySelector selector;
    private final DatePreparationService datePreparationService;
    private final GetStockService getStockService;
    private final SavingExistingStockService savingExistingStockService;
    private final SavingNewAndExistingStockStrategy savingNewAndExistingStockStrategy;

    public void saveStock(@NotNull StockSaveRequestDto stock) {
        String tickerSymbol = stock.getTickerSymbol();
        tickerValidationService.validate(tickerSymbol);
        List<Stock> existingStocks = getStockService.getExistingStocks(stock);
        List<LocalDate> existingDates = datePreparationService.toListOfDates(existingStocks);
        StockGettingStrategy strategy = selector.select(stock, existingDates);
        PolygonResultDto polygonResultDto = strategy.getStock(stock, existingDates);
        if (polygonResultDto != null) {
            List<Stock> newStocks = polygonResultDto.getStocks();
            savingNewAndExistingStockStrategy.saveAll(stock, newStocks);
        } else {
            savingExistingStockService.saveAll(existingStocks);
        }
    }

    public HistoryResponseDto getStockHistory(String tickerSymbol) {
        List<Stock> stockHistory = getStockService.getStockHistoryForCurrentUser(tickerSymbol);
        return new HistoryResponseDto(tickerSymbol, stockHistory);
    }
}
