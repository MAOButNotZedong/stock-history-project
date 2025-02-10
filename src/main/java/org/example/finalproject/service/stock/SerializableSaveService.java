package org.example.finalproject.service.stock;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dao.StockRepository;
import org.example.finalproject.dto.NewAndExistingStocksPair;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.model.entity.Stock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SerializableSaveService {
    private final StockRepository stockRepository;
    private final ExcludeService excludeService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public NewAndExistingStocksPair<List<Stock>, List<Stock>> excludeAndSaveAllStock(StockSaveRequestDto stock, List<Stock> stocks) {
        NewAndExistingStocksPair<List<Stock>, List<Stock>> stocks1 = excludeService.excludeExistingStocks(stock, stocks);
        stockRepository.saveAll(stocks1.newStocks());
        return stocks1;
    }
}
