package mymovielist.mymovielist.controllers;

import mymovielist.mymovielist.dto.RatingDTO;
import mymovielist.mymovielist.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  Controller for processing GET, POST, PUT, and DELETE requests involving rating entity
 * @author Andrew Gee
 */
@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    /**
     * Updates the stars and review of a rating
     * @param movie we want to update rating for
     * @param authHeader contains jwt token
     * @param ratingDTO has the new stars and review
     * @return response entity of the status of the update
     */
    @PutMapping("/update/{movie}")
    public ResponseEntity<String> updateRating(@PathVariable String movie, @RequestHeader("Authorization") String authHeader, @RequestBody RatingDTO ratingDTO){
        return ratingService.updateRating(movie,authHeader,ratingDTO);
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
