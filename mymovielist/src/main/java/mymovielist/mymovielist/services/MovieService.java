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

import java.util.*;

/**
 * Service class to handle the business logic that process the requests involving movie
 * @author Andrew Gee
 */
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

    /**
     * Adds movie, its review, and its categories to db
     * @param authHeader contains the jwt token
     * @param movieReviewDTO contains the movie info, rating info, and categories the movie belongs to
     * @return success string if movie is successfully added, error otherwise
     */
    public ResponseEntity<String> addMovie(String authHeader, MovieReviewDTO movieReviewDTO){
        Optional<Movie> movie = movieRepository.findByTitle(movieReviewDTO.getTitle());
        User user = userRepository.findById(jwtUtil.extractUsername(authHeader.substring(7))).orElseThrow();
        if(movie.isPresent()){
            if(!ratingRepository.existsByUserAndMovie(user,movie.get())){
                Rating rating = new Rating(new RatingKey(user.getEmail(),movie.get().getId()),movieReviewDTO.getStars(),movieReviewDTO.getReview(),user,movie.get());
                movieReviewDTO.getCategories().forEach(id->{
                    Category category = categoryRepository.findById(id).orElseThrow();
                    movie.get().getCategories().add(category);
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
                Category category = categoryRepository.findById(id).orElseThrow();
                newMovie.getCategories().add(category);
            });
            movieRepository.save(newMovie);
            Rating rating = new Rating(new RatingKey(user.getEmail(),newMovie.getId()),movieReviewDTO.getStars(),movieReviewDTO.getReview(),user,newMovie);
            ratingRepository.save(rating);
            return ResponseEntity.ok("Successfully added movie");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not add movie or review");
    }

    /**
     * Populates the movies for each category in the categoryMovieReviewDTO
     * @param movieDTOS the category's list of movies
     * @param category that the movie belongs to
     * @param user of the current session
     */
    public void populateCategoryMovieReviewDTO(List<MovieDTO> movieDTOS, Category category, User user){
        List<Movie> movies = movieRepository.findAllByCategories(category);
        movies.forEach(movie -> {
            MovieDTO movieDTO = new MovieDTO(movie.getId(),movie.getImg(),movie.getTitle(), new RatingDTO());
            ratingService.populateCategoryMovieReviewDTO(movieDTO,movie,user);
            movieDTOS.add(movieDTO);
        });
    }

    /**
     * Deletes a movie from a category
     * @param movieId identification number of the movie we want to remove from category
     * @param categoryId identification number of the category we want to remove movie from
     * @param authHeader contains the jwt token
     * @return response entity containing status of the deletion
     */
    public ResponseEntity<String> deleteMovieFromCategory(long movieId, long categoryId, String authHeader){
        Optional<Movie> movie = movieRepository.findById(movieId);
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(movie.isPresent() && category.isPresent()){
            movie.get().getCategories().remove(category.get());
            movieRepository.save(movie.get());
            return ResponseEntity.ok("Successfully removed movie from category");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error removing movie from category");
    }

    /**
     * Used to find the list of category ids for the categories a movie is in for a user
     * @param movie we want the categories for
     * @param authHeader contains the jwt token
     * @return a list of category ids of the categories that have the movie
     */
    public ResponseEntity<List<Long>> getMoviesSelectedCategories(String movie,String authHeader){
        return ResponseEntity.ok(findUsersMovieCategories(movie, authHeader));
    }

    /**
     * Updates the categories a movie is in
     * @param selectedCategoryIds the new set of categories that the movie should be in
     * @param movie the movie we are updating
     * @param authHeader contains the jwt token
     * @return status of update
     */
    public ResponseEntity<String> updateSelectedCategories(List<Long> selectedCategoryIds, String movie, String authHeader){
        List<Long> categoryIds = findUsersMovieCategories(movie, authHeader);
        Optional<Movie> movie1 = movieRepository.findByTitle(movie);
        if (movie1.isPresent()){
            Set<Long> newSelectedCategories = new HashSet<>(selectedCategoryIds);//new list of categories movie should be in
            Set<Long> oldSelectedCategories = new HashSet<>(categoryIds); //old list of categories movies should be in
            Set<Long> deleteCategories = new HashSet<>(oldSelectedCategories); //categories in old that aren't in new
            deleteCategories.removeAll(newSelectedCategories);
            deleteCategories.forEach(categoryId->{
                Category category = categoryRepository.findById(categoryId).orElseThrow();
                movie1.get().getCategories().remove(category);
            });
            Set<Long> addCategories = new HashSet<>(newSelectedCategories);//categories in new that aren't in old
            addCategories.removeAll(oldSelectedCategories);
            addCategories.forEach(categoryId->{
                Category category = categoryRepository.findById(categoryId).orElseThrow();
                movie1.get().getCategories().add(category);
            });
            movieRepository.save(movie1.get());
        }
        return ResponseEntity.ok("Updated the selected categories");
    }

    /**
     * Helper method used to find the list of category ids for the categories a movie is in for a user
     * @param movie we want the categories for
     * @param authHeader contains the jwt token
     * @return a list of category ids of the categories that have the movie
     */
    private List<Long> findUsersMovieCategories(String movie,String authHeader){
        Optional<Movie> movie1 = movieRepository.findByTitle(movie);
        String email = jwtUtil.extractUsername(authHeader.substring(7));
        User user = userRepository.findById(email).orElseThrow();
        List<Category> categories = categoryRepository.findAllByUser(user);
        List<Long> categoryIds = new ArrayList<>();
        if(movie1.isPresent()){
            categories.forEach(category -> {
                if(movie1.get().getCategories().contains(category)){
                    categoryIds.add(category.getId());
                }
            });
        }
        return categoryIds;
    }
}
