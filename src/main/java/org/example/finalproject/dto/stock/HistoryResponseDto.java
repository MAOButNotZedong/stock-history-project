package org.example.finalproject.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.finalproject.constants.JsonConstants;
import org.example.finalproject.model.entity.Stock;

import java.util.List;

/**
 * <pre>
 * This class is main entity for response ticker history:
 * {
 *   "ticker": "A",
 *   "data": [
 *   {{@link Stock}},
 *   ]
 * }</pre>
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({JsonConstants.TICKER, JsonConstants.DATA })
@Schema(description = "История запросов по акции")
public class HistoryResponseDto {
    @JsonProperty(JsonConstants.TICKER)
    @Schema(example = "AAAA")
    private String tickerSymbol;
    @JsonProperty(JsonConstants.DATA)
    private List<Stock> stocks;
}
