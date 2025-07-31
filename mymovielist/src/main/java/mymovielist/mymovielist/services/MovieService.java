package mymovielist.mymovielist.services;

import mymovielist.mymovielist.dto.MovieCategoryRequest;
import mymovielist.mymovielist.entities.Category;
import mymovielist.mymovielist.entities.Movie;
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
}
