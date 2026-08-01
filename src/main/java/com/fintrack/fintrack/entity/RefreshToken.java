package com.fintrack.fintrack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    @Column(nullable = false, unique = true)
    private String token;

    private LocalDateTime expiryDate;
    @OneToOne
    @JoinColumn(name = "user_id")
    public User user;
}
