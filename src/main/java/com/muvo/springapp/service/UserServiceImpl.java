package com.muvo.springapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muvo.springapp.exception.DuplicateRecordException;
import com.muvo.springapp.model.PasswordResetToken;
import com.muvo.springapp.model.User;
import com.muvo.springapp.repository.PasswordResetRepo;
import com.muvo.springapp.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordResetRepo tokenRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Value("${BACKEND_URL}")
    private String backendUrl;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(int userId) {
        return userRepo.findById(userId).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public User createUser(User user) {
        User existingUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null) {
            throw new DuplicateRecordException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User updateUser(int userId, User userDetails) {
        Optional<User> getUser = userRepo.findById(userId);
        if (getUser.isPresent()) {
            User user = getUser.get();
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setUserRole(userDetails.getUserRole());
            return userRepo.save(user);
        }
        return null;
    }

    @Override
    public void deleteUser(int userId) {
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
        }
    }

    @Override
    public List<User> getUserByRole(String userRole) {
        return userRepo.findByUserRole(userRole).orElse(List.of());
    }

    @Override
    public String createPasswordResetToken(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpirationTime(LocalDateTime.now().plusMinutes(30));
        tokenRepo.save(resetToken);

        // Send the token to the user's email
        String resetLink = backendUrl + "/api/reset-password?token=" + token;
        emailService.sendEmail(user.getEmail(), "Password Reset Request",
                "Click the link to reset your password: " + resetLink);
        return token;
    }

    @Override
    public User validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = tokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (resetToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }
        return resetToken.getUser();
    }

}