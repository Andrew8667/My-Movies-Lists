package mymovielist.mymovielist.services;

import mymovielist.mymovielist.auth.JwtUtil;
import mymovielist.mymovielist.dto.CategoryDTO;
import mymovielist.mymovielist.dto.CategoryMovieRatingDTO;
import mymovielist.mymovielist.dto.CategoryRequest;
import mymovielist.mymovielist.entities.Movie;
import mymovielist.mymovielist.repositories.MovieRepository;
import mymovielist.mymovielist.repositories.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import mymovielist.mymovielist.entities.Category;
import mymovielist.mymovielist.entities.User;
import mymovielist.mymovielist.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieRepository movieRepository;

    /**
     * Creates a new category
     * @param categoryRequest contains the title and description of the new category
     * @param authHeader contains jwt token
     * @return response entity with the newly created category DTO, null if it can't be created
     */
    public ResponseEntity<CategoryDTO> createCategory(CategoryRequest categoryRequest, String authHeader){
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findById(email);
        if(user.isPresent()){
            //check if category title already exists for user
            if(!categoryRepository.existsByTitleAndUser(categoryRequest.getTitle(),user.get())){
                Category category = new Category();
                category.setTitle(categoryRequest.getTitle());
                category.setUser(user.get());
                category.setDescription(categoryRequest.getDescription());
                Category savedCategory = categoryRepository.save(category);
                CategoryDTO categoryDTO = new CategoryDTO(savedCategory.getId(),categoryRequest.getTitle(),categoryRequest.getDescription(),user.get().getEmail());
                return ResponseEntity.ok(categoryDTO);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * Gets the user's categories, the movies inside the categories, and the ratings for those movies
     * @param authHeader contains the jwt token
     * @return the user's categories, the movies inside the categories, and the ratings for those movies in dictionary format
     * null otherwise
     */
    public ResponseEntity<List<CategoryMovieRatingDTO>> getUserCategories(String authHeader){
        String email = jwtUtil.extractUsername(authHeader.substring(7)); //extract email from the token
        Optional<User> user = userRepository.findById(email);
        if(!userRepository.existsById(email)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<Category> categories = categoryRepository.findAllByUser(user.get());
        List<CategoryMovieRatingDTO> categoryMovieRatingDTOS = new ArrayList<>();
        for(int i = 0 ; i< categories.size() ; i++){
            CategoryMovieRatingDTO categoryMovieRatingDTO = new CategoryMovieRatingDTO();
            categoryMovieRatingDTO.setId(categories.get(i).getId());
            categoryMovieRatingDTO.setTitle(categories.get(i).getTitle());
            categoryMovieRatingDTO.setDescription(categories.get(i).getDescription());
            categoryMovieRatingDTO.setMovies(new ArrayList<>());
            movieService.populateCategoryMovieReviewDTO(categoryMovieRatingDTO.getMovies(),categories.get(i),user.get());
            categoryMovieRatingDTOS.add(categoryMovieRatingDTO);
        }
        return ResponseEntity.ok(categoryMovieRatingDTOS);
    }

    public ResponseEntity<CategoryDTO> getCategory(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            CategoryDTO categoryDTO = new CategoryDTO(category.get().getId(),category.get().getTitle(),category.get().getDescription(),category.get().getUser().getEmail());
            return ResponseEntity.ok(categoryDTO);
        }
        return ResponseEntity.badRequest().body(null);
    }

    /**
     * Updates the category's title and description
     * @param token jwt token of user
     * @param id of the category
     * @param categoryRequest contains the new title and description
     * @return status of the update
     */
    public ResponseEntity<String> updateCategoryTitleDesc(String token, Long id, CategoryRequest categoryRequest){
        Optional<Category> category = categoryRepository.findById(id);
        Optional<User> user = userRepository.findById(jwtUtil.extractUsername(token));
        if(category.isPresent() && user.isPresent()) {
            if (!categoryRepository.existsByTitleAndUser(categoryRequest.getTitle(),user.get())){
                category.get().setTitle(categoryRequest.getTitle());
                category.get().setDescription(categoryRequest.getDescription());
                categoryRepository.save(category.get());
                return ResponseEntity.ok("Successfully updated category title");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not find category to update");
    }

    /**
     * Deletes a users category and the categories from the movie
     * @param id of the category we want to remove
     * @return a response entity with http status
     */
    public ResponseEntity<String> deleteCategory(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            List<Movie> movies = movieRepository.findAllByCategories(category.get());
            movies.forEach(movie -> {
                movie.getCategories().remove(category.get());
            });
            categoryRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully deleted the category");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not delete the category");
    }


}
