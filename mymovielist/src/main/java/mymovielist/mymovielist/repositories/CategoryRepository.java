package mymovielist.mymovielist.repositories;

import mymovielist.mymovielist.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import mymovielist.mymovielist.entities.Category;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the category entity
 * Provides JPA methods
 * @author Andrew Gee
 */
public interface CategoryRepository extends JpaRepository<Category,Long>{
    /**
     * Finds all the categories by the user provided
     * @param user we want categories for
     * @return a list of categories
     */
    public List<Category> findAllByUser(User user);
    /**
     * Checks if there is entry in category table with corresponding title and user
     * @param title of the category
     * @param user that owns the category
     * @return true if found, false otherwise
     */
    public boolean existsByTitleAndUser(String title, User user);
}
