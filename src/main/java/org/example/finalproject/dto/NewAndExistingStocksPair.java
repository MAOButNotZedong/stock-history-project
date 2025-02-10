package org.example.finalproject.dto;

public record NewAndExistingStocksPair<N, E>(N newStocks, E existingStocks) {
    public NewAndExistingStocksPair(N newStocks, E existingStocks)  {
        this.newStocks = newStocks;
        this.existingStocks = existingStocks;
    }
}