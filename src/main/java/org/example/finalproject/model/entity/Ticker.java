package org.example.finalproject.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 * Ticker class is used to store the symbolic representation of stocks in database.
 * It is a dependent entity of the {@link Stock} class.
 * Using bidirectional association.
 * Ticker is main entity for response body:
 * {
 *   "id": "1",
 *   "ticker": "A",
 *   "data": [
 *   {{@link Stock}},
 *   ]
 * }</pre>
 */
@Entity
@Table(name = "tickers")
@Data
@JsonPropertyOrder({ "id", "ticker", "data" })
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NaturalId(mutable = true)
    @Column(unique = true, length = 20)
    @JsonProperty("ticker")
    private String stockTicker;

    @OneToMany(mappedBy = "ticker")
    @JsonProperty("data")
    private List<Stock> stocks = new ArrayList<>();
}
