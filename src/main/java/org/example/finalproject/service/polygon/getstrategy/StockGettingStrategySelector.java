package org.example.finalproject.service.polygon.getstrategy;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.finalproject.dto.stock.DateRangeDto;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.service.DatePreparationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockGettingStrategySelector {

    private final static double PERCENTAGE_OF_EXISTING_STOCK = 0.5; // 50%
    private final static int NUM_OF_RANGES = 3;
    private final List<StockGettingStrategy> stockGettingStrategies;
    private final DatePreparationService datePreparationService;

    public StockGettingStrategy select(@NotNull StockSaveRequestDto stock, List<LocalDate> existingDates) {
        if (existingDates.isEmpty()) {
            return stockGettingStrategies.get(1);
        }

        LocalDate startDate = stock.getStartDate();
        LocalDate endDate = stock.getEndDate();
        List<DateRangeDto> searchingRangeDates = datePreparationService.cutDateRange(startDate, endDate, existingDates);
        long numOfExistingDates = existingDates.size();
        long numOfDates = datePreparationService.getNumOfDays(startDate, endDate);
        int numOfRanges = searchingRangeDates.size();

        if (numOfExistingDates * 1.0 / numOfDates < PERCENTAGE_OF_EXISTING_STOCK &&  numOfRanges >= NUM_OF_RANGES) {
            return stockGettingStrategies.get(1);

        }
        return stockGettingStrategies.get(0);
    }
    
}
