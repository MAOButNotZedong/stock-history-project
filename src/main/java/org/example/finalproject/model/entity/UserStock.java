package org.example.finalproject.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_stock")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserStock {
    @EmbeddedId
    private UserStockId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stockId")
    private Stock stock;
}
