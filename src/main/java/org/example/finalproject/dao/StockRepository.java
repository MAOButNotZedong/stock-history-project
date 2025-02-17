package org.example.finalproject.dao;

import org.example.finalproject.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    String QUERY_GET_NEXT_SEQ_VALUE = "SELECT nextval('" + Stock.STOCKS_ID_SEQ + "')";

    @Query("SELECT s FROM Stock s WHERE s.ticker.tickerSymbol = ?1  AND (s.date BETWEEN ?2 AND ?3) ORDER BY s.date ASC")
    List<Stock> findStockDates (String ticker, LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT s FROM Stock s JOIN FETCH s.userStocks us WHERE s.ticker.tickerSymbol = ?1  AND us.user.id= ?2 ORDER BY s.date ASC")
    List<Stock> getStockHistory(String ticker, int userId);

    @Query(value = QUERY_GET_NEXT_SEQ_VALUE, nativeQuery = true)
    int getNextSequenceValue();
}


