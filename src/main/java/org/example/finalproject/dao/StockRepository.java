package org.example.finalproject.dao;

import org.example.finalproject.model.entity.Stock;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface StockDao extends JpaRepository<Stock, Integer> {

    @Query("SELECT s FROM Stock s WHERE s.ticker.tickerSymbol = ?1  AND (s.date BETWEEN ?2 AND ?3) ORDER BY s.date ASC")
    List<Stock> findStockDates (String ticker, LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT s FROM Stock s JOIN FETCH s.userStocks us WHERE s.ticker.tickerSymbol = ?1  AND us.user.id= ?2 ORDER BY s.date ASC")
    List<Stock> getStockHistory(String ticker, int userId);
}
