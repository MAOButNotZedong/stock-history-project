package org.example.finalproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStockId {
    @Column(name = "user_id")
    private int userId;
    @Column(name = "stock_id")
    private int stockId;
}
