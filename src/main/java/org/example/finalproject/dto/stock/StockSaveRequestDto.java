package org.example.finalproject.dto.stock;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalproject.constants.JsonConstants;
import org.example.finalproject.constants.ValidationConstants;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dto для запроса на сохрание акции")
public class StockSaveRequestDto {

    @NotBlank
    @Size(min = ValidationConstants.MIN,
            max = ValidationConstants.TICKER_SYMBOL_MAX_LENGTH)
    @JsonAlias(JsonConstants.TICKER)
    @Schema(
            description = "Тикер акции",
            example = "AAAA",
            requiredMode = Schema.RequiredMode.REQUIRED,
            requiredProperties = "not blank"
    )
    private String tickerSymbol;

    @NotNull
    @Schema(
            description = "Дата начала диапазона (включительно)",
            example = "2025-01-02",
            requiredMode = Schema.RequiredMode.REQUIRED,
            pattern = "YYYY-MM-DD",
            requiredProperties = "startDate <= endDate"
    )
    private LocalDate startDate;

    @NotNull
    @Schema(
            description = "Дата конца диапазона (включительно)",
            example = "2025-01-02",
            requiredMode = Schema.RequiredMode.REQUIRED,
            pattern = "YYYY-MM-DD",
            requiredProperties = "startDate <= endDate"
    )
    private LocalDate endDate;

    @Schema(hidden = true)
    @AssertFalse(message = ValidationConstants.START_DATE_GREATER_THAN_END_DATE)
    public boolean isStartAfterEndDate() {
        return startDate != null && endDate != null && startDate.isAfter(endDate);
    }
}
