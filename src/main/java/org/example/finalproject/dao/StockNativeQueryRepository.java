package org.example.finalproject.dao;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.model.entity.Stock;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockNativeQueryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StockRepository stockRepository;

    public static final String QUERY = """
            INSERT INTO stocks (id, ticker_id, date, high, low, open, close)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT ON CONSTRAINT %s DO NOTHING
            RETURNING id;""";

    public int[] saveAndGetExistingOnConflict(List<Stock> stocks) {
        String query = String.format(QUERY, Stock.UNIQUE_CONSTRAINT_NAME);
        return jdbcTemplate.batchUpdate(query,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Stock stock = stocks.get(i);
                        ps.setInt(1, stock.getId());
                        ps.setInt(2, stock.getTicker().getId());
                        ps.setDate(3, java.sql.Date.valueOf(stock.getDate()));
                        ps.setBigDecimal(4, stock.getHigh());
                        ps.setBigDecimal(5, stock.getLow());
                        ps.setBigDecimal(6, stock.getOpen());
                        ps.setBigDecimal(7, stock.getClose());
                    }
                    @Override
                    public int getBatchSize() {
                        return stocks.size();
                    }
                });
    }

    public List<Integer> saveAll (List<Stock> stocks) {
        int startIndex = 0;
        int endIndex = Math.min(stocks.size(), Stock.ALLOCATION_SIZE);
        List<Integer> savedIds = new ArrayList<>();

        while (true) {
            List<Stock> stocksCopy = stocks.subList(startIndex, endIndex);
            int lastSequenceValue = stockRepository.getLastSequenceValue() + 1;
            for (Stock stock : stocksCopy) {
                stock.setId(lastSequenceValue);
                lastSequenceValue++;
            }

            int[] existing = saveAndGetExistingOnConflict(stocksCopy);
            if (existing != null && existing.length != 0) {
                List<Integer> list = Arrays.stream(existing).boxed().toList();
                savedIds.addAll(list);
            }

            startIndex = endIndex;
            endIndex = Math.min(stocks.size(), endIndex + Stock.ALLOCATION_SIZE);

            if (startIndex >= stocks.size()) {
                return savedIds;
            }
        }
    }
}
