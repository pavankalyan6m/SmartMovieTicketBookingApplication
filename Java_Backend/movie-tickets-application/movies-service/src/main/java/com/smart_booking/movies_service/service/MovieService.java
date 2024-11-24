package com.smart_booking.movies_service.service;

import com.smart_booking.movies_service.model.Movie;
import com.smart_booking.movies_service.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // Add a new movie
    public Movie addMovie(Movie movie) {
        // Check if the movie already exists
        Optional<Movie> existingMovie = movieRepository.findByTitle(movie.getTitle());
        if (existingMovie.isPresent()) {
            throw new RuntimeException("Movie with this title already exists.");
        }
        return movieRepository.save(movie);
    }

    // Update an existing movie
    public Movie updateMovie(String id, Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (!existingMovie.isPresent()) {
            throw new RuntimeException("Movie not found.");
        }
        movie.setId(id);
        return movieRepository.save(movie);
    }

    // Retrieve a movie by title
    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Movie not found."));
    }

    // List all movies
    public Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> findMoviesBySynopsisKeywords(String[] keywords) {
        // Assuming `movieRepository.findAll()` fetches all movies from the database
        List<Movie> movies = movieRepository.findAll();
        List<Movie> filteredMovies = new ArrayList<>();

        // Loop through the movies and check if any of the keywords are in the synopsis
        for (Movie movie : movies) {
            // Loop through all the keywords
            for (String keyword : keywords) {
                if (movie.getSynopsis().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredMovies.add(movie);  // Add movie if a match is found
                    break;  // No need to check further keywords for this movie
                }
            }
        }

        return filteredMovies;
    }

}
