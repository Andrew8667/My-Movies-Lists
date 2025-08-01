package mymovielist.mymovielist.controllers;

import mymovielist.mymovielist.dto.CategoryDTO;
import mymovielist.mymovielist.dto.CategoryMovieRatingDTO;
import mymovielist.mymovielist.dto.CategoryRequest;
import mymovielist.mymovielist.entities.Category;
import mymovielist.mymovielist.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mymovielist.mymovielist.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a new category
     * @param categoryRequest contains the title and description of the new category
     * @param authHeader contains jwt token
     * @return response entity with the newly created category, null if it can't be created
     */
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest, @RequestHeader("Authorization") String authHeader){
        return categoryService.createCategory(categoryRequest,authHeader);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        return categoryService.getCategories();
    }

    /**
     * Gets the user's categories, the movies inside the categories, and the ratings for those movies
     * @param authHeader contains the jwt token
     * @return the user's categories, the movies inside the categories, and the ratings for those movies in dictionary format
     * null otherwise
     */
    @GetMapping("getUserCategories")
    public ResponseEntity<List<CategoryMovieRatingDTO>> getUserCategories(@RequestHeader("Authorization") String authHeader){
        return categoryService.getUserCategories(authHeader);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Long id){
        return categoryService.getCategory(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategoryTitle(@PathVariable("id") Long id,@RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategoryTitle(id,categoryRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryRepository.deleteById(id);
        return ResponseEntity.ok("Category deleted");
    }
}
