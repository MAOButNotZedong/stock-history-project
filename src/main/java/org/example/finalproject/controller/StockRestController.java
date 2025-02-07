package org.example.finalproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.finalproject.constants.EndPointPaths;
import org.example.finalproject.controller.docs.StockRestControllerDocumentation;
import org.example.finalproject.dto.stock.HistoryResponseDto;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.service.stock.StockFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPointPaths.API_STOCK)
@RequiredArgsConstructor
public class StockRestController implements StockRestControllerDocumentation {

    private final StockFacade stockFacade;

    @GetMapping(EndPointPaths.HISTORY_TICKER)
    public ResponseEntity<HistoryResponseDto> getStockHistory(@PathVariable(EndPointPaths.TICKER) String ticker) {
        return ResponseEntity.ok(stockFacade.getStockHistory(ticker));
    }

    @PostMapping(EndPointPaths.SAVE)
    public ResponseEntity<Void> saveStock(@Valid @RequestBody StockSaveRequestDto stock) {
        stockFacade.saveStock(stock);
        return ResponseEntity.ok().build();
    }

}
