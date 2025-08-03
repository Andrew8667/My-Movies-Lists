package mymovielist.mymovielist.controllers;

import mymovielist.mymovielist.dto.MovieReviewDTO;
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
    public ResponseEntity<String> addMovie(@RequestHeader("Authorization") String authHeader, @RequestBody MovieReviewDTO movieReviewDTO){
        return movieService.addMovie(authHeader, movieReviewDTO);
    }

    @DeleteMapping("/delete/{movieId}/{categoryId}")
    public ResponseEntity<String> deleteMovieFromCategory(@PathVariable long movieId,@PathVariable long categoryId,@RequestHeader("Authorization") String authHeader){
        return movieService.deleteMovieFromCategory(movieId,categoryId,authHeader);
    }
}
