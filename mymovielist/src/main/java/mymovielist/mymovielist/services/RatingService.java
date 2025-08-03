package mymovielist.mymovielist.services;

import mymovielist.mymovielist.auth.JwtUtil;
import mymovielist.mymovielist.dto.*;
import mymovielist.mymovielist.entities.Category;
import mymovielist.mymovielist.entities.Movie;
import mymovielist.mymovielist.entities.Rating;
import mymovielist.mymovielist.entities.User;
import mymovielist.mymovielist.keys.RatingKey;
import mymovielist.mymovielist.repositories.CategoryRepository;
import mymovielist.mymovielist.repositories.MovieRepository;
import mymovielist.mymovielist.repositories.RatingRepository;
import mymovielist.mymovielist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.swing.text.html.Option;
import java.util.List;
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
    @Autowired
    private CategoryRepository categoryRepository;

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

    public ResponseEntity<String> updateRating(long movieId, String authHeader, RatingDTO ratingDTO){
        String email = jwtUtil.extractUsername(authHeader.substring(7));
        Optional<User> user = userRepository.findById(email);
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(user.isPresent() && movie.isPresent()){
            Optional<Rating> rating = ratingRepository.findByUserAndMovie(user.get(),movie.get());
            if(rating.isPresent()){
                rating.get().setRating(ratingDTO.getRating());
                rating.get().setDescription(ratingDTO.getDescription());
                ratingRepository.save(rating.get());
                return ResponseEntity.ok("Successfully updated rating");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't update rating");
    }

    /**
     * Finds the number of stars and description of rating for a movie and user
     * Used to populate the movie rating details in the home page
     * @param authHeader contains jwt token
     * @param movie the name of the movie we want to find review for
     * @return the stars and description of the rating that user put for that movie, null otherwise
     */
    public ResponseEntity<RatingDTO> getRatingDetails(String authHeader,String movie){
        //find the rating by the user and movie
        String email = jwtUtil.extractUsername(authHeader.substring(7));
        Optional<User> user = userRepository.findById(email);
        Optional<Movie> movie1 = movieRepository.findByTitle(movie);
        if(user.isPresent() && movie1.isPresent()){
            Optional<Rating> rating = ratingRepository.findByUserAndMovie(user.get(),movie1.get());
            if(rating.isPresent()){
                RatingDTO ratingDTO = new RatingDTO(rating.get().getRating(),rating.get().getDescription());
                return ResponseEntity.ok(ratingDTO);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * Given a user and movie title, method removes that user's rating for that movie
     * It also removes the categories of the user from that movie
     * @param movie id of movie we want to remove
     * @param authHeader contains jwt token
     * @return String containing message about status of deletion
     */
    public ResponseEntity<String> deleteRating(long movie, String authHeader){
        Optional<Movie> movie1 = movieRepository.findById(movie);
        String email = jwtUtil.extractUsername(authHeader.substring(7));
        Optional<User> user = userRepository.findById(email);
        if(movie1.isPresent() && user.isPresent()){
            Optional<Rating> rating = ratingRepository.findByUserAndMovie(user.get(),movie1.get());
            List<Category> categories = categoryRepository.findAllByUser(user.get());
            categories.forEach(category -> {
                if(movie1.get().getCategories().contains(category)){
                    movie1.get().getCategories().remove(category);
                }
            });
            movieRepository.save(movie1.get());
            if(rating.isPresent()){
                ratingRepository.delete(rating.get());
            }
            return ResponseEntity.ok("Successfully deleted rating");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not delete rating");
    }
}
