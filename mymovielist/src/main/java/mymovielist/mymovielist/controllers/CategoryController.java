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

/**
 * Controller responsible for GET,PUT,POST, and DELETE requests about category entity
 * @author Andrew Gee
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a new category for the user
     * @param categoryRequest contains the title and description of the new category
     * @param authHeader contains jwt token
     * @return response entity with the newly created category, null if it can't be created
     */
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryRequest categoryRequest, @RequestHeader("Authorization") String authHeader){
        return categoryService.createCategory(categoryRequest,authHeader);
    }

    /**
     * Gets the user's categories, the movies inside the categories, and the ratings for those movies
     * @param authHeader contains the jwt token
     * @return the user's categories, the movies inside the categories, and the ratings for those movies in dictionary format
     * null otherwise
     */
    @GetMapping("/getUserCategories")
    public ResponseEntity<List<CategoryMovieRatingDTO>> getUserCategories(@RequestHeader("Authorization") String authHeader){
        return categoryService.getUserCategories(authHeader);
    }

    /**
     * Updates the category's title and description
     * @param authHeader authorization header
     * @param id of the category
     * @param categoryRequest contains the new title and description
     * @return status of the update
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategoryTitleDesc(@RequestHeader("Authorization") String authHeader, @PathVariable("id") Long id,@RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategoryTitleDesc(authHeader.substring(7), id,categoryRequest);
    }

    /**
     * Deletes the category given the category's id
     * @param id identification number of the category we want to delete
     * @return the status of the deletion
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }
}
