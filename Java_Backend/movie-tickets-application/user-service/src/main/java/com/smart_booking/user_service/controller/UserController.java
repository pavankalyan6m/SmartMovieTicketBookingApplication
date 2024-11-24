package com.smart_booking.user_service.controller;

import com.smart_booking.user_service.model.User;
import com.smart_booking.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register User: You can set isAdmin to true if you want to create an admin from the request.
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // For example, you could set a flag here to register as an admin based on input
        if (user.getUsername().equals("admin")) {
            user.setAdmin(true);  // You could check the condition and decide to make this user an admin
        }

        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Registration failed: " + e.getMessage());
        }
    }

    // Login User: Check if the user exists and is authenticated
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        // Authenticate the user (check username and password)
        boolean isAuthenticated = userService.authenticateUser(user);

        if (isAuthenticated) {
            // Check if user is admin and return a different message
            boolean isAdmin = user.isAdmin();  // Using the isAdmin field to check the role
            if (isAdmin) {
                return ResponseEntity.ok("Admin login successful!");
            } else {
                return ResponseEntity.ok("User login successful!");
            }
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
