package mymovielist.mymovielist.controllers;

import mymovielist.mymovielist.dto.MovieReviewDTO;
import mymovielist.mymovielist.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for processing GET, POST, PUT, and DELETE requests involving movie entity
 * @author Andrew Gee
 */
@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    /**
     * Adds movie, its review, and its categories to db
     * @param authHeader contains the jwt token
     * @param movieReviewDTO contains the movie info, rating info, and categories the movie belongs to
     * @return success string if movie is successfully added, error otherwise
     */
    @PostMapping("/add")
    public ResponseEntity<String> addMovie(@RequestHeader("Authorization") String authHeader, @RequestBody MovieReviewDTO movieReviewDTO){
        return movieService.addMovie(authHeader, movieReviewDTO);
    }

    /**
     * Deletes a movie from a category
     * @param movieId identification number of the movie we want to remove from category
     * @param categoryId identification number of the category we want to remove movie from
     * @param authHeader contains the jwt token
     * @return response entity containing status of the deletion
     */
    @DeleteMapping("/delete/{movieId}/{categoryId}")
    public ResponseEntity<String> deleteMovieFromCategory(@PathVariable long movieId,@PathVariable long categoryId,@RequestHeader("Authorization") String authHeader){
        return movieService.deleteMovieFromCategory(movieId,categoryId,authHeader);
    }

    /**
     * Used to find the list of category ids for the categories a movie is in for a user
     * @param movie we want the categories for
     * @param authHeader contains the jwt token
     * @return a list of category ids of the categories that have the movie
     */
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
