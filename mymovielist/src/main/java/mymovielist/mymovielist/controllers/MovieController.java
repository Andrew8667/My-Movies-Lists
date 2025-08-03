package mymovielist.mymovielist.controllers;

import mymovielist.mymovielist.dto.MovieReviewDTO;
import mymovielist.mymovielist.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getCategories/{movie}")
    public ResponseEntity<List<Long>> getMoviesSelectedCategories(@PathVariable String movie,@RequestHeader("Authorization") String authHeader){
        return movieService.getMoviesSelectedCategories(movie, authHeader);
    }

    /**
     * Updates the categories a movie is in
     * @param selectedCategoryIds the new set of categories that the movie should be in
     * @param movie the movie we are updating
     * @param authHeader contains the jwt token
     * @return status of update
     */
    @PutMapping("/updateCategories/{movie}")
    public ResponseEntity<String> updateSelectedCategories(@RequestBody List<Long> selectedCategoryIds,@PathVariable String movie,@RequestHeader("Authorization") String authHeader){
        return movieService.updateSelectedCategories(selectedCategoryIds, movie, authHeader);
    }
}
