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
}
