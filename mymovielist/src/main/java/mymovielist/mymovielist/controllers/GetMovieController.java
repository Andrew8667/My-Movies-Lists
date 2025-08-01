package mymovielist.mymovielist.controllers;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

/**
 * Controller used for mappings related to retrieving movies from OMDb api
 * @author Andrew Gee
 */
@RestController
@RequestMapping("/getMovie")
public class GetMovieController {
    @Autowired
    private GetMovieService getMovieService;

    /**
     * Obtains an array containing the details for the searched for movie
     * @param title to search for
     * @return array containing the details for the searched movie
     */
    @GetMapping("/{title}")
    public ResponseEntity<String[]> getMovieDetails(@PathVariable String title){
        try{
            return getMovieService.getMovie(title);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
