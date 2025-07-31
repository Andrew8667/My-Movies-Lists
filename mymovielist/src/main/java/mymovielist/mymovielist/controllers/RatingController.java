package mymovielist.mymovielist.controllers;

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
    @PostMapping("/add")
    public ResponseEntity<String> addRating(@RequestBody RatingRequest rating, @RequestHeader("Authorization") String authHeader){
        return ratingService.addRating(rating,authHeader);
    }
}
