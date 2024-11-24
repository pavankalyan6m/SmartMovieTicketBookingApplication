package com.smart_booking.user_service.service;

import com.smart_booking.user_service.model.User;
import com.smart_booking.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register a new user after validating username and email uniqueness
    public User registerUser(User user) {
        // Check if user with the same username or email already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Authenticate user by comparing passwords
    public boolean authenticateUser(User user) {
        // Find the user by username
        Optional<User> foundUserOptional = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));

        // If user is not found, return false
        if (foundUserOptional.isEmpty()) {
            log.warn("User not found: " + user.getUsername());
            return false;
        }

        // Get the user from Optional
        User foundUser = foundUserOptional.get();

        // Debugging: Log details
        log.info("Found user: " + foundUser.getUsername());
        log.info("Provided password: " + user.getPassword());
        log.info("Stored password: " + foundUser.getPassword());

        // Compare provided password with stored hashed password using BCrypt
        if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            log.info("Authentication successful for user: " + user.getUsername());
            return true; // Password matches
        }

        log.warn("Password does not match for user: " + user.getUsername());
        return false; // Password doesn't match
    }
}
