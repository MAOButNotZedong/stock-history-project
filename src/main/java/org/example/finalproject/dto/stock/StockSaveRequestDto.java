package org.example.finalproject.dto.stock;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDateRangeDto {
    @JsonAlias("ticker")
    private String tickerSymbol;
    private LocalDate startDate;
    private LocalDate endDate;
}
