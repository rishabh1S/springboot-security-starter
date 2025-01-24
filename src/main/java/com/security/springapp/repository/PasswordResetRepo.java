package com.security.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.springapp.model.PasswordResetToken;

public interface PasswordResetRepo extends JpaRepository<PasswordResetToken, Long> {
    public Optional<PasswordResetToken> findByToken(String token);
}
