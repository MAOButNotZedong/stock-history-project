package org.example.finalproject.service;

import org.example.finalproject.dto.stock.DateRangeDto;
import org.example.finalproject.model.entity.Stock;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatePreparationService {

    public List<DateRangeDto> cutDateRange(LocalDate startDate, LocalDate endDate, List<LocalDate> excludedDates) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        List<DateRangeDto> dateRanges = new ArrayList<>();

        if (excludedDates == null || excludedDates.isEmpty()) {
            dateRanges.add(new DateRangeDto(startDate, endDate));
            return dateRanges;
        }

        LocalDate firstDate = excludedDates.get(0);
        LocalDate lastDate = excludedDates.get(excludedDates.size() - 1);

        if (startDate.isBefore(firstDate)) {
            dateRanges.add(new DateRangeDto(startDate, firstDate.minusDays(1)));
        }

        if (endDate.isAfter(lastDate)) {
            dateRanges.add(new DateRangeDto(lastDate.plusDays(1), endDate));
        }

        for (int i = 0; i < excludedDates.size() - 1; i++) {
            LocalDate date1 = excludedDates.get(i);
            LocalDate date2 = excludedDates.get(i + 1);
            if (date1.isAfter(date2) || date1.isEqual(date2)) {
                throw new IllegalArgumentException("Second date cannot be after first date");
            }
            if (isConsecutiveDays(date1, date2)) {
                continue;
            }
            dateRanges.add(new DateRangeDto(date1.plusDays(1), date2.minusDays(1)));
        }
        return dateRanges;
    }

    public List<LocalDate> toListOfDates(List<Stock> stocks) {
        List<LocalDate> result = new ArrayList<>();
        if (stocks == null || stocks.isEmpty()) {
            return result;
        }
        for (Stock stock : stocks) {
            result.add(stock.getDate());
        }
        return result;
    }

    public long getNumOfDays(LocalDate startDate, LocalDate endDate) {
        long days = Duration.between(
                        LocalDateTime.of(startDate, LocalTime.NOON),
                        LocalDateTime.of(endDate.plusDays(1), LocalTime.NOON)).toDays();
        return days;
    }

    public long getNumOfDaysFromRanges(List<DateRangeDto> dateRanges) {
        long result = 0;
        for (DateRangeDto dateRange : dateRanges) {
            result += getNumOfDays(dateRange.getStartDate(), dateRange.getEndDate());
        }
        return result;
    }

    public boolean isConsecutiveDays(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.isEqual(secondDate.minusDays(1)) || firstDate.isEqual(secondDate.plusDays(1));
    }
}
