package com.smart_booking.movies_service.repo;

import com.smart_booking.movies_service.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {

    // Custom query to find movies where synopsis contains any of the keywords
    @Query("{ 'synopsis': { $regex: ?0, $options: 'i' } }")
    List<Movie> findBySynopsisContainingIgnoreCase(String keywords);

    Optional<Movie> findByTitle(String title);  // Existing method
}
