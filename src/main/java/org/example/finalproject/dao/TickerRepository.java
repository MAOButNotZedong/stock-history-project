package org.example.finalproject.dao;

import org.example.finalproject.model.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TickerDao extends JpaRepository<Ticker, Integer> {

    Optional<Ticker> findByTickerSymbol(String tickerSymbol);

}
