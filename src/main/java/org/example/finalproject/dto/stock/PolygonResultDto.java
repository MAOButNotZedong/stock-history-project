package org.example.finalproject.dto.stock;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ResultDto {
    @JsonAlias("ticker")
    String tickerSymbol;
    String status;
    
}
