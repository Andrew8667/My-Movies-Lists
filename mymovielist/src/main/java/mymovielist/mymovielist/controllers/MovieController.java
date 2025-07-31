package mymovielist.mymovielist.controllers;

import mymovielist.mymovielist.dto.MovieCategoryRequest;
import mymovielist.mymovielist.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @PostMapping("/add")
    public ResponseEntity<String> addMovieCategory(@RequestBody MovieCategoryRequest movie){
        return movieService.addMovieCategory(movie);
    }
}
