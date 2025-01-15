package com.muvo.springapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muvo.springapp.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);

    public Optional<List<User>> findByUserRole(String role);
}