package com.fintrack.fintrack.repository;

import com.fintrack.fintrack.dto.login.RefreshTokenRequest;
import com.fintrack.fintrack.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long>
{
    Optional<RefreshToken> findByToken(String token);



}
