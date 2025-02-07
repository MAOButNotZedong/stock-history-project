package org.example.finalproject.dto.stock;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalproject.constants.JsonConstants;
import org.example.finalproject.model.entity.Stock;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolygonResultDto {
    @JsonAlias(JsonConstants.TICKER)
    private String tickerSymbol;
    private String status;
    @JsonAlias(JsonConstants.RESULTS)
    private List<Stock> stocks;
}
