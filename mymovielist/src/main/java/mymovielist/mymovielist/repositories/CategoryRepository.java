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
}
