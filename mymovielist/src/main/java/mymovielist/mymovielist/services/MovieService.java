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
            Set<Long> newSelectedCategories = new HashSet<>(selectedCategoryIds);
            Set<Long> oldSelectedCategories = new HashSet<>(categoryIds);
            Set<Long> deleteCategories = new HashSet<>(oldSelectedCategories);
            deleteCategories.removeAll(newSelectedCategories);
            System.out.println(deleteCategories);
            deleteCategories.forEach(categoryId->{
                Optional<Category> category = categoryRepository.findById(categoryId);
                if(category.isPresent()){
                    movie1.get().getCategories().remove(category.get());
                }
            });
            Set<Long> addCategories = new HashSet<>(newSelectedCategories);
            addCategories.removeAll(oldSelectedCategories);
            System.out.println(addCategories);
            addCategories.forEach(categoryId->{
                Optional<Category> category = categoryRepository.findById(categoryId);
                if(category.isPresent()){
                    movie1.get().getCategories().add(category.get());
                }
            });
            movieRepository.save(movie1.get());
        }
        return ResponseEntity.ok("Updated the selected categories");
    }

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
