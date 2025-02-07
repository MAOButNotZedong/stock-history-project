package org.example.finalproject.dao;

import org.example.finalproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> getUserByEmail(String email);
}
