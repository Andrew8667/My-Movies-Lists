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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    public ResponseEntity<String> addMovie(String authHeader, MovieReviewDTO movieReviewDTO){
        Optional<Movie> movie = movieRepository.findByTitle(movieReviewDTO.getTitle());
        Optional<User> user = userRepository.findById(jwtUtil.extractUsername(authHeader.substring(7)));
        if(movie.isPresent()){
            if(!ratingRepository.existsByUserAndMovie(user.get(),movie.get())){
                Rating rating = new Rating();
                rating.setRatingKey(new RatingKey(user.get().getEmail(),movie.get().getId()));
                rating.setUser(user.get());
                rating.setMovie(movie.get());
                rating.setRating(movieReviewDTO.getStars());
                rating.setDescription(movieReviewDTO.getReview());
                movieReviewDTO.getCategories().forEach(id->{
                    Optional<Category> category = categoryRepository.findById(id);
                    movie.get().getCategories().add(category.get());
                });
                ratingRepository.save(rating);
                return ResponseEntity.ok("Successfully added review");
            }
        } else {
            Movie newMovie = new Movie();
            newMovie.setTitle(movieReviewDTO.getTitle());
            newMovie.setImg(movieReviewDTO.getImg());
            newMovie.setCategories(new ArrayList<>());
            movieReviewDTO.getCategories().forEach(id->{
                Optional<Category> category = categoryRepository.findById(id);
                newMovie.getCategories().add(category.get());
            });
            movieRepository.save(newMovie);
            Rating rating = new Rating();
            rating.setDescription(movieReviewDTO.getReview());
            rating.setRating(movieReviewDTO.getStars());
            rating.setUser(user.get());
            rating.setMovie(newMovie);
            rating.setRatingKey(new RatingKey(user.get().getEmail(),newMovie.getId()));
            ratingRepository.save(rating);
            return ResponseEntity.ok("Successfully added movie");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not add movie or review");
    }

    /**
     * Populates the movies for each category
     * @param movieDTOS contains essential info such as the movie id, title, and img
     * @param category the movie belongs to
     * @param user of the current session
     */
    public void populateCategoryMovieReviewDTO(List<MovieDTO> movieDTOS, Category category, User user){
        List<Movie> movies = movieRepository.findAllByCategories(category);
        for(int i = 0 ; i < movies.size() ; i++){
            Movie movie = movies.get(i);
            MovieDTO movieDTO = new MovieDTO(movie.getId(),movie.getImg(),movie.getTitle(), new RatingDTO());
            ratingService.populateCategoryMovieReviewDTO(movieDTO,movie,user);
            movieDTOS.add(movieDTO);
        }
    }
}
