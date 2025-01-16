package com.muvo.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muvo.springapp.model.PasswordResetToken;

public interface PasswordResetRepo extends JpaRepository<PasswordResetToken, Long> {
    public Optional<PasswordResetToken> findByToken(String token);
}
