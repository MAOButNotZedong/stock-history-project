package org.example.finalproject.service.polygon.getstrategy;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.stock.DateRangeDto;
import org.example.finalproject.dto.stock.PolygonResultDto;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.service.DatePreparationService;
import org.example.finalproject.service.polygon.IntegrationService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Order(1)
public class DefaultStockGettingStrategy implements StockGettingStrategy {

    private final DatePreparationService datePreparationService;
    private final IntegrationService integrationService;

    @Override
    public PolygonResultDto getStock(StockSaveRequestDto stock, List<LocalDate> existingDates) {
        List<DateRangeDto> searchingRangeDates = datePreparationService.cutDateRange(stock.getStartDate(), stock.getEndDate(), existingDates);
        PolygonResultDto fullResult = null;
        for (DateRangeDto dateRange : searchingRangeDates) {
            System.out.println("dateRange: " + dateRange);
            PolygonResultDto result = integrationService.getStockByDateRange(stock.getTickerSymbol(), dateRange);
            if (result != null && result.getStocks() !=null && !result.getStocks().isEmpty()) {
                if (fullResult == null) {
                    fullResult = result;
                } else {
                    fullResult.getStocks().addAll(result.getStocks());
                }
            }
        }
        return fullResult;
    }

    @Override
    public boolean isExcluded() {
        return true;
    }

}
