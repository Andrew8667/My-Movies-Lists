package mymovielist.mymovielist.controllers;

import mymovielist.mymovielist.dto.CategoryDTO;
import mymovielist.mymovielist.dto.CategoryRequest;
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

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequest createCategoryRequest, @RequestHeader("Authorization") String authHeader){
        return categoryService.createCategory(createCategoryRequest.getTitle(), createCategoryRequest.getDescription(),authHeader);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        return categoryService.getCategories();
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
