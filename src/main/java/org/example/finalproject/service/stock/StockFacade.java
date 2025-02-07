package org.example.finalproject.service.stock;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.finalproject.dao.StockDao;
import org.example.finalproject.dto.stock.HistoryResponseDto;
import org.example.finalproject.dto.stock.PolygonResultDto;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.model.entity.Ticker;
import org.example.finalproject.model.entity.User;
import org.example.finalproject.service.date.DatePreparationService;
import org.example.finalproject.service.polygon.strategy.StockGettingStrategySelector;
import org.example.finalproject.service.user.UserService;
import org.example.finalproject.service.validation.TickerValidationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockFacade {

    private final TickerValidationService tickerValidationService;
    private final StockGettingStrategySelector selector;
    private final TickerService tickerService;
    private final DatePreparationService datePreparationService;
    private final StockDao stockDao;
    private final UserService userService;

    public void saveStock(StockSaveRequestDto stock) {
        String tickerSymbol = stock.getTickerSymbol();
        tickerValidationService.validate(tickerSymbol);

        List<Stock> existingStocks = getExistingStocks(stock);
        List<LocalDate> existingDates = datePreparationService.toListOfDates(existingStocks);
        PolygonResultDto stockFromExApi = selector.selectAndRun(stock, existingDates, true);

    }

    public void saveExistingStocksForUser(List<Stock> stocks, User user) {
        if (stocks != null && !stocks.isEmpty()) {
            for (Stock savingStock : stocks) {
                savingStock.addUser(user);
            }
        }
    }

    public List<Stock> getExistingStocks(StockSaveRequestDto stock) {
        String tickerSymbol = stock.getTickerSymbol();
        List<Stock> existingStocks = new ArrayList<>();
        Optional<Ticker> existingTicker = tickerService.getTicker(tickerSymbol);
        if (existingTicker.isPresent()) {
            existingStocks = stockDao.findStockDates(tickerSymbol, stock.getStartDate(), stock.getEndDate());
        }
        return existingStocks;
    }

    public HistoryResponseDto getStockHistory(String tickerSymbol) {
        User currentUser = userService.getCurrentUser();
        List<Stock> stockHistory = stockDao.getStockHistory(tickerSymbol, currentUser.getId());
        HistoryResponseDto result = new HistoryResponseDto(tickerSymbol, stockHistory);
        return result;
    }
}
