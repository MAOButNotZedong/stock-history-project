package org.example.finalproject.service.polygon.getstrategy;

import org.example.finalproject.dto.stock.PolygonResultDto;
import org.example.finalproject.dto.stock.StockSaveRequestDto;

import java.time.LocalDate;
import java.util.List;

public interface StockGettingStrategy {
    
    PolygonResultDto getStock(StockSaveRequestDto stock, List<LocalDate> existingDates);

    boolean isExcluded();
}
