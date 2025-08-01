package mymovielist.mymovielist.services;

import mymovielist.mymovielist.dto.CategoryMovieRatingDTO;
import mymovielist.mymovielist.dto.MovieCategoryRequest;
import mymovielist.mymovielist.dto.MovieDTO;
import mymovielist.mymovielist.dto.RatingDTO;
import mymovielist.mymovielist.entities.Category;
import mymovielist.mymovielist.entities.Movie;
import mymovielist.mymovielist.entities.User;
import mymovielist.mymovielist.repositories.CategoryRepository;
import mymovielist.mymovielist.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> addMovieCategory(MovieCategoryRequest movie){
        Optional<Movie> curMovie = movieRepository.findByTitle(movie.getTitle());
        if(!curMovie.isPresent()){ //movie hasn't been added
            Movie newMovie = new Movie();
            newMovie.setTitle(movie.getTitle());
            newMovie.setImg(movie.getImg());
            Optional<Category> category = categoryRepository.findById(movie.getCategory());
            List<Category> categories = new ArrayList<>();
            if(category.isPresent()){
                categories.add(category.get());
            }
            newMovie.setCategories(categories);
            movieRepository.save(newMovie);
            return ResponseEntity.ok("Added new movie");
        }else { //movie already exists
            Optional<Category> category = categoryRepository.findById(movie.getCategory());
            if(category.isPresent()){
                if(!curMovie.get().getCategories().contains(category.get())){
                    curMovie.get().getCategories().add(category.get());
                    movieRepository.save(curMovie.get());
                    return ResponseEntity.ok("Added category to movie");
                }
            }
        }
        return ResponseEntity.ok("The movie is already in the category");
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
