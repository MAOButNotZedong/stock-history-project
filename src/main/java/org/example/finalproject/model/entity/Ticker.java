package org.example.finalproject.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;



@Entity
@Table(name = "tickers")
@Data
@NoArgsConstructor
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Ticker(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    @NaturalId(mutable = true)
    @Column(unique = true, length = 30)
    private String tickerSymbol;
}
