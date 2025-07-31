package mymovielist.mymovielist.controllers;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import mymovielist.mymovielist.services.GetMovieService;

@RestController
@RequestMapping("/movie")
public class GetMovieController {
    @Autowired
    private GetMovieService getMovieService;
    @GetMapping("/{title}")
    public ResponseEntity<String[]> getMovieDetails(@PathVariable String title){
        try {
            String[] movieDetails = new String[8];
            JsonNode jsonNode = getMovieService.getMovie(title);
            movieDetails[0]=jsonNode.get("Title").toString().replaceAll("\"", "");
            movieDetails[1]=jsonNode.get("Year").toString().replaceAll("\"", "");
            movieDetails[2]=jsonNode.get("Rated").toString().replaceAll("\"", "");
            movieDetails[3]=jsonNode.get("Runtime").toString().replaceAll("\"", "");
            movieDetails[4]=jsonNode.get("Genre").toString().replaceAll("\"", "");
            movieDetails[5]=jsonNode.get("Director").toString().replaceAll("\"", "");
            movieDetails[6]=jsonNode.get("Plot").toString().replaceAll("\"", "");
            movieDetails[7]=jsonNode.get("Poster").toString().replaceAll("\"", "");
            return ResponseEntity.ok(movieDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
