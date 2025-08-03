package mymovielist.mymovielist.controllers;

import mymovielist.mymovielist.dto.RatingDTO;
import mymovielist.mymovielist.dto.RatingRequest;
import mymovielist.mymovielist.entities.Rating;
import mymovielist.mymovielist.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PutMapping("/update/{movieId}")
    public ResponseEntity<String> updateRating(@PathVariable long movieId, @RequestHeader("Authorization") String authHeader, @RequestBody RatingDTO ratingDTO){
        return ratingService.updateRating(movieId,authHeader,ratingDTO);
    }

    /**
     * Finds the number of stars and description of rating for a movie and user
     * Used to populate the movie rating details in the home page
     * @param authHeader contains jwt token
     * @param movie the name of the movie we want to find review for
     * @return the stars and description of the rating that user put for that movie, null otherwise
     */
    @GetMapping("/get/{movie}")
    public ResponseEntity<RatingDTO> getRatingDetails(@RequestHeader("Authorization") String authHeader, @PathVariable String movie){
        return ratingService.getRatingDetails(authHeader,movie);
    }

    /**
     * Given a user and movie title, method removes that user's rating for that movie
     * It also removes the categories of the user from that movie
     * @param movie id of movie we want to remove
     * @param authHeader contains jwt token
     * @return String containing message about status of deletion
     */
    @DeleteMapping("/delete/{movie}")
    public ResponseEntity<String> deleteRating(@PathVariable long movie, @RequestHeader("Authorization") String authHeader){
        return ratingService.deleteRating(movie,authHeader);
    }
}
