package mymovielist.mymovielist.repositories;

import mymovielist.mymovielist.entities.Category;
import mymovielist.mymovielist.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the movie entity
 * Provides JPA methods
 * @author Andrew Gee
 */
public interface MovieRepository extends JpaRepository<Movie,Long> {
    /**
     * Finds movie by the title
     * @param title of movie we want to find
     * @return movie with the title, null otherwise
     */
    public Optional<Movie> findByTitle(String title);

    /**
     * Finds a list of movies by the categories they are in
     * @param category the category that contains the movie
     * @return list of movies in category if found, empty list otherwise
     */
    public List<Movie> findAllByCategories(Category category);
}
