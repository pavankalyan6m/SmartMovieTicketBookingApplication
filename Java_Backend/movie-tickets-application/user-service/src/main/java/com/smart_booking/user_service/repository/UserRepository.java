package com.smart_booking.user_service.repository;

import com.smart_booking.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Custom query method to find user by username
    User findByUsername(String username);

    // Custom query method to find user by email (if needed)
    Optional<User> findByEmail(String email);

    // Custom query method to find user by ID (for completeness, though MongoRepository provides this by default)
    Optional<User> findById(String id);
}
