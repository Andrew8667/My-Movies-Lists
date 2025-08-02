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
    public List<Category> findAllByUser(User user);

    /**
     * Finds the category by its title
     * @param title of category we want to find
     * @return category if found, null otherwise
     */
    public Optional<Category> findByTitle(String title);
    /**
     * Checks if there is entry in category table with corresponding title and user
     * @param title of the category
     * @param user that owns the category
     * @return true if found, false otherwise
     */
    public boolean existsByTitleAndUser(String title, User user);
}
