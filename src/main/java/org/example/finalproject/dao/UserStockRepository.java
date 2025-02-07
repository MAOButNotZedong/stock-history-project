package org.example.finalproject.dao;


import org.example.finalproject.model.entity.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStockRepository extends JpaRepository<UserStock, Long> {
}
