package org.example.finalproject.model.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.example.finalproject.service.TimestampToLocalDateDeserializer;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "stocks",
        uniqueConstraints = {@UniqueConstraint(name = Stock.UNIQUE_CONSTRAINT_NAME, columnNames = {"ticker_id", "date"})})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Stock {

    public static final String UNIQUE_CONSTRAINT_NAME = "stocks_ticker_id_and_date";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stocks_id_seq")
    @SequenceGenerator(name = "stocks_id_seq", allocationSize = 30)
    @JsonIgnore
    private Integer id;

    @JsonAlias({"t", "from"})
    @JsonDeserialize(using = TimestampToLocalDateDeserializer.class)
    private LocalDate date;


    @JsonAlias({"o", "open"})
    @Schema(example = "120.00")
    private BigDecimal open;

    @JsonAlias({"c", "close"})
    @Schema(example = "121.00")

    private BigDecimal close;

    @JsonAlias({"h", "high"})
    @Schema(example = "120.99")
    private BigDecimal high;

    @JsonAlias({"l", "low"})
    @Schema(example = "123.99")
    private BigDecimal low;

    @OneToMany(mappedBy = "stock", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @ToString.Exclude
    private Set<UserStock> userStocks = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private Ticker ticker;

    public void addUser(User user) {
        userStocks.add(new UserStock(user, this));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Stock stock = (Stock) o;
        return getDate() != null  && Objects.equals(getDate(), stock.getDate())
                && ticker != null &&  Objects.equals(ticker.getTickerSymbol(), stock.getTicker().getTickerSymbol());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}