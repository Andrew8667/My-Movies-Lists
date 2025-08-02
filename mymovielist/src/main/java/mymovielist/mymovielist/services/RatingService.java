package mymovielist.mymovielist.services;

import mymovielist.mymovielist.auth.JwtUtil;
import mymovielist.mymovielist.dto.CategoryMovieRatingDTO;
import mymovielist.mymovielist.dto.MovieDTO;
import mymovielist.mymovielist.dto.RatingDTO;
import mymovielist.mymovielist.dto.RatingRequest;
import mymovielist.mymovielist.entities.Category;
import mymovielist.mymovielist.entities.Movie;
import mymovielist.mymovielist.entities.Rating;
import mymovielist.mymovielist.entities.User;
import mymovielist.mymovielist.keys.RatingKey;
import mymovielist.mymovielist.repositories.MovieRepository;
import mymovielist.mymovielist.repositories.RatingRepository;
import mymovielist.mymovielist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    public ResponseEntity<String> addRating(String movieName,RatingDTO ratingDTO, String authHeader){
        Rating newRating = new Rating();
        String email = jwtUtil.extractUsername(authHeader.substring(7));
        Optional<User> user = userRepository.findById(email);
        Optional<Movie> movie = movieRepository.findByTitle(movieName);
        newRating.setDescription(ratingDTO.getDescription());
        newRating.setRating(ratingDTO.getRating());
        if(user.isPresent() && movie.isPresent()){
            newRating.setMovie(movie.get());
            newRating.setUser(user.get());
            RatingKey ratingKey = new RatingKey(user.get().getEmail(),movie.get().getId());
            newRating.setRatingKey(ratingKey);
        }

        ratingRepository.save(newRating);
        return ResponseEntity.ok("Added rating successfully");
    }

    /**
     * Populates the reviews for each of the movies
     * @param movieDTO contains essential info such as the movie id, title, and img
     * @param movie that the review is for
     * @param user of the session
     */
    public void populateCategoryMovieReviewDTO(MovieDTO movieDTO, Movie movie, User user){
        Optional<Rating> rating = ratingRepository.findByUserAndMovie(user,movie);
        if(rating.isPresent()){
            RatingDTO ratingDTO = new RatingDTO(rating.get().getRating(),rating.get().getDescription());
            movieDTO.setRating(ratingDTO);
        }
    }
}
