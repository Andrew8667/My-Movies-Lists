package mymovielist.mymovielist.controllers;

import mymovielist.mymovielist.dto.AddMovieReviewDTO;
import mymovielist.mymovielist.dto.CategoryMovieRatingDTO;
import mymovielist.mymovielist.dto.MovieCategoryRequest;
import mymovielist.mymovielist.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @PostMapping("/add")
    public ResponseEntity<String> addMovie(@RequestHeader("Authorization") String authHeader, @RequestBody AddMovieReviewDTO addMovieReviewDTO){
        return movieService.addMovie(authHeader,addMovieReviewDTO);
    }
}
