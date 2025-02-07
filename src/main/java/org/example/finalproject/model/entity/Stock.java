package org.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stocks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stocks_id_seq")
    @SequenceGenerator(name = "stocks_id_seq", allocationSize = 10)
    private int id;

    @Transient
    @JsonAlias({"symbol"})
    @JsonIgnore
    private String stockTicker;

    @JsonAlias({"from"})
    private LocalDate date;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;

    @OneToMany(mappedBy = "stock")
    @JsonIgnore
    private List<UserStock> userStocks = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private Ticker ticker;
}