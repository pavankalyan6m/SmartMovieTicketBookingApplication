package com.smart_booking.movies_service.controller;

import com.smart_booking.movies_service.model.Movie;
import com.smart_booking.movies_service.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Endpoint to add a new movie
    @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    // Endpoint to update an existing movie
    @PutMapping("/update/{id}")
    public Movie updateMovie(@PathVariable String id, @RequestBody Movie movie) {
        return movieService.updateMovie(id, movie);
    }

    // Endpoint to get a movie by title
    @GetMapping("/title/{title}")
    public Movie getMovieByTitle(@PathVariable String title) {
        return movieService.getMovieByTitle(title);
    }

    // Endpoint to get all movies
    @GetMapping("/all")
    public Iterable<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/search-by-synopsis")
    public List<Movie> getMoviesBySynopsisKeywords(@RequestParam String keywords) {
        // Split the comma-separated keywords into an array
        String[] keywordArray = keywords.split(",");

        // Call a method to find movies based on these keywords
        List<Movie> resultMovies = movieService.findMoviesBySynopsisKeywords(keywordArray);
        return resultMovies;
    }


}
