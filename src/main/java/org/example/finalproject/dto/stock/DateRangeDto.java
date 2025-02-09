package org.example.finalproject.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DateRangeDto {
    private LocalDate startDate;
    private LocalDate endDate;
}
