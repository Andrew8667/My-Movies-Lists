package mymovielist.mymovielist.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service class to handle the business logic that process the requests involving retrieving movies from OMDb api
 * @author Andrew Gee
 */
@Service
public class GetMovieService {
    @Value("${movieapi.key}")
    private String APIKEY;

    /**
     * Obtains an array containing the details for the searched for movie
     * @param movieName to search for
     * @return array containing the details for the searched movie
     * @throws JsonMappingException related to converting to json node
     * @throws JsonProcessingException  related to converting to json node
     */
    public ResponseEntity<String[]> getMovie(String movieName) throws JsonMappingException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .getForEntity(String.format("http://www.omdbapi.com/?apikey=%s&t=%s", APIKEY,movieName), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        String[] movieDetails = new String[8];
        movieDetails[0]=jsonNode.get("Title").toString().replaceAll("\"", "");
        movieDetails[1]=jsonNode.get("Year").toString().replaceAll("\"", "");
        movieDetails[2]=jsonNode.get("Rated").toString().replaceAll("\"", "");
        movieDetails[3]=jsonNode.get("Runtime").toString().replaceAll("\"", "");
        movieDetails[4]=jsonNode.get("Genre").toString().replaceAll("\"", "");
        movieDetails[5]=jsonNode.get("Director").toString().replaceAll("\"", "");
        movieDetails[6]=jsonNode.get("Plot").toString().replaceAll("\"", "");
        movieDetails[7]=jsonNode.get("Poster").toString().replaceAll("\"", "");
        return ResponseEntity.ok(movieDetails);
    }
}
