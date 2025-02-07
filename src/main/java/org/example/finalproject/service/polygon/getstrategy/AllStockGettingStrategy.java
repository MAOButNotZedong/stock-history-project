package org.example.finalproject.service.stock.strategy;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.stock.DateRangeDto;
import org.example.finalproject.dto.stock.PolygonResultDto;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.service.polygon.IntegrationService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Order(2)
public class AllStockGettingStrategy implements StockGettingStrategy{
    private final IntegrationService integrationService;

    @Override
    public PolygonResultDto getStock(StockSaveRequestDto stock, List<LocalDate> existingDates) {
        DateRangeDto dateRangeDto = new DateRangeDto(stock.getStartDate(), stock.getEndDate());
        return integrationService.getStockByDateRange(stock.getTickerSymbol(), dateRangeDto);
    }

    @Override
    public boolean isExcluded() {
        return false;
    }
}
