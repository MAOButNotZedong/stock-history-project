package org.example.finalproject.model.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users",
uniqueConstraints = {@UniqueConstraint(name = User.UNIQUE_CONSTRAINT_NAME, columnNames = {"email"})})
@Nonnull
public class User {

    public static final String UNIQUE_CONSTRAINT_NAME = "users_email_key";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NaturalId
    @Column(unique = true)
    private String email;

    private String username;
    private String password;
    private boolean isDeleted;
    private LocalDate lastLogin;

    @CreatedDate
    @Version
    @Column(updatable = false)
    private Date created;

}
